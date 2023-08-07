package com.eyh.mall.feign.client.user;

import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.user.Admin;
import com.eyh.mall.feign.config.FeignConfig;
import com.eyh.mall.feign.entity.MallUser;
import com.eyh.mall.feign.entity.UserAddress;
import com.eyh.mall.feign.entity.UserCoupon;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 李平
 * @Date 2023-2-14
 */
@FeignClient(value = "eyh-mall-user", configuration = FeignConfig.class)
public interface UserApiClient {

    /**
     * 根据账号查询
     * @param account
     * @return Admin
     */
    @GetMapping("/user/selectAdmin")
    Admin selectAdmin(@RequestParam String account);

    /**
     * 通过openid获取用户
     * @param openid
     * @return MallUser
     */
    @GetMapping("/user/getMallUserByOpenId/{openid}")
    MallUser getMallUserByOpenId(@PathVariable String openid);

    /**
     * 通过phone获取用户
     * @param phone
     * @return MallUser
     */
    @GetMapping("/user/getMallUserByPhone/{phone}")
    MallUser getMallUserByPhone(@PathVariable String phone);

    /**
     * 通过phone,password获取用户
     * @param phone
     * @param password
     * @return MallUser
     */
    @GetMapping("/user/getMallUserByPhonePwd")
    MallUser getMallUserByPhonePwd(@RequestParam(value = "phone") String phone,
                                          @RequestParam(value = "password") String password);

    /**
     * 商城用户用户id
     *
     * @param userId 用户id
     * @return {@link MallUser}
     */
    @GetMapping("/user/getMallUserByUserId/{userId}")
    MallUser getMallUserByUserId(@PathVariable String userId);

    /**
     * wx绑定手机
     *
     * @param phone  电话
     * @param userId 用户id
     * @return {@link Result}
     */
    @GetMapping("/user/wxBindingPhone/{phone}/{userId}")
    Result wxBindingPhone(@PathVariable String phone, @PathVariable String userId);

    /**
     * 更新pwd
     *
     * @param phone 电话
     * @param pwd   松材线虫病
     * @return {@link Result}
     */
    @GetMapping("/user/updatePwd/{phone}/{pwd}")
    Result updatePwd(@PathVariable String phone, @PathVariable String pwd);

    /**
     * 让商城用户名字
     *
     * @param userName 用户名
     * @return {@link Result}
     */
    @GetMapping("/user/getMallUserByName/{userName}")
    MallUser getMallUserByName(@PathVariable String userName);

    /**
     * 得到用户id
     *
     * @param addressId 地址标识
     * @return {@link UserAddress}
     */
    @GetMapping("/user/getUserAddressById/{addressId}")
    UserAddress getUserAddressById(@PathVariable String addressId);

    /**
     * 通过id获取用户优惠券
     *
     * @param couponId 优惠券id
     * @return {@link UserCoupon}
     */
    @GetMapping("/user/getUserCouponById/{couponId}")
    UserCoupon getUserCouponById(@PathVariable String couponId);
}
