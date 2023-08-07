package com.eyh.mall.controller;

import com.eyh.mall.entity.UserCoupon;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.UserCouponVo;
import com.eyh.mall.service.UserCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 李平
 * @NAME UserCouponController
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-03-29 20:19:07
 * @Description 用户优惠券控制类
 */
@RestController
@RequestMapping("/user")
public class UserCouponController {

    @Autowired
    private UserCouponService userCouponService;

    /**
     * 画优惠券
     *
     * @param userCouponVo 用户优惠券签证官
     * @return {@link Result}
     */
    @PostMapping("/drawCoupon")
    public Result drawCoupon(@RequestBody UserCouponVo userCouponVo){
        return userCouponService.drawCoupon(userCouponVo);
    }

    /**
     * 获取用户优惠券计数
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @GetMapping("/getUserCouponCount/{userId}")
    public Result getUserCouponCount(@PathVariable String userId){
        return userCouponService.getUserCouponCount(userId);
    }

    /**
     * 通过id获取用户优惠券
     * 获取用户优惠券计数
     *
     * @param couponId 优惠券id
     * @return {@link UserCoupon}
     */
    @GetMapping("/getUserCouponById/{couponId}")
    public UserCoupon getUserCouponById(@PathVariable String couponId){
        return userCouponService.getUserCouponById(couponId);
    }

    /**
     * 得到用户优惠券列表
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @GetMapping("/getUserCouponList/{userId}")
    public Result getUserCouponList(@PathVariable String userId){
        return userCouponService.getUserCouponList(userId);
    }

}
