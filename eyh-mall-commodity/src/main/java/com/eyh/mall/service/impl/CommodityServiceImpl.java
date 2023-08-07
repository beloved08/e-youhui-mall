package com.eyh.mall.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.easyexcel.CommodityExcelListener;
import com.eyh.mall.entity.*;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.merchantStores.Business;
import com.eyh.mall.entity.redis.ClassificationRedis;
import com.eyh.mall.entity.tdo.CommodityDetailTdo;
import com.eyh.mall.entity.tdo.CommodityResords;
import com.eyh.mall.entity.tdo.CommodityTdo;
import com.eyh.mall.entity.tdo.DataVDto;
import com.eyh.mall.entity.vo.ActivityCommodity;
import com.eyh.mall.entity.vo.CommodityBatch;
import com.eyh.mall.entity.vo.CommodityPage;
import com.eyh.mall.entity.vo.CommodityVo;
import com.eyh.mall.feign.client.commodity.CommodityApiClient;
import com.eyh.mall.feign.client.merchantStores.BusinessApiClient;
import com.eyh.mall.feign.client.merchantStores.MerchantStoresApiClient;
import com.eyh.mall.feign.client.pay.OrderApiClient;
import com.eyh.mall.feign.entity.Order;
import com.eyh.mall.mapper.ClassificationMapper;
import com.eyh.mall.mapper.CommodityCommentMapper;
import com.eyh.mall.mapper.CommodityMapper;
import com.eyh.mall.rabbitmq.CommodityConstant;
import com.eyh.mall.redis.RedisUtil;
import com.eyh.mall.service.ClassificationService;
import com.eyh.mall.service.CommodityService;
import com.eyh.mall.service.CommodityStockService;
import com.eyh.mall.util.FileUtil;
import com.eyh.mall.util.HanyuPinyinUtil;
import com.eyh.mall.util.TimeUtil;
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
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author 李平
 * @Date 2023-2-18
 */
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements CommodityService {

    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private CommodityCommentMapper commodityCommentMapper;

    @Autowired
    private ClassificationMapper classificationMapper;

    @Autowired
    private ClassificationService classificationService;

    @Autowired
    private MerchantStoresApiClient merchantStoresApiClient;

    @Autowired
    private CommodityApiClient commodityApiClient;

    @Autowired
    private BusinessApiClient businessApiClient;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JsonUtil<Commodity> commodityJsonUtil;

    @Autowired
    private JsonUtil<CommodityDetailTdo> commodityDetailTdoJsonUtil;

    @Autowired
    private OrderApiClient orderApiClient;

    @Autowired
    private CommodityStockService commodityStockService;

    /**
     * 上传商品图片
     *
     * @param file          文件
     * @param commodityName 商品名称
     * @return {@link Result}
     */
    @Override
    public Result uploadCommodityImage(MultipartFile file,
                                       String businessName,
                                       String oneClassificationName,
                                       String twoClassificationName,
                                       String commodityName) {
        try {
            //获取文件后缀
            String extension = "." + FilenameUtils.getExtension(file.getOriginalFilename());
            //生成新的文件名称
            String newFileName = new SimpleDateFormat("mmss").format(new Date()) +
                    UUID.randomUUID().toString().replace("-", "") + extension;

            //目录
            ApplicationHome home = new ApplicationHome(getClass());
            File source = home.getSource();
            String s1 = source.getParentFile().toString() + "/commodity_image/";

            String dirPath = "/" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())
                    + "/" + HanyuPinyinUtil.toHanyuPinyin(commodityName);
            String dateDirPath = s1 + dirPath;
            File dateDir = new File(dateDirPath);
            if (!dateDir.exists()) {
                dateDir.mkdirs();
            }

            String filePath = HanyuPinyinUtil.toHanyuPinyin(businessName)
                    + "/" + HanyuPinyinUtil.toHanyuPinyin(oneClassificationName)
                    + "/" + HanyuPinyinUtil.toHanyuPinyin(twoClassificationName)
                    + "/" + HanyuPinyinUtil.toHanyuPinyin(commodityName);

            // 文件上传到服务器
            file.transferTo(new File(dateDir, newFileName));
            String pinyinName = "commodity_image/" + filePath;
            // 分类ID
            String classificationId = UUID.randomUUID().toString();
            // 将图片上传至OSS存储服务中
            merchantStoresApiClient.uploadLogo(dateDirPath, pinyinName, newFileName, "manage");
            // 发送消息
            // rabbitTemplate.convertAndSend(ClassificationIconConstant.CLASSIFICATION_ICON_QUEUE, JSON.toJSONString(classificationIcon));
            // https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/shop_logo/biaodianfuhaogeVSdongruoguanhuo/4341d0c007457ea04a629b44adcb2ad88ebf.png
            Boolean existence = merchantStoresApiClient.isExistence(pinyinName + "/" + newFileName, "manage");
            if (existence) {
                // 上传到oss成功
                // 删除服务器文件
                FileUtil.deleteFile(new File(s1));
                // 获取商家图片URL
                String ossUrl = merchantStoresApiClient.getOSSUrl(pinyinName, newFileName, "manage");
                return Result.ok(ossUrl);
            } else {
                return Result.err("商品图片上传到oss错误");
            }
            // return Result.ok(classificationIcon);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.err("商品图片上传错误");
        }
    }

    /**
     * 添加商品
     *
     * @param commodityVo 商品签证官
     * @return {@link Result}
     */
    @Override
    public Result addCommodity(CommodityVo commodityVo) {
        // 判断该商品是否已经添加
        // 获取商家
        Business business = commodityApiClient.getBusiness(commodityVo.getBusinessName());
        // 获取分类
        LambdaQueryWrapper<Classification> classificationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        classificationLambdaQueryWrapper.eq(
                !"".equals(commodityVo.getTwoClassificationName()),
                Classification::getClassificationName,
                commodityVo.getTwoClassificationName()
        );
        //二级分类
        classificationLambdaQueryWrapper.eq(true, Classification::getClassificationGrade, 1);
        Classification classification = classificationMapper.selectOne(classificationLambdaQueryWrapper);
        // 获取商品
        LambdaQueryWrapper<Commodity> commodityLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //商品名称
        commodityLambdaQueryWrapper.eq(
                !"".equals(commodityVo.getCommodityName()),
                Commodity::getCommodityName,
                commodityVo.getCommodityName()
        );
        //商家
        commodityLambdaQueryWrapper.eq(!"".equals(business.getBusinessId()),
                Commodity::getBusinessId, business.getBusinessId());
        //分类
        commodityLambdaQueryWrapper.eq(!"".equals(classification.getClassificationId()),
                Commodity::getTwoClassificationId, classification.getClassificationId());
        commodityLambdaQueryWrapper.eq(!"".equals(classification.getParentId()),
                Commodity::getOneClassificationId, classification.getParentId());

        Commodity commodity = commodityMapper.selectOne(commodityLambdaQueryWrapper);

        if (commodity != null) {
            // 已经添加过该商品
            return Result.err("该商品已添加，无需重复添加");
        }
        String groundTime = "";
        if ("0".equals(commodityVo.getCommodityStatus())) {
            // 立即上架
            groundTime = TimeUtil.getCurrentTime();
        }
        // 没有添加过该商品
        Commodity c = new Commodity(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                //一级分类ID
                classification.getParentId(),
                //二级分类ID
                classification.getClassificationId(),
                //商品名称
                commodityVo.getCommodityName(),
                //零售价格
                commodityVo.getRetailPrice(),
                // 图片URL
                commodityVo.getCommodityImageUrl(),
                //商品描述
                commodityVo.getCommodityDescribe(),
                //状态 0上架，1下架
                Integer.parseInt(commodityVo.getCommodityStatus()),
                //类型 0虚拟商品，1实物商品
                Integer.parseInt(commodityVo.getCommodityType()),
                //计量单位
                commodityVo.getMeterCompany(),
                //销售模式 0零售型，1批发型
                Integer.parseInt(commodityVo.getSalesModel()),
                //商品重量
                commodityVo.getCommodityWeight(),
                //商品推荐 0：推荐，1不推荐
                Integer.parseInt(commodityVo.getCommodityRecommend()),
                //商品批发价格
                commodityVo.getWholesalePrice(),
                //创建时间
                TimeUtil.getCurrentTime(),
                //上架时间
                groundTime,
                //下架时间
                "",
                //下架原因
                "",
                //是否批量上传 0否，1是
                0,
                //所属商家ID
                business.getBusinessId(),
                Integer.parseInt(commodityVo.getCommodityStock()),
                2
        );
        int insert = commodityMapper.insert(c);
        //添加商品库存记录
        commodityStockService.increaseProductInventory(c.getCommodityId(), c.getCommodityStock());

        return insert > 0 ? Result.ok("商品添加成功") : Result.err("商品添加失败");
    }

    /**
     * 得到商品页面
     *
     * @param commodityPage 商品页面
     * @return {@link Result}
     */
    @Override
    public Result getCommodityByPage(CommodityPage commodityPage) {
        IPage<Commodity> page = new Page<Commodity>(commodityPage.getCurrentPage(), commodityPage.getPageSize());
        LambdaQueryWrapper<Commodity> lqw = new LambdaQueryWrapper<>();
        // 条件查询
        // 是否删除(0：是，1：否-放入回收站，2：正常)
        lqw.eq(true, Commodity::getIsDelete, 2);
        // 商品名称
        lqw.like(!"".equals(commodityPage.getCommodityName()), Commodity::getCommodityName, commodityPage.getCommodityName());

        if (!"".equals(commodityPage.getBusinessName())) {
            Business business = commodityApiClient.getBusiness(commodityPage.getBusinessName());
            //商家名称
            lqw.eq(!"".equals(commodityPage.getBusinessName()), Commodity::getBusinessId, business.getBusinessId());
        }

        //一级分类名称
        if (!"".equals(commodityPage.getOneClassificationName())) {
            LambdaQueryWrapper<Classification> oneClassificationLQW = new LambdaQueryWrapper<>();
            oneClassificationLQW.eq(!"".equals(commodityPage.getOneClassificationName()), Classification::getClassificationName, commodityPage.getOneClassificationName());
            Classification oneClassification = classificationMapper.selectOne(oneClassificationLQW);
            lqw.eq(!"".equals(commodityPage.getOneClassificationName()), Commodity::getOneClassificationId, oneClassification.getClassificationId());
        }

        if (!"".equals(commodityPage.getTwoClassificationName())) {
            //二级分类名称
            LambdaQueryWrapper<Classification> twoClassificationLQW = new LambdaQueryWrapper<>();
            twoClassificationLQW.eq(!"".equals(commodityPage.getTwoClassificationName()), Classification::getClassificationName, commodityPage.getTwoClassificationName());
            Classification twoClassification = classificationMapper.selectOne(twoClassificationLQW);
            lqw.eq(!"".equals(commodityPage.getTwoClassificationName()), Commodity::getTwoClassificationId, twoClassification.getClassificationId());
        }
        if (!"".equals(commodityPage.getCommodityType())) {
            // 商品类型
            lqw.eq(!"".equals(commodityPage.getCommodityType()), Commodity::getCommodityType, Integer.parseInt(commodityPage.getCommodityType()));
        }
        if (!"".equals(commodityPage.getCommodityStatus())) {
            // 商品状态
            lqw.eq(!"".equals(commodityPage.getCommodityStatus()), Commodity::getCommodityStatus, Integer.parseInt(commodityPage.getCommodityStatus()));
        }
        if (!"".equals(commodityPage.getSalesModel())) {
            //销售模式
            lqw.eq(!"".equals(commodityPage.getSalesModel()), Commodity::getSalesModel, Integer.parseInt(commodityPage.getSalesModel()));
        }
        if (!"".equals(commodityPage.getCommodityRecommend())) {
            //是否推荐
            lqw.eq(!"".equals(commodityPage.getCommodityRecommend()), Commodity::getCommodityRecommend, Integer.parseInt(commodityPage.getCommodityRecommend()));
        }
        //添加时间
        //小于等于
        lqw.ge(!"".equals(commodityPage.getCreateStartTime()), Commodity::getCreateTime, commodityPage.getCreateStartTime());
        // 开始时间 大于等于 数据库存的时间
        lqw.le(!"".equals(commodityPage.getCreateEndTime()), Commodity::getCreateTime, commodityPage.getCreateEndTime());
        //上架时间
        lqw.ge(!"".equals(commodityPage.getPutShelfStartTime()), Commodity::getPutShelfTime, commodityPage.getPutShelfStartTime());
        lqw.le(!"".equals(commodityPage.getPutShelfEndTime()), Commodity::getPutShelfTime, commodityPage.getPutShelfEndTime());

        IPage<Commodity> commodityIPage = commodityMapper.selectPage(page, lqw);
        // if (commodityIPage.getTotal() == 0){
        //     return Result.err("商品数据为空");
        // }
        // 封装数据
        CommodityTdo commodityTdo = new CommodityTdo();
        commodityTdo.setCurrentPage(commodityIPage.getCurrent());
        commodityTdo.setPageSize(commodityIPage.getPages());
        commodityTdo.setSize(commodityIPage.getSize());
        commodityTdo.setTotal(commodityIPage.getTotal());

        List<Commodity> records = commodityIPage.getRecords();
        List<CommodityResords> commodityResordsList = new ArrayList<>();

        records.stream().forEach(c -> {
            CommodityResords r = new CommodityResords();
            // 查询商家名称
            Business businessById = commodityApiClient.getBusinessById(c.getBusinessId());
            if (businessById != null) {
                r.setBusinessName(businessById.getBusinessName());
            }
            //查询分类名称
            LambdaQueryWrapper<Classification> twoWrapper = new LambdaQueryWrapper<>();
            twoWrapper.eq(!"".equals(c.getTwoClassificationId()), Classification::getClassificationId, c.getTwoClassificationId());
            LambdaQueryWrapper<Classification> oneWrapper = new LambdaQueryWrapper<>();
            oneWrapper.eq(!"".equals(c.getOneClassificationId()), Classification::getClassificationId, c.getOneClassificationId());
            Classification one = classificationMapper.selectOne(oneWrapper);
            Classification two = classificationMapper.selectOne(twoWrapper);
            if (one != null) {
                r.setOneClassificationName(one.getClassificationName());
            }
            if (two != null) {
                r.setTwoClassificationName(two.getClassificationName());
            }
            //封装其他数据
            r.setCommodityDescribe(c.getCommodityDescribe());
            r.setCommodityImageUrl(c.getCommodityImageUrl());
            r.setCommodityName(c.getCommodityName());
            r.setCommodityRecommend(c.getCommodityRecommend());
            r.setCommodityStatus(c.getCommodityStatus());
            r.setCommodityStock(c.getCommodityStock());
            r.setCommodityType(c.getCommodityType());
            r.setCommodityWeight(c.getCommodityWeight());
            r.setCreateTime(c.getCreateTime());
            r.setMeterCompany(c.getMeterCompany());
            r.setOffShelfReason(c.getOffShelfReason());
            r.setOffShelfTime(c.getOffShelfTime());
            r.setPutShelfTime(c.getPutShelfTime());
            r.setRetailPrice(c.getRetailPrice());
            r.setSalesModel(c.getSalesModel());
            r.setWholesalePrice(c.getWholesalePrice());
            r.setCommodityId(c.getCommodityId());

            commodityResordsList.add(r);
        });
        commodityTdo.setCommodityResordsList(commodityResordsList);
        return Result.ok(commodityTdo);

    }

    /**
     * 批量发布商品
     *
     * @param commodityBatch 商品批量
     * @return {@link Result}
     */
    @Override
    public Result batchLaunchCommodity(CommodityBatch commodityBatch) {
        AtomicBoolean b = new AtomicBoolean(false);
        commodityBatch.getBatchCommodityId().forEach(id -> {
            LambdaUpdateWrapper<Commodity> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(!"".equals(id), Commodity::getCommodityId, id)
                    .set(!"".equals(id), Commodity::getCommodityStatus, 0);
            updateWrapper.eq(!"".equals(id), Commodity::getCommodityId, id)
                    .set(!"".equals(id), Commodity::getPutShelfTime, TimeUtil.getCurrentTime());
            int i = commodityMapper.update(null, updateWrapper);
            b.set(i > 0);
        });
        return b.get() ? Result.ok("批量上架操作成功") : Result.err("批量上架操作失败");
    }

    /**
     * 批量下架
     *
     * @param commodityBatch 商品批量
     * @return {@link Result}
     */
    @Override
    public Result batchOffShelfCommodity(CommodityBatch commodityBatch) {
        AtomicBoolean b = new AtomicBoolean(false);
        commodityBatch.getBatchCommodityId().forEach(id -> {
            LambdaUpdateWrapper<Commodity> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(!"".equals(id), Commodity::getCommodityId, id)
                    .set(!"".equals(id), Commodity::getCommodityStatus, 1);
            updateWrapper.eq(!"".equals(id), Commodity::getCommodityId, id)
                    .set(!"".equals(id), Commodity::getOffShelfTime, TimeUtil.getCurrentTime());
            updateWrapper.eq(!"".equals(id), Commodity::getCommodityId, id)
                    .set(!"".equals(id), Commodity::getOffShelfReason, "管理员下架");
            int i = commodityMapper.update(null, updateWrapper);
            b.set(i > 0);
        });
        return b.get() ? Result.ok("批量下架操作成功") : Result.err("批量下架操作失败");
    }

    /**
     * 批量推荐商品
     *
     * @param commodityBatch 商品批量
     * @return {@link Result}
     */
    @Override
    public Result batchRecommendationCommodity(CommodityBatch commodityBatch) {
        AtomicBoolean b = new AtomicBoolean(false);
        commodityBatch.getBatchCommodityId().forEach(id -> {
            LambdaUpdateWrapper<Commodity> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(!"".equals(id), Commodity::getCommodityId, id)
                    .set(!"".equals(id), Commodity::getCommodityRecommend, 0);
            int i = commodityMapper.update(null, updateWrapper);
            b.set(i > 0);
        });
        return b.get() ? Result.ok("批量推荐为新品操作成功") : Result.err("批量推荐为新品操作失败");
    }

    /**
     * 批量把推荐商品修改为不推荐上
     *
     * @param commodityBatch 商品批量
     * @return {@link Result}
     */
    @Override
    public Result batchNoRecommendationCommodity(CommodityBatch commodityBatch) {
        AtomicBoolean b = new AtomicBoolean(false);
        commodityBatch.getBatchCommodityId().forEach(id -> {
            LambdaUpdateWrapper<Commodity> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(!"".equals(id), Commodity::getCommodityId, id)
                    .set(!"".equals(id), Commodity::getCommodityRecommend, 1);
            int i = commodityMapper.update(null, updateWrapper);
            b.set(i > 0);
        });
        return b.get() ? Result.ok("批量推荐为新品操作成功") : Result.err("批量推荐为新品操作失败");
    }

    /**
     * 批量购买商品
     *
     * @param commodityBatch 商品批量
     * @return {@link Result}
     */
    @Override
    public Result bulkPurchaseCommodity(CommodityBatch commodityBatch) {
        AtomicBoolean b = new AtomicBoolean(false);
        commodityBatch.getBatchCommodityId().forEach(id -> {
            // 商品库存中增加入库记录
            commodityStockService.increaseProductInventory(id, commodityBatch.getCommodityNumber());
            // 查询当前商品原有的数量
            LambdaQueryWrapper<Commodity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(!"".equals(id), Commodity::getCommodityId, id);
            Commodity commodity = commodityMapper.selectOne(queryWrapper);

            LambdaUpdateWrapper<Commodity> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(!"".equals(id), Commodity::getCommodityId, id)
                    .set(!"".equals(id), Commodity::getCommodityStock, commodity.getCommodityStock() + commodityBatch.getCommodityNumber());
            int i = commodityMapper.update(null, updateWrapper);
            b.set(i > 0);
        });
        return b.get() ? Result.ok("批量进货操作成功") : Result.err("批量进货操作失败");
    }

    /**
     * 放入回收站商品
     *
     * @param commodityBatch 商品批量
     * @return {@link Result}
     */
    @Override
    public Result putInRecycleBinCommodity(CommodityBatch commodityBatch) {
        AtomicBoolean b = new AtomicBoolean(false);
        commodityBatch.getBatchCommodityId().forEach(id -> {
            // 查询当前商品原有的数量
            LambdaQueryWrapper<Commodity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(!"".equals(id), Commodity::getCommodityId, id);
            Commodity commodity = commodityMapper.selectOne(queryWrapper);

            LambdaUpdateWrapper<Commodity> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(!"".equals(id), Commodity::getCommodityId, id)
                    .set(!"".equals(id), Commodity::getIsDelete, 1);
            int i = commodityMapper.update(null, updateWrapper);
            b.set(i > 0);
        });
        return b.get() ? Result.ok("批量放入回收站操作成功") : Result.err("批量放入回收站操作失败");
    }

    /**
     * 商品批量上传
     *
     * @param file 文件
     * @return {@link Result}
     */
    @Override
    public Result commodityBatchUpload(MultipartFile file) {
        try {
            //获取文件后缀
            String extension = "." + FilenameUtils.getExtension(file.getOriginalFilename());
            //生成新的文件名称
            String newFileName = new SimpleDateFormat("mmss").format(new Date()) +
                    UUID.randomUUID().toString().replace("-", "") + extension;
            //目录
            ApplicationHome home = new ApplicationHome(getClass());
            File source = home.getSource();
            String s1 = source.getParentFile().toString() + "/commodity/";

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
            // EasyExcelListener listener = new EasyExcelListener<>();
            CommodityService commodityService = new CommodityServiceImpl();
            EasyExcel.read(absolutePath, ExcelCommodity.class, new CommodityExcelListener(
                            commodityStockService,
                            commodityApiClient,
                            commodityService,
                            classificationMapper))
                    .sheet()
                    .doRead();

            // String key = ClassificationRedis.COMMODITY_EXCEL_LIST + new SimpleDateFormat("mmss").format(new Date()).toString();
            // // 将Excel数据存储
            // redisUtil.lSet(key,
            //         JSON.toJSONString(listener.getCachedDataList()),
            //         ClassificationRedis.COMMODITY_EXCEL_LIST_TTL);
            //
            // // RabbitMQ发送消息存储数据
            // rabbitTemplate.convertAndSend(
            //         CommodityConstant.SAVE_COMMODITY_LIST_QUEUE,
            //         key);

            // 删除服务器文件
            FileUtil.deleteFile(new File(s1));
            return Result.ok("导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.err("导入错误");
        }
    }

    /**
     * 被分类商品id
     *
     * @param classificationId 分类id
     * @return {@link Result}
     */
    @Override
    public Result getCommodityByClassificationId(String classificationId) {
        String key = ClassificationRedis.COMMODITY_CLASSIFICATION_ID_LIST + classificationId;

        List<Object> lGet = redisUtil.lGet(key, 0, -1);
        if (lGet.size() != 0) {
            return Result.ok(commodityJsonUtil.toListBean(lGet, Commodity.class));
        }
        LambdaQueryWrapper<Commodity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!"".equals(classificationId), Commodity::getTwoClassificationId, classificationId);
        //获取上架商品
        wrapper.eq(true, Commodity::getCommodityStatus, 0);
        List<Commodity> commodityList = commodityMapper.selectList(wrapper);

        if (commodityList.size() > 0) {
            redisUtil.lSet(key, commodityList, ClassificationRedis.COMMODITY_CLASSIFICATION_ID_LIST_TTL);
        }
        return Result.ok(commodityList);
    }

    /**
     * 获得商品细节
     *
     * @param commodityId 商品id
     * @return {@link Result}
     */
    @Override
    public Result getCommodityDetail(String commodityId) {
        String key = ClassificationRedis.COMMODITY_DETAIL + commodityId;
        Object o = redisUtil.get(key);
        if (o != null) {
            return Result.ok(commodityDetailTdoJsonUtil.toJavaBean(o, CommodityDetailTdo.class));
        }
        LambdaQueryWrapper<Commodity> commodityLambdaQueryWrapper = new LambdaQueryWrapper<>();
        commodityLambdaQueryWrapper.eq(!"".equals(commodityId), Commodity::getCommodityId, commodityId);
        Commodity commodity = commodityMapper.selectOne(commodityLambdaQueryWrapper);

        // 获取该商品的商家信息
        Business business = commodityApiClient.getBusinessById(commodity.getBusinessId());
        //  获取该商品的评价信息
        LambdaQueryWrapper<CommodityComment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!"".equals(commodity.getCommodityId()),CommodityComment::getCommodityId,commodity.getCommodityId());
        List<CommodityComment> selectList = commodityCommentMapper.selectList(wrapper);

        CommodityDetailTdo commodityDetailTdo = new CommodityDetailTdo();
        commodityDetailTdo.setCommodity(commodity);
        commodityDetailTdo.setBusiness(business);
        commodityDetailTdo.setCommodityCommentList(selectList);

        redisUtil.set(key, JSON.toJSONString(commodityDetailTdo), ClassificationRedis.COMMODITY_DETAIL_TTL);
        return Result.ok(commodityDetailTdo);
    }

    /**
     * 获得商品推荐
     *
     * @return {@link Result}
     */
    @Override
    public Result getCommodityRecommend() {
        return Result.ok(commodityMapper.getCommodityRecommend());
    }

    /**
     * 要旋转图商品
     *
     * @return {@link Result}
     */
    @Override
    public Result getRotationChartCommodity() {
        return Result.ok(commodityMapper.getRotationChartCommodity());
    }

    /**
     * 得到指数展示商品
     *
     * @param currentPage 当前页面
     * @param pageSize    页面大小
     * @return {@link Result}
     */
    @Override
    public Result getIndexShowCommodity(Integer currentPage, Integer pageSize) {
        IPage<Commodity> page = new Page<Commodity>(currentPage, pageSize);
        LambdaQueryWrapper<Commodity> wrapper = new LambdaQueryWrapper<>();
        //销售状态为上架中
        wrapper.eq(true, Commodity::getCommodityStatus, 0);

        return Result.ok(commodityMapper.selectPage(page, wrapper));
    }

    /**
     * 通过id获取商品
     *
     * @param commodityId 商品id
     * @return {@link Commodity}
     */
    @Override
    public Commodity getCommodityById(String commodityId) {
        LambdaQueryWrapper<Commodity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!"".equals(commodityId), Commodity::getCommodityId, commodityId);
        return commodityMapper.selectOne(wrapper);
    }

    /**
     * 被商业商品id
     *
     * @param businessId 业务标识
     * @return {@link List}<{@link Commodity}>
     */
    @Override
    public List<Commodity> getCommodityByBusinessId(String businessId) {
        LambdaQueryWrapper<Commodity> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(!"".equals(businessId), Commodity::getBusinessId, businessId);
        //状态-销售中
        wrapper.eq(true, Commodity::getCommodityStatus, 0);
        //没有删除
        wrapper.eq(true, Commodity::getIsDelete, 2);
        //使用last方法拼接sql语句
        wrapper.last(true, "limit 3");
        return commodityMapper.selectList(wrapper);
    }

    /**
     * 获得商品列表
     *
     * @return {@link List}<{@link Commodity}>
     */
    @Override
    public List<Commodity> getCommodityList() {
        LambdaQueryWrapper<Commodity> wrapper = new LambdaQueryWrapper<>();

        //状态-销售中
        wrapper.eq(true, Commodity::getCommodityStatus, 0);
        //没有删除
        wrapper.eq(true, Commodity::getIsDelete, 2);
        //使用last方法拼接sql语句
        wrapper.last(true, "limit 3");

        return commodityMapper.selectList(wrapper);
    }

    /**
     * 由业务id获取商品页面
     *
     * @param businessId  业务标识
     * @param currentPage 当前页面
     * @param pageSize    页面大小
     * @return {@link Result}
     */
    @Override
    public Result getCommodityByBusinessIdPage(String businessId, Integer currentPage, Integer pageSize) {
        Page<Commodity> page = new Page<Commodity>(currentPage, pageSize);

        LambdaQueryWrapper<Commodity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(businessId), Commodity::getBusinessId, businessId);

        //状态-销售中
        queryWrapper.eq(true, Commodity::getCommodityStatus, 0);
        //没有删除
        queryWrapper.eq(true, Commodity::getIsDelete, 2);

        return Result.ok(commodityMapper.selectPage(page, queryWrapper));
    }

    /**
     * 扣除库存商品
     *
     * @param orderNumber 订单号
     * @param orderId     订单id
     */
    @Override
    public void deductionInventoryCommodity(String orderNumber, String orderId) {
        List<Order> list = orderApiClient.getOrderByNumberId(orderNumber, orderId);
        list.forEach(o -> {
            String commodityId = o.getCommodityId();
            Integer purchaseQuantity = o.getPurchaseQuantity();

            Commodity commodity = getCommodityById(commodityId);
            LambdaUpdateWrapper<Commodity> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(!"".equals(commodityId), Commodity::getCommodityId, commodityId)
                    .set(purchaseQuantity > 0, Commodity::getCommodityStock, commodity.getCommodityStock() - purchaseQuantity);

            commodityMapper.update(null, updateWrapper);

            // 生成商品库存扣减单
            commodityStockService.deductionInventoryGoods(commodityId, orderNumber, purchaseQuantity);
        });

    }

    /**
     * 搜索商品
     *
     * @param commodityName 商品名称
     * @return {@link Result}
     */
    @Override
    public Result searchCommodity(String commodityName) {
        LambdaQueryWrapper<Commodity> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(!"".equals(commodityName),Commodity::getCommodityName,commodityName);

        //状态-销售中
        wrapper.eq(true, Commodity::getCommodityStatus, 0);
        //没有删除
        wrapper.eq(true, Commodity::getIsDelete, 2);
        //使用last方法拼接sql语句
        wrapper.last(true, "limit 10");

        return Result.ok(commodityMapper.selectList(wrapper));
    }

    /**
     * 得到畅销商品
     *
     * @return {@link Result}
     */
    @Override
    public Result getBestSellersCommodity() {
        return Result.ok(commodityMapper.getBestSellersCommodity());
    }

    /**
     * 让时间杀了商品
     *
     * @return {@link List}<{@link Commodity}>
     */
    @Override
    public List<Commodity> getTimeKillCommodity() {
        return commodityMapper.getTimeKillCommodity();
    }

    /**
     * 获得促销活动商品
     *
     * @param activityCommodity 活动商品
     * @return {@link List}<{@link Commodity}>
     */
    @Override
    public List<Commodity> getPromotionActivityCommodity(ActivityCommodity activityCommodity) {
        List<Commodity> list = new ArrayList<>();
        activityCommodity.getCommodityId().forEach(c -> {
            LambdaQueryWrapper<Commodity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(!"".equals(c),Commodity::getCommodityId,c);
            list.add(commodityMapper.selectOne(wrapper));
        });
        return list;
    }

    /**
     * 获得促销活动商品限制三个
     *
     * @param activityCommodity 活动商品
     * @return {@link List}<{@link Commodity}>
     */
    @Override
    public List<Commodity> getPromotionActivityCommodityLimitThree(ActivityCommodity activityCommodity) {
        List<Commodity> list = new ArrayList<>();
        activityCommodity.getCommodityId().forEach(c -> {
            list.add(commodityMapper.getPromotionActivityCommodityLimitThree(c));
        });
        return list;
    }

    /**
     * 获得商品数据vlist
     *
     * @return {@link Result}
     */
    @Override
    public Result getCommodityDataVList() {
        List<Commodity> commodityList = commodityMapper.getCommodityDataVList();
        List<String[]> list = new ArrayList<String[]>();
        commodityList.forEach(c -> {
            LambdaQueryWrapper<Classification> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(!"".equals(c.getTwoClassificationId()),Classification::getClassificationId,c.getTwoClassificationId());
            Classification one = classificationMapper.selectOne(wrapper);

            Business business = businessApiClient.getBusinessById(c.getBusinessId());
            String[] str = new String[5];
            str[0] = c.getCommodityName().substring(0,2);
            str[1] = one.getClassificationName();
            str[2] = c.getCommodityStock().toString() + c.getMeterCompany();
            if (c.getSalesModel() == 0){
                //零售型
                str[3] = c.getRetailPrice();
            }else{
                //批发型
                str[3] = c.getWholesalePrice();
            }
            str[4] = business.getCalloutContent();
            list.add(str);
        });

        return Result.ok(list);
    }

    /**
     * 得到分类商品比例
     *
     * @return {@link Result}
     */
    @Override
    public Result getClassificationCommodityProportion() {
        return Result.ok(commodityMapper.getClassificationCommodityProportion());
    }

}
