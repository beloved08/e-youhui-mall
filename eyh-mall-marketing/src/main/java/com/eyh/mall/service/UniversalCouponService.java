package com.eyh.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyh.mall.entity.MerchantCoupon;
import com.eyh.mall.entity.UniversalCoupon;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.UniversalCouponVoPage;

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
public interface UniversalCouponService extends IService<UniversalCoupon> {

    /**
     * 添加通用券
     *
     * @param universalCoupon 普遍优惠券
     * @return {@link Result}
     */
    Result addUniversalCoupon(UniversalCoupon universalCoupon);

    /**
     * 得到所有通用优惠券页面
     *
     * @param universalCouponVoPage 通用券vo页面
     * @return {@link Result}
     */
    Result getAllUniversalCouponPage(UniversalCouponVoPage universalCouponVoPage);

    /**
     * 删除通用券
     *
     * @param universalCouponId 通用券id
     * @return {@link Result}
     */
    Result deleteUniversalCoupon(String universalCouponId);

    /**
     * 选择通用券
     *
     * @return {@link Result}
     */
    Result selectUniversalCoupon();

    /**
     * 选择通用优惠券通过id
     *
     * @param couponId 优惠券id
     * @return {@link UniversalCoupon}
     */
    UniversalCoupon selectUniversalCouponById(String couponId);

    /**
     * 更新环球优惠券
     */
    void updateUniversalCoupon(String couponId);
}
