package com.eyh.mall.controller;

import com.eyh.mall.entity.UserAddress;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.UserAddressPage;
import com.eyh.mall.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 李平
 * @NAME UserAddressController
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-03-27 08:56:33
 * @Description 用户的收货地址控制类
 */
@RestController
@RequestMapping("/user")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;


    /**
     * 添加用户地址
     *
     * @param userAddress 用户地址
     * @return {@link Result}
     */
    @PostMapping("/addUserAddress")
    public Result addUserAddress(@RequestBody UserAddress userAddress){
        return userAddressService.addUserAddress(userAddress);
    }

    /**
     * 获取用户地址
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @GetMapping("/getUserAddress/{userId}")
    public Result getUserAddress(@PathVariable String userId){
        return userAddressService.getUserAddress(userId);
    }

    /**
     * 通过id获取地址
     *
     * @param addressId 地址标识
     * @return {@link Result}
     */
    @GetMapping("/getAddressById/{addressId}")
    public Result getAddressById(@PathVariable String addressId){
        return userAddressService.getAddressById(addressId);
    }

    /**
     * 得到用户id
     * 通过id获取地址
     *
     * @param addressId 地址标识
     * @return {@link UserAddress}
     */
    @GetMapping("/getUserAddressById/{addressId}")
    public UserAddress getUserAddressById(@PathVariable String addressId){
        return userAddressService.getUserAddressById(addressId);
    }

    /**
     * 编辑用户地址
     *
     * @param userAddress 用户地址
     * @return {@link Result}
     */
    @PostMapping("/editUserAddress")
    public Result editUserAddress(@RequestBody UserAddress userAddress){
        return userAddressService.editUserAddress(userAddress);
    }

    /**
     * 删除用户地址
     *
     * @param addressId 地址标识
     * @return {@link Result}
     */
    @GetMapping("/deleteUserAddress/{addressId}/{userId}")
    public Result deleteUserAddress(@PathVariable String addressId,@PathVariable String userId){
        return userAddressService.deleteUserAddress(addressId,userId);
    }

    /**
     * 得到所有用户地址
     *
     * @param userAddressPage 用户地址页面
     * @return {@link Result}
     */
    @PostMapping("/getAllUserAddress")
    public Result getAllUserAddress(@RequestBody UserAddressPage userAddressPage) {
        return userAddressService.getAllUserAddress(userAddressPage);
    }

}
