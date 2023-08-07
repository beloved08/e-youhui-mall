package com.eyh.mall.controller;

import com.eyh.mall.entity.Commodity;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.*;
import com.eyh.mall.service.CommodityService;
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
public class CommodityController {

    @Autowired
    private CommodityService commodityService;

    /**
     * 添加商品
     *
     * @param commodityVo 商品签证官
     * @return {@link Result}
     */
    @PostMapping("/addCommodity")
    public Result addCommodity(@RequestBody CommodityVo commodityVo){
        return commodityService.addCommodity(commodityVo);
    }

    /**
     * 上传商品图片
     *
     * @param file          文件
     * @param commodityName 商品名称
     * @return {@link Result}
     */
    @PostMapping(value = "/uploadCommodityImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result uploadCommodityImage(
            @RequestParam("commodityServiceImage") MultipartFile file,
            @RequestParam(value = "businessName",required = true) String businessName,
            @RequestParam(value = "oneClassificationName",required = true) String oneClassificationName,
            @RequestParam(value = "twoClassificationName",required = true) String twoClassificationName,
            @RequestParam(value = "commodityName",required = true) String commodityName) {
        return commodityService.uploadCommodityImage(file,businessName,oneClassificationName,twoClassificationName,commodityName);
    }

    /**
     * 得到分类页面
     *
     * @param commodityPage 分类页面
     * @return {@link Result}
     */
    @PostMapping("/getCommodityByPage")
    public Result getCommodityByPage(@RequestBody CommodityPage commodityPage){
        return commodityService.getCommodityByPage(commodityPage);
    }

    /**
     * 批量发布商品
     *
     * @param commodityBatch 商品批量
     * @return {@link Result}
     */
    @PostMapping("/batchLaunchCommodity")
    public Result batchLaunchCommodity(@RequestBody CommodityBatch commodityBatch){
        return commodityService.batchLaunchCommodity(commodityBatch);
    }

    /**
     * 批量下架
     *
     * @param commodityBatch 商品批量
     * @return {@link Result}
     */
    @PostMapping("/batchOffShelfCommodity")
    public Result batchOffShelfCommodity(@RequestBody CommodityBatch commodityBatch){
        return commodityService.batchOffShelfCommodity(commodityBatch);
    }

    /**
     * 批量推荐商品为新品
     *
     * @param commodityBatch 商品批量
     * @return {@link Result}
     */
    @PostMapping("/batchRecommendationCommodity")
    public Result batchRecommendationCommodity(@RequestBody CommodityBatch commodityBatch){
        return commodityService.batchRecommendationCommodity(commodityBatch);
    }

    /**
     * 批量把推荐商品修改为不推荐上
     *
     * @param commodityBatch 商品批量
     * @return {@link Result}
     */
    @PostMapping("/batchNoRecommendationCommodity")
    public Result batchNoRecommendationCommodity(@RequestBody CommodityBatch commodityBatch){
        return commodityService.batchNoRecommendationCommodity(commodityBatch);
    }

    /**
     * 批量购买商品
     *
     * @param commodityBatch 商品批量
     * @return {@link Result}
     */
    @PostMapping("/bulkPurchaseCommodity")
    public Result bulkPurchaseCommodity(@RequestBody CommodityBatch commodityBatch){
        return commodityService.bulkPurchaseCommodity(commodityBatch);
    }

    /**
     * 放入回收站商品
     *
     * @param commodityBatch 商品批量
     * @return {@link Result}
     */
    @PostMapping("/putInRecycleBinCommodity")
    public Result putInRecycleBinCommodity(@RequestBody CommodityBatch commodityBatch){
        return commodityService.putInRecycleBinCommodity(commodityBatch);
    }

    /**
     * 商品批量商品数据
     *
     * @param file 文件
     * @return {@link Result}
     */
    @PostMapping(value = "/uploadBatchCommodity", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result commodityBatchLeadeIn(@RequestParam("commodityBatchData") MultipartFile file) {
        return commodityService.commodityBatchUpload(file);
    }

    /**
     * 被分类商品id
     *
     * @param classificationId 分类id
     * @return {@link Result}
     */
    @GetMapping("/getCommodityByClassificationId/{classificationId}")
    public Result getCommodityByClassificationId(@PathVariable String classificationId){
        return commodityService.getCommodityByClassificationId(classificationId);
    }

    /**
     * 获得商品细节
     *
     * @param commodityId 商品id
     * @return {@link Result}
     */
    @GetMapping("/getCommodityDetail/{commodityId}")
    public Result getCommodityDetail(@PathVariable String commodityId){
        return commodityService.getCommodityDetail(commodityId);
    }

    /**
     * 通过id获取商品
     *
     * @param commodityId 商品id
     * @return {@link Result}
     */
    @GetMapping("/getCommodityById/{commodityId}")
    public Commodity getCommodityById(@PathVariable String commodityId){
        return commodityService.getCommodityById(commodityId);
    }

    /**
     * 获得商品推荐
     *
     * @return {@link Result}
     */
    @GetMapping("/getRecommendCommodity")
    public Result getCommodityRecommend(){
        return commodityService.getCommodityRecommend();
    }

    /**
     * 得到畅销商品
     *
     * @return {@link Result}
     */
    @GetMapping("/getBestSellersCommodity")
    public Result getBestSellersCommodity(){
        return commodityService.getBestSellersCommodity();
    }

    /**
     * 要旋转图商品
     *
     * @return {@link Result}
     */
    @GetMapping("/getRotationChartCommodity")
    public Result getRotationChartCommodity(){
        return commodityService.getRotationChartCommodity();
    }

    /**
     * 得到指数展示商品
     * 得到首页展示的商品
     *
     * @param currentPage 当前页面
     * @param pageSize    页面大小
     * @return {@link Result}
     */
    @GetMapping("/getIndexShowCommodity/{currentPage}/{pageSize}")
    public Result getIndexShowCommodity(@PathVariable Integer currentPage, @PathVariable Integer pageSize){
        return commodityService.getIndexShowCommodity(currentPage,pageSize);
    }

    /**
     * 被商业商品id
     *
     * @param businessId 业务标识
     * @return {@link List}<{@link Commodity}>
     */
    @GetMapping("/getCommodityByBusinessId/{businessId}")
    public List<Commodity> getCommodityByBusinessId(@PathVariable String businessId){
        return commodityService.getCommodityByBusinessId(businessId);
    }

    /**
     * 由业务id获取商品页面
     *
     * @param businessId  业务标识
     * @param currentPage 当前页面
     * @param pageSize    页面大小
     * @return {@link Result}
     */
    @GetMapping("/getCommodityByBusinessIdPage/{businessId}/{currentPage}/{pageSize}")
    public Result getCommodityByBusinessIdPage(@PathVariable String businessId,
                                               @PathVariable Integer currentPage,
                                               @PathVariable Integer pageSize){
        return commodityService.getCommodityByBusinessIdPage(businessId,currentPage,pageSize);
    }

    /**
     * 获得商品列表
     *
     * @return {@link List}<{@link Commodity}>
     */
    @GetMapping("/getCommodityList")
    public List<Commodity> getCommodityList(){
        return commodityService.getCommodityList();
    }

    /**
     * 搜索商品
     *
     * @param commodityName 商品名称
     * @return {@link Result}
     */
    @GetMapping("/searchCommodity/{commodityName}")
    public Result searchCommodity(@PathVariable String commodityName){
        return commodityService.searchCommodity(commodityName);
    }

    /**
     * 让时间杀了商品
     *
     * @return {@link List}<{@link Commodity}>
     */
    @GetMapping("/getTimeKillCommodity")
    public List<Commodity> getTimeKillCommodity(){
        return commodityService.getTimeKillCommodity();
    }

    /**
     * 获得促销活动商品
     *
     * @param activityCommodity 活动商品
     * @return {@link List}<{@link Commodity}>
     */
    @PostMapping("/getPromotionActivityCommodity")
    public List<Commodity> getPromotionActivityCommodity(@RequestBody ActivityCommodity activityCommodity){
        return commodityService.getPromotionActivityCommodity(activityCommodity);
    }

    /**
     * 获得促销活动商品限制三个
     *
     * @param activityCommodity 活动商品
     * @return {@link List}<{@link Commodity}>
     */
    @PostMapping("/getPromotionActivityCommodityLimitThree")
    public List<Commodity> getPromotionActivityCommodityLimitThree(@RequestBody ActivityCommodity activityCommodity){
        return commodityService.getPromotionActivityCommodityLimitThree(activityCommodity);
    }

    /**
     * 获得商品数据vlist
     *
     * @return {@link Result}
     */
    @GetMapping("/getCommodityDataVList")
    public Result getCommodityDataVList(){
        return commodityService.getCommodityDataVList();
    }

    /**
     * 得到分类商品比例
     *
     * @return {@link Result}
     */
    @GetMapping("/getClassificationCommodityProportion")
    public Result getClassificationCommodityProportion(){
        return commodityService.getClassificationCommodityProportion();
    }

}
