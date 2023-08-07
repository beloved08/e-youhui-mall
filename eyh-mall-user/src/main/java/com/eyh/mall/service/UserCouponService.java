package com.eyh.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyh.mall.entity.UserCoupon;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.UserCouponVo;

/**
 * @Author 李平
 * @NAME UserCouponService
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-03-29 20:16:47
 * @Description 用户优惠券业务接口层
 */
public interface UserCouponService extends IService<UserCoupon> {

    /**
     * 画优惠券
     *
     * @param userCouponVo 用户优惠券签证官
     * @return {@link Result}
     */
    Result drawCoupon(UserCouponVo userCouponVo);

    /**
     * 获取用户优惠券计数
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    Result getUserCouponCount(String userId);

    /**
     * 得到用户优惠券列表
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    Result getUserCouponList(String userId);

    /**
     * 通过id获取用户优惠券
     *
     * @param couponId 优惠券id
     * @return {@link UserCoupon}
     */
    UserCoupon getUserCouponById(String couponId);

    /**
     * 更新用户使用优惠券
     *
     * @param couponId 优惠券id
     */
    void updateUserCouponIsUse(String couponId);
}
