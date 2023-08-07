package com.eyh.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyh.mall.entity.UserAddress;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.UserAddressPage;

/**
 * @author 李平
 * @Date 2023-3-27
 */
public interface UserAddressService extends IService<UserAddress> {

    /**
     * 添加用户地址
     *
     * @param userAddress 用户地址
     * @return {@link Result}
     */
    Result addUserAddress(UserAddress userAddress);

    /**
     * 获取用户地址
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    Result getUserAddress(String userId);

    /**
     * 通过id获取地址
     *
     * @param addressId 地址标识
     * @return {@link Result}
     */
    Result getAddressById(String addressId);

    /**
     * 编辑用户地址
     *
     * @param userAddress 用户地址
     * @return {@link Result}
     */
    Result editUserAddress(UserAddress userAddress);

    /**
     * 删除用户地址
     *
     * @param addressId 地址标识
     * @return {@link Result}
     */
    Result deleteUserAddress(String addressId,String userId);

    /**
     * 得到所有用户地址
     *
     * @param userAddressPage 用户地址页面
     * @return {@link Result}
     */
    Result getAllUserAddress(UserAddressPage userAddressPage);

    /**
     * 得到用户id
     *
     * @param addressId 地址标识
     * @return {@link UserAddress}
     */
    UserAddress getUserAddressById(String addressId);
}
