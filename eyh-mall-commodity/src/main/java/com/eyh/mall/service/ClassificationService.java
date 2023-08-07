package com.eyh.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyh.mall.entity.Classification;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.ClassificationPage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 李平
 * @Date 2023-2-18
 */
public interface ClassificationService extends IService<Classification> {

    /**
     * 添加商品分类
     *
     * @param classification 分类签证官
     * @return {@link Result}
     */
    Result addCommodityClassification(Classification classification);


    /**
     * 上传分类图标
     *
     * @param file 文件
     * @param name 名字
     * @return {@link Result}
     */
    Result uploadClassificationIcon(MultipartFile file, String name);

    /**
     * 得到父id
     *
     * @param parentName 父母名字
     * @return {@link Result}
     */
    Result getParentId(String parentName);

    /**
     * 得到父母名单
     *
     * @param parentNameSearch 父母名字搜索
     * @return {@link Result}
     */
    Result getParentNameList(String parentNameSearch);

    /**
     * 得到分类页面
     *
     * @param classificationPage 分类页面
     * @return {@link Result}
     */
    Result getClassificationByPage(ClassificationPage classificationPage);

    /**
     * 删除分类按年级id
     *
     * @param classificationGrade 分类等级
     * @param classificationId    分类id
     * @return {@link Result}
     */
    Result deleteClassificationByGradeId(Integer classificationGrade, String classificationId);

    /**
     * 批leade在
     * 批量导入
     *
     * @param file  文件
     * @param grade 年级
     * @return {@link Result}
     */
    Result batchLeadeIn(MultipartFile file,String grade);

    /**
     * 根据一级分类名称获取所有的二级分类名称
     *
     * @param oneLevelName 一个级别名称
     * @return {@link Result}
     */
    Result getTwoLevelNameList(String oneLevelName);

    /**
     * 得到分类情况
     *
     * @return {@link Result}
     */
    Result getClassificationSituation();

    /**
     * 得到一个分类列表
     *
     * @return {@link List}<{@link Classification}>
     */
    List<Classification> getAllOneClassificationList();

}
