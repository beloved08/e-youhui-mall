package com.eyh.mall.controller;

import com.eyh.mall.entity.Classification;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.ClassificationPage;
import com.eyh.mall.service.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 李平
 * @Date 2023-1-31
 */
@RestController
@RequestMapping("/commodity")
public class ClassificationController {

    @Autowired
    private ClassificationService classificationService;

    /**
     * 上传分类图标
     * 上传店铺标志
     * 上传图片至服务器和OSS
     *
     * @param file 文件
     * @param name 名字
     * @return {@link Result}
     */
    @PostMapping(value = "/uploadClassificationIcon/{name}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result uploadClassificationIcon(@RequestParam("classificationIcon") MultipartFile file, @PathVariable String name) {
        return classificationService.uploadClassificationIcon(file,name);
    }

    /**
     * 添加商品分类
     *
     * @param classification 分类签证官
     * @return {@link Result}
     */
    @PostMapping("/addCommodityClassification")
    public Result addCommodityClassification(@RequestBody Classification classification){
        return classificationService.addCommodityClassification(classification);
    }

    /**
     * 得到父id
     * 添加商品分类
     *
     * @param parentName 父母名字
     * @return {@link Result}
     */
    @GetMapping("/getParentId")
    public Result getParentId(@RequestParam(value = "parentName",required = true) String parentName){
        return classificationService.getParentId(parentName);
    }

    /**
     * 得到父母名单
     * 得到父id
     * 添加商品分类
     *
     * @param parentNameSearch 父母名字搜索
     * @return {@link Result}
     */
    @GetMapping("/getParentNameList")
    public Result getParentNameList(@RequestParam(value = "parentNameSearch",required = false) String parentNameSearch){
        return classificationService.getParentNameList(parentNameSearch);
    }

    /**
     * 根据一级分类名称获取所有的二级分类名称
     *
     * @param oneLevelName 一个级别名称
     * @return {@link Result}
     */
    @GetMapping("/getTwoLevelNameList")
    public Result getTwoLevelNameList(@RequestParam(value = "oneLevelName",required = true) String oneLevelName){
        return classificationService.getTwoLevelNameList(oneLevelName);
    }

    /**
     * 得到分类页面
     *
     * @param classificationPage 分类页面
     * @return {@link Result}
     */
    @PostMapping("/getClassificationByPage")
    public Result getClassificationByPage(@RequestBody ClassificationPage classificationPage){
        return classificationService.getClassificationByPage(classificationPage);
    }

    /**
     * 删除分类
     *
     * @param classificationGrade 分类等级
     * @param classificationId    分类id
     * @return {@link Result}
     */
    @GetMapping("/deleteClassification/{classificationGrade}/{classificationId}")
    public Result deleteClassification(
            @PathVariable(value = "classificationGrade") Integer classificationGrade,
            @PathVariable(value = "classificationId") String classificationId){

        return classificationService.deleteClassificationByGradeId(classificationGrade, classificationId);
    }

    /**
     * 批leade在
     * 上传文件
     *
     * @param file  文件
     * @param grade 年级
     * @return {@link Result}
     */
    @PostMapping(value = "/uploadBatchLeade/{grade}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result batchLeadeIn(@RequestParam("classificationBatchLevel") MultipartFile file,
                               @PathVariable(value = "grade") String grade) {
        return classificationService.batchLeadeIn(file, grade);
    }

    /**
     * 得到分类情况
     *
     * @return {@link Result}
     */
    @GetMapping("/getClassificationSituation")
    public Result getClassificationSituation(){
        return classificationService.getClassificationSituation();
    }

    /**
     * 得到一级分类列表
     *
     * @return {@link List}<{@link Classification}>
     */
    @GetMapping("/getAllOneClassificationList")
    public List<Classification> getAllOneClassificationList(){
        return classificationService.getAllOneClassificationList();
    }

}
