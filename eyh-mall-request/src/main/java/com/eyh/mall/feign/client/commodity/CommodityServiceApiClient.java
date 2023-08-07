package com.eyh.mall.feign.client.commodity;

import com.eyh.mall.entity.merchantStores.Business;
import com.eyh.mall.feign.config.FeignConfig;
import com.eyh.mall.feign.entity.ActivityCommodity;
import com.eyh.mall.feign.entity.Classification;
import com.eyh.mall.feign.entity.Commodity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 李平
 * @NAME CommodityApiClient
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期六
 * @Date 2023-03-18 11:26:46
 * @Description 商品服务feign api接口
 */
@FeignClient(value = "eyh-mall-commodity", configuration = FeignConfig.class)
public interface CommodityServiceApiClient {

    @GetMapping("/commodity/getCommodityById/{commodityId}")
    Commodity getCommodityById(@PathVariable String commodityId);

    @GetMapping("/commodity/getCommodityByBusinessId/{businessId}")
    List<Commodity> getCommodityByBusinessId(@PathVariable String businessId);

    /**
     * 获得商品列表
     *
     * @return {@link List}<{@link Commodity}>
     */
    @GetMapping("/commodity/getCommodityList")
    List<Commodity> getCommodityList();

    /**
     * 让时间杀了商品
     *
     * @return {@link List}<{@link Commodity}>
     */
    @GetMapping("/commodity/getTimeKillCommodity")
    List<Commodity> getTimeKillCommodity();

    /**
     * 获得促销活动商品
     *
     * @param activityCommodity 活动商品
     * @return {@link List}<{@link Commodity}>
     */
    @PostMapping("/commodity/getPromotionActivityCommodity")
    List<Commodity> getPromotionActivityCommodity(@RequestBody ActivityCommodity activityCommodity);

    /**
     * 获得促销活动商品限制三个
     *
     * @param activityCommodity 活动商品
     * @return {@link List}<{@link Commodity}>
     */
    @PostMapping("/commodity/getPromotionActivityCommodityLimitThree")
    List<Commodity> getPromotionActivityCommodityLimitThree(@RequestBody ActivityCommodity activityCommodity);

    /**
     * 得到一个分类列表
     *
     * @return {@link List}<{@link Classification}>
     */
    @GetMapping("/getAllOneClassificationList")
    List<Classification> getAllOneClassificationList();

}
