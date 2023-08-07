package com.eyh.mall.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.easyexcel.EasyExcelListener;
import com.eyh.mall.entity.Classification;
import com.eyh.mall.entity.ExcelClassification;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.redis.ClassificationRedis;
import com.eyh.mall.entity.tdo.ClassificationResords;
import com.eyh.mall.entity.tdo.ClassificationSituationDto;
import com.eyh.mall.entity.tdo.ClassificationTdo;
import com.eyh.mall.entity.vo.ClassificationPage;
import com.eyh.mall.feign.client.commodity.CommodityApiClient;
import com.eyh.mall.feign.client.merchantStores.MerchantStoresApiClient;
import com.eyh.mall.mapper.ClassificationMapper;
import com.eyh.mall.rabbitmq.CommodityConstant;
import com.eyh.mall.redis.RedisUtil;
import com.eyh.mall.service.ClassificationService;
import com.eyh.mall.util.FileUtil;
import com.eyh.mall.util.HanyuPinyinUtil;
import com.eyh.mall.util.JsonStringListUtil;
import com.eyh.mall.util.json.JsonUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 分类服务impl
 *
 * @author 李平
 * @Date 2023-2-18
 */
@Service
public class ClassificationServiceImpl extends ServiceImpl<ClassificationMapper, Classification> implements ClassificationService {

    @Autowired
    private ClassificationMapper classificationMapper;

    @Autowired
    private CommodityApiClient commodityApiClient;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private JsonUtil<Classification> classificationJsonUtil;

    @Autowired
    private MerchantStoresApiClient merchantStoresApiClient;

    /**
     * 上传分类图标
     *
     * @param file 文件
     * @param name 名字
     * @return {@link Result}
     */
    @Override
    public Result uploadClassificationIcon(MultipartFile file, String name) {
        try {
            //获取文件后缀
            String extension = "." + FilenameUtils.getExtension(file.getOriginalFilename());

            //生成新的文件名称
            String newFileName = new SimpleDateFormat("mmss").format(new Date()) +
                    UUID.randomUUID().toString().replace("-", "") + extension;

            //目录
            ApplicationHome home = new ApplicationHome(getClass());
            File source = home.getSource();
            String s1 = source.getParentFile().toString() + "/classification_icon/";

            String dirPath = "/" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "/" + HanyuPinyinUtil.toHanyuPinyin(name);
            String dateDirPath = s1 + dirPath;
            File dateDir = new File(dateDirPath);
            if (!dateDir.exists()) {
                dateDir.mkdirs();
            }

            // 文件上传到服务器
            file.transferTo(new File(dateDir, newFileName));
            String pinyinName = "classification_icon/" + HanyuPinyinUtil.toHanyuPinyin(name);
            // 分类ID
            String classificationId = UUID.randomUUID().toString();
            // 将图片上传至OSS存储服务中

            merchantStoresApiClient.uploadLogo(dateDirPath,pinyinName,newFileName, "manage");
            // 发送消息
            // rabbitTemplate.convertAndSend(ClassificationIconConstant.CLASSIFICATION_ICON_QUEUE, JSON.toJSONString(classificationIcon));
            // https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/shop_logo/biaodianfuhaogeVSdongruoguanhuo/4341d0c007457ea04a629b44adcb2ad88ebf.png
            Boolean existence = merchantStoresApiClient.isExistence(pinyinName + "/" + newFileName,"manage");
            if (existence) {
                // 上传到oss成功
                // 删除服务器文件
                FileUtil.deleteFile(new File(s1));
                // 获取商家图片URL
                String ossUrl = merchantStoresApiClient.getOSSUrl(pinyinName, newFileName,"manage");
                return Result.ok(ossUrl);
            }else{
                return Result.err("商品分类图片上传到oss错误");
            }
            // return Result.ok(classificationIcon);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.err("商品分类图片上传错误");
        }
    }

    /**
     * 得到父id
     *
     * @param parentName 父母名字
     * @return {@link Result}
     */
    @Override
    public Result getParentId(String parentName) {
        String key = ClassificationRedis.CLASSIFICATION_ID + parentName;
        Classification classificationRedis = classificationJsonUtil.toJavaBean(redisUtil.get(key), Classification.class);
        if (classificationRedis != null){
            return Result.ok(classificationRedis.getClassificationId());
        }
        LambdaQueryWrapper<Classification> classificationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        classificationLambdaQueryWrapper.eq(!"".equals(parentName),Classification::getClassificationName,parentName);
        Classification classification = classificationMapper.selectOne(classificationLambdaQueryWrapper);

        if (classification != null){
            redisUtil.set(key,JSON.toJSONString(classification),ClassificationRedis.CLASSIFICATION_ID_TTL);

            return Result.ok(classification.getClassificationId());
        }else {
            return Result.err("该分类不存在，请先添加到一级分类中");
        }
    }

    /**
     * 得到父母名单
     *
     * @param parentNameSearch 父母名字搜索
     * @return {@link Result}
     */
    @Override
    public Result getParentNameList(String parentNameSearch) {
        String key = ClassificationRedis.CLASSIFICATION_ONE_NAME_LIST + parentNameSearch;
        // 当 parentNameSearch 为空时，走redis
        if ("".equals(parentNameSearch)){
            Object o = redisUtil.get(key);
            if (o != null){
                String[] strings = JsonStringListUtil.toArrayString(o.toString());
                return Result.ok(strings);
            }
        }
        // 当 parentNameSearch 不为空时，走mysql
        // redis为空
        List<String> oneLevel = selectOneLevel(parentNameSearch, key, ClassificationRedis.CLASSIFICATION_ONE_NAME_LIST_TTL);
        if (oneLevel == null){
            return Result.err("一级分类为空，请先添加一级分类");
        }
        return Result.ok(oneLevel);
    }

    /**
     * 选择一个级别
     *
     * @param parentNameSearch 父母名字搜索
     * @param key              关键
     * @param ttl              ttl
     * @return {@link List}<{@link String}>
     */
    List<String> selectOneLevel(String parentNameSearch,String key,long ttl){
        LambdaQueryWrapper<Classification> classificationLambdaQueryWrapper = new LambdaQueryWrapper<>();

        classificationLambdaQueryWrapper.like(!"".equals(parentNameSearch),Classification::getClassificationName,parentNameSearch);
        classificationLambdaQueryWrapper.eq(true,Classification::getClassificationGrade,0);
        // 一级分类集合
        List<Classification> classificationList = classificationMapper.selectList(classificationLambdaQueryWrapper);

        if (classificationList.size() == 0) {
            // return Result.err("一级分类为空，请先添加一级分类");
            return null;
        }
        List<String> nameList = new ArrayList<>();
        for(Classification l : classificationList){
            nameList.add(l.getClassificationName());
        }
        if ("".equals(parentNameSearch)){
            // 若搜索条件为空，则表示搜索全部
            redisUtil.set(key,JSON.toJSONString(nameList),ttl);
        }
        return nameList;
    }

    /**
     * 得到分类页面
     *
     * @param classificationPage 分类页面
     * @return {@link Result}
     */
    @Override
    public Result getClassificationByPage(ClassificationPage classificationPage) {
        IPage<Classification> page = new Page<Classification>(classificationPage.getCurrentPage(), classificationPage.getPageSize());
        LambdaQueryWrapper<Classification> lqw = new LambdaQueryWrapper<>();
        // 分页查询所有一级分类
        lqw.eq(true,Classification::getClassificationGrade,0);

        // 条件查询
        lqw.like(!"".equals(classificationPage.getClassificationName()),
                Classification::getClassificationName,
                classificationPage.getClassificationName());

        IPage<Classification> selectPage = classificationMapper.selectPage(page, lqw);

        List<Classification> records = selectPage.getRecords();

        ClassificationTdo classificationTdo = new ClassificationTdo();
        classificationTdo.setTotal(selectPage.getTotal());
        classificationTdo.setCurrentPage(selectPage.getCurrent());
        classificationTdo.setPageSize(selectPage.getPages());
        classificationTdo.setSize(selectPage.getSize());

        List<ClassificationResords> list = new ArrayList<>();
        for(Classification r : records){
            // 查询所有二级分类
            LambdaQueryWrapper<Classification> classificationLambdaQueryWrapper = new LambdaQueryWrapper<>();
            classificationLambdaQueryWrapper.eq(true,Classification::getClassificationGrade,1);

            // 查询当前一级分类下的二级分类
            classificationLambdaQueryWrapper.eq(
                    !"".equals(r.getClassificationId()),
                    Classification::getParentId,
                    r.getClassificationId());

            list.add(new ClassificationResords(
                    r.getClassificationId(),
                    r.getClassificationName(),
                    r.getClassificationDescribe(),
                    r.getClassificationGrade(),
                    classificationMapper.selectList(classificationLambdaQueryWrapper)));
        }
        classificationTdo.setClassificationResordsList(list);
        return Result.ok(classificationTdo);
    }

    /**
     * 删除分类按等级和id
     *
     * @param classificationGrade 分类等级
     * @param classificationId    分类id
     * @return {@link Result}
     */
    @Override
    public Result deleteClassificationByGradeId(Integer classificationGrade, String classificationId) {
        // TODO 判断该分类下是否存在商品，存在，不允许删除，不存在，删除
        // 校验分类等级
        if (classificationGrade == 0){
            //删除一级分类，需删除其所有下级分类
            LambdaQueryWrapper<Classification> wrapper = new LambdaQueryWrapper<>();
            // 匹配删除一级分类
            wrapper.eq(!"".equals(classificationId),Classification::getParentId,classificationId);
            List<Classification> classificationList = classificationMapper.selectList(wrapper);

            if (classificationList.size() != 0){
                // 说明有二级分类，不能删除
                return Result.err("该分类拥有下级分类，不能删除");
            }
            // 没有二级分类，直接删除
            LambdaQueryWrapper<Classification> lqw = new LambdaQueryWrapper<>();
            lqw.eq(!"".equals(classificationId),Classification::getClassificationId,classificationId);
            classificationMapper.delete(lqw);
            return Result.ok("删除成功");
        } else {
            LambdaQueryWrapper<Classification> lqw = new LambdaQueryWrapper<>();
            lqw.eq(!"".equals(classificationId),Classification::getClassificationId,classificationId);
            //发送消息删除文件
            Classification classification = classificationMapper.selectOne(lqw);
            String msg = "manage/classification_icon/" + HanyuPinyinUtil.toHanyuPinyin(classification.getClassificationName());

            if (classification.getIsBatch() == 0){
                // 管理员手动上传，说明阿里云oss存在此分类图标
                merchantStoresApiClient.delete(msg);
            }
            // rabbitTemplate.convertAndSend(CommodityConstant.DELETE_ALY_FILE_QUEUE,JSON.toJSONString(msg));
            // 否则为Excel批量导入，阿里云oss中不存在该分类图标，直接删除二级分类
            classificationMapper.delete(lqw);
            return Result.ok("删除成功");
        }
    }

    /**
     * 批量导入
     *
     * @param file  文件
     * @param grade 年级
     * @return {@link Result}
     */
    @Override
    public Result batchLeadeIn(MultipartFile file,String grade) {
        try {
            //获取文件后缀
            String extension = "." + FilenameUtils.getExtension(file.getOriginalFilename());
            //生成新的文件名称
            String newFileName = new SimpleDateFormat("mmss").format(new Date()) +
                    UUID.randomUUID().toString().replace("-", "") + extension;
            //目录
            ApplicationHome home = new ApplicationHome(getClass());
            File source = home.getSource();
            String s1 = source.getParentFile().toString() + "/classification/";

            String dirPath = "/" + new SimpleDateFormat("yyyy-MM-dd")
                    .format(new Date());
            String dateDirPath = s1 + dirPath;
            File dateDir = new File(dateDirPath);
            if (!dateDir.exists()) {
                dateDir.mkdirs();
            }

            File file1 = new File(dateDir, newFileName);
            // 文件上传到服务器
            file.transferTo(file1);

            String absolutePath = file1.getAbsolutePath();
            EasyExcelListener<ExcelClassification> listener = new EasyExcelListener<>();
            EasyExcel.read(absolutePath, ExcelClassification.class, listener)
                    .sheet()
                    .doRead();

            String g = "0".equals(grade) ? "first" : "second";
            String key = ClassificationRedis.CLASSIFICATION_EXCEL_LIST + g;
            // 将Excel数据存储
            redisUtil.lSet(key,
                    JSON.toJSONString(listener.getCachedDataList()),
                    ClassificationRedis.CLASSIFICATION_EXCEL_LIST_TTL);

            // RabbitMQ发送消息存储数据
            rabbitTemplate.convertAndSend(
                    CommodityConstant.SAVE_CLASSIFICATION_LIST_QUEUE,
                    key);

            // 删除服务器文件
            FileUtil.deleteFile(new File(s1));
            return Result.ok("导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.err("导入错误");
        }
    }

    /**
     * 根据一级分类名称获取所有的二级分类名称
     *
     * @param oneLevelName 一个级别名称
     * @return {@link Result}
     */
    @Override
    public Result getTwoLevelNameList(String oneLevelName) {
        String key = ClassificationRedis.CLASSIFICATION_TWO_NAME_LIST + oneLevelName;
        Object o = redisUtil.get(key);
        if (o == null){
            List<String> nameList = selectTwoLevelNameList(oneLevelName, key, ClassificationRedis.CLASSIFICATION_TWO_NAME_LIST_TTL);
            if (nameList != null){
                if (nameList.size() == 0){
                    return Result.err("该分类没有二级分类，请先前往添加二级分类");
                }
                return Result.ok(nameList);
            }
            return Result.err("结果为空");
        }
        String[] arrayString = JsonStringListUtil.toArrayString(o.toString());
        if (arrayString.length == 0){
            List<String> nameList = selectTwoLevelNameList(oneLevelName, key, ClassificationRedis.CLASSIFICATION_TWO_NAME_LIST_TTL);
            if (nameList.size() == 0){
                return Result.err("该分类没有二级分类，请先前往添加二级分类");
            }
            return Result.ok(nameList);
        }
        return Result.ok(arrayString);
    }

    /**
     * 得到分类情况
     *
     * @return {@link Result}
     */
    @Override
    public Result getClassificationSituation() {
        LambdaQueryWrapper<Classification> wrapper = new LambdaQueryWrapper<>();
        //一级分类
        wrapper.eq(true,Classification::getClassificationGrade,0);
        List<Classification> classificationList = classificationMapper.selectList(wrapper);

        List<ClassificationSituationDto> list = new ArrayList<ClassificationSituationDto>();
        classificationList.forEach(c -> {
            LambdaQueryWrapper<Classification> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(true,Classification::getParentId,c.getClassificationId());

            ClassificationSituationDto dto = new ClassificationSituationDto();
            dto.setName(c.getClassificationName());
            dto.setCount(classificationMapper.selectCount(queryWrapper));
            list.add(dto);
        });

        return Result.ok(list);
    }

    /**
     * 得到一个分类列表
     *
     * @return {@link List}<{@link Classification}>
     */
    @Override
    public List<Classification> getAllOneClassificationList() {
        LambdaQueryWrapper<Classification> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(true,Classification::getClassificationGrade,0);
        return classificationMapper.selectList(wrapper);
    }

    /**
     * 选择两层名称列表
     *
     * @param oneLevelName 一个级别名称
     * @param key          关键
     * @param ttl          ttl
     * @return {@link List}<{@link String}>
     */
    List<String> selectTwoLevelNameList(String oneLevelName,String key,long ttl){
        LambdaQueryWrapper<Classification> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(true,Classification::getClassificationGrade,0);
        wrapper.eq(!"".equals(oneLevelName),Classification::getClassificationName,oneLevelName);
        Classification classification = classificationMapper.selectOne(wrapper);
        if (classification == null){
            return null;
        }
        LambdaQueryWrapper<Classification> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(true,Classification::getClassificationGrade,1);
        queryWrapper.eq(!"".equals(classification.getClassificationId()),Classification::getParentId,classification.getClassificationId());
        List<Classification> selectList = classificationMapper.selectList(queryWrapper);
        if (selectList.size() > 0){
            List<String> list = new ArrayList<>();
            for (Classification c : selectList){
                list.add(c.getClassificationName());
            }
            redisUtil.set(key,JSON.toJSONString(list),ttl);
            return list;
        }
        return null;
    }

    /**
     * 添加商品分类
     *
     * @param classification 分类
     * @return {@link Result}
     */
    @Override
    public Result addCommodityClassification(Classification classification) {

        //判断该分类是否已经添加
        LambdaQueryWrapper<Classification> classificationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        classificationLambdaQueryWrapper.eq(
                !"".equals(classification.getClassificationName()),
                Classification::getClassificationName,
                classification.getClassificationName()
        );

        if (classificationMapper.selectOne(classificationLambdaQueryWrapper) != null){
            return Result.err(classification.getClassificationName() + "分类已存在，无需重复添加");
        }

        String id = UUID.randomUUID().toString();
        String classificationId = UUID.randomUUID().toString();
        classification.setId(id);
        classification.setClassificationId(classificationId);
        classification.setIsBatch(0);

        return classificationMapper.insert(classification) > 0
                ? Result.ok("商品分类添加成功")
                : Result.err("商品分类添加失败");

    }

}
