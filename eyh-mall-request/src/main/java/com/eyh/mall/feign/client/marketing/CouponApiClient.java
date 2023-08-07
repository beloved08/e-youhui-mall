package com.eyh.mall.feign.client.marketing;

import com.eyh.mall.feign.config.FeignConfig;
import com.eyh.mall.feign.entity.MerchantCoupon;
import com.eyh.mall.feign.entity.UniversalCoupon;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author 李平
 * @NAME CouponApiClient
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-03-29 20:32:32
 * @Description 营销模块优惠券接口抽取类
 */
@FeignClient(value = "eyh-mall-marketing", configuration = FeignConfig.class)
public interface CouponApiClient {

    /**
     * 选择商家优惠券通过id
     *
     * @param couponId 优惠券id
     * @return {@link MerchantCoupon}
     */
    @GetMapping("/marketing/selectMerchantCouponById/{couponId}")
    MerchantCoupon selectMerchantCouponById(@PathVariable String couponId);


    /**
     * 选择通用优惠券通过id
     *
     * @param couponId 优惠券id
     * @return {@link UniversalCoupon}
     */
    @GetMapping("/marketing/selectUniversalCouponById/{couponId}")
    UniversalCoupon selectUniversalCouponById(@PathVariable String couponId);

}
