package com.eyh.mall.controller;

import com.eyh.mall.entity.MerchantCoupon;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.MerchantCouponPage;
import com.eyh.mall.entity.vo.MerchantCouponVo;
import com.eyh.mall.entity.vo.UniversalCouponVoPage;
import com.eyh.mall.service.MerchantCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 李平
 * @NAME MerchantCouponController
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-03-29 15:40:44
 * @Description 商家优惠券控制层
 */
@RestController
@RequestMapping("/marketing")
public class MerchantCouponController {

    @Autowired
    private MerchantCouponService merchantCouponService;

    /**
     * 添加商户优惠券
     *
     * @param merchantCouponVo 商家优惠券签证官
     * @return {@link Result}
     */
    @PostMapping("/addMerchantCoupon")
    public Result addMerchantCoupon(@RequestBody MerchantCouponVo merchantCouponVo){
        return merchantCouponService.addMerchantCoupon(merchantCouponVo);
    }

    /**
     * 得到所有商家优惠券页面
     *
     * @param merchantCouponPage 商家优惠券页面
     * @return {@link Result}
     */
    @PostMapping("/getAllMerchantCouponPage")
    public Result getAllMerchantCouponPage(@RequestBody MerchantCouponPage merchantCouponPage){
        return merchantCouponService.getAllMerchantCouponPage(merchantCouponPage);
    }

    /**
     * 删除商户优惠券通过id
     *
     * @param merchantCouponId 商家优惠券id
     * @return {@link Result}
     */
    @GetMapping("/deleteMerchantCoupon/{merchantCouponId}")
    public Result deleteMerchantCouponById(@PathVariable String merchantCouponId){
        return merchantCouponService.deleteMerchantCouponById(merchantCouponId);
    }

    /**
     * 选择所有商家优惠券
     *
     * @return {@link Result}
     */
    @GetMapping("/selectAllMerchantCoupon")
    public Result selectAllMerchantCoupon(){
        return merchantCouponService.selectAllMerchantCoupon();
    }

    /**
     * 选择商家优惠券通过id
     *
     * @param couponId 优惠券id
     * @return {@link Result}
     */
    @GetMapping("/selectMerchantCouponById/{couponId}")
    public MerchantCoupon selectMerchantCouponById(@PathVariable String couponId){
        return merchantCouponService.selectMerchantCouponById(couponId);
    }
}
