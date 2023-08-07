package com.eyh.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyh.mall.entity.MerchantCoupon;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.MerchantCouponPage;
import com.eyh.mall.entity.vo.MerchantCouponVo;

/**
 * @Author 李平
 * @NAME UniversalCouponService
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-03-29 10:37:34
 * @Description 通用优惠券业务接口层
 */
public interface MerchantCouponService extends IService<MerchantCoupon> {

    /**
     * 添加商户优惠券
     *
     * @param merchantCouponVo 商家优惠券签证官
     * @return {@link Result}
     */
    Result addMerchantCoupon(MerchantCouponVo merchantCouponVo);

    /**
     * 得到所有商家优惠券页面
     *
     * @param merchantCouponPage 商家优惠券页面
     * @return {@link Result}
     */
    Result getAllMerchantCouponPage(MerchantCouponPage merchantCouponPage);

    /**
     * 删除商户优惠券通过id
     *
     * @param merchantCouponId 商家优惠券id
     * @return {@link Result}
     */
    Result deleteMerchantCouponById(String merchantCouponId);

    /**
     * 选择所有商家优惠券
     *
     * @return {@link Result}
     */
    Result selectAllMerchantCoupon();

    /**
     * 选择商家优惠券通过id
     *
     * @param couponId 优惠券id
     * @return {@link Result}
     */
    MerchantCoupon selectMerchantCouponById(String couponId);


    /**
     * 更新商户优惠券
     *
     * @param couponId 优惠券id
     */
    void updateMerchantCoupon(String couponId);
}
