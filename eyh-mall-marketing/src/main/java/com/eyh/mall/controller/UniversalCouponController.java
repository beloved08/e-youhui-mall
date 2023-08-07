package com.eyh.mall.controller;

import com.eyh.mall.entity.MerchantCoupon;
import com.eyh.mall.entity.UniversalCoupon;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.UniversalCouponVoPage;
import com.eyh.mall.service.UniversalCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 李平
 * @NAME UniversalCouponController
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-03-29 10:39:17
 * @Description 通用优惠券控制类
 */
@RestController
@RequestMapping("/marketing")
public class UniversalCouponController {

    @Autowired
    private UniversalCouponService universalCouponService;

    /**
     * 添加通用券
     *
     * @param universalCoupon 普遍优惠券
     * @return {@link Result}
     */
    @PostMapping("/addUniversalCoupon")
    public Result addUniversalCoupon(@RequestBody UniversalCoupon universalCoupon){
        return universalCouponService.addUniversalCoupon(universalCoupon);
    }

    /**
     * 得到所有通用优惠券页面
     *
     * @param universalCouponVoPage 通用券vo页面
     * @return {@link Result}
     */
    @PostMapping("/getAllUniversalCouponPage")
    public Result getAllUniversalCouponPage(@RequestBody UniversalCouponVoPage universalCouponVoPage){
        return universalCouponService.getAllUniversalCouponPage(universalCouponVoPage);
    }

    /**
     * 删除通用优惠券通过id
     *
     * @param universalCouponId 通用券id
     * @return {@link Result}
     */
    @GetMapping("/deleteUniversalCoupon/{universalCouponId}")
    public Result deleteUniversalCouponById(@PathVariable String universalCouponId){
        return universalCouponService.deleteUniversalCoupon(universalCouponId);
    }

    /**
     * 选择通用券
     *
     * @return {@link Result}
     */
    @GetMapping("/selectUniversalCoupon")
    public Result selectUniversalCoupon(){
        return universalCouponService.selectUniversalCoupon();
    }

    /**
     * 选择通用优惠券通过id
     *
     * @param couponId 优惠券id
     * @return {@link UniversalCoupon}
     */
    @GetMapping("/selectUniversalCouponById/{couponId}")
    public UniversalCoupon selectUniversalCouponById(@PathVariable String couponId){
        return universalCouponService.selectUniversalCouponById(couponId);
    }

}
