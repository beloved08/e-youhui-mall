package com.eyh.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.entity.UserAddress;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.redis.UserAddressConstant;
import com.eyh.mall.entity.tdo.UserAddressDetailDto;
import com.eyh.mall.entity.tdo.UserAddressTdo;
import com.eyh.mall.entity.vo.UserAddressPage;
import com.eyh.mall.feign.entity.MallUser;
import com.eyh.mall.mapper.UserAddressMapper;
import com.eyh.mall.redis.RedisUtil;
import com.eyh.mall.service.MallUserService;
import com.eyh.mall.service.UserAddressService;
import com.eyh.mall.util.json.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 李平
 * @Date 2023-3-27
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JsonUtil<UserAddress> userAddressJsonUtil;

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private MallUserService mallUserService;


    /**
     * 添加用户地址
     *
     * @param userAddress 用户地址
     * @return {@link Result}
     */
    @Override
    public Result addUserAddress(UserAddress userAddress) {
        userAddress.setAddressId(UUID.randomUUID().toString());
        userAddress.setId(UUID.randomUUID().toString());
        //设为不默认地址
        // userAddress.setIsDefault(1);
        return userAddressMapper.insert(userAddress) > 0 ? Result.ok("地址新增成功") : Result.err("地址新增失败");
    }

    /**
     * 获取用户地址
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @Override
    public Result getUserAddress(String userId) {
        String key = UserAddressConstant.USER_ADDRESS_LIST + userId;
        if (redisUtil.hasKey(key)){
            List<Object> objects = redisUtil.lGet(key, 0, -1);
            if (objects.size() != 0){
                // 读取redis
                return Result.ok(userAddressJsonUtil.toListBean(redisUtil.lGet(key, 0, -1), UserAddress.class));
            }
        }
        LambdaQueryWrapper<UserAddress> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!"".equals(userId),UserAddress::getUserId,userId);
        List<UserAddress> userAddresses = userAddressMapper.selectList(wrapper);
        if (userAddresses.size() > 0) {
            redisUtil.lSet(key,userAddresses,UserAddressConstant.USER_ADDRESS_LIST_TIME);
        }
        return Result.ok(userAddresses);
    }

    /**
     * 通过id获取地址
     *
     * @param addressId 地址标识
     * @return {@link Result}
     */
    @Override
    public Result getAddressById(String addressId) {
        LambdaQueryWrapper<UserAddress> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!"".equals(addressId),UserAddress::getAddressId,addressId);
        return Result.ok(userAddressMapper.selectOne(wrapper));
    }

    /**
     * 编辑用户地址
     *
     * @param userAddress 用户地址
     * @return {@link Result}
     */
    @Override
    public Result editUserAddress(UserAddress userAddress) {
        //更新之前根据当前的地址id查询该地址的类型
        LambdaQueryWrapper<UserAddress> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(userAddress.getAddressId()),UserAddress::getAddressId,userAddress.getAddressId());
        UserAddress address = userAddressMapper.selectOne(queryWrapper);
        //更新信息
        AtomicInteger i = new AtomicInteger();
        //判断当前修改是否将改地址的普通类型修改为默认为新地址
        if (userAddress.getIsDefault() == 0 && address.getIsDefault() == 1){
            LambdaQueryWrapper<UserAddress> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(!"".equals(userAddress.getUserId()),UserAddress::getUserId,userAddress.getUserId());

            List<UserAddress> userAddresses = userAddressMapper.selectList(wrapper);
            if (userAddresses.size() > 0) {
                userAddresses.forEach(a -> {
                    LambdaUpdateWrapper<UserAddress> updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper.eq(!"".equals(userAddress.getUserId()),UserAddress::getUserId,userAddress.getUserId())
                            .set(!"".equals(userAddress.getUserId()),UserAddress::getIsDefault,1);
                    i.set(userAddressMapper.update(null, updateWrapper));
                });
            }
        }

        LambdaUpdateWrapper<UserAddress> userAddressLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        userAddressLambdaUpdateWrapper.eq(!"".equals(userAddress.getAddressId()),UserAddress::getAddressId,userAddress.getAddressId())
                .set(!"".equals(userAddress.getAddressId()),UserAddress::getIsDefault,0);
        int update = userAddressMapper.update(null, userAddressLambdaUpdateWrapper);
        i.addAndGet(update);

        return i.get() > 0 ? Result.ok("编辑成功") :Result.err("编辑失败");
    }

    /**
     * 删除用户地址
     *
     * @param addressId 地址标识
     * @return {@link Result}
     */
    @Override
    public Result deleteUserAddress(String addressId,String userId) {
        //删除
        LambdaQueryWrapper<UserAddress> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(addressId),UserAddress::getAddressId,addressId);
        int delete;
         delete = userAddressMapper.delete(queryWrapper);

        //选一个其他的为默认地址
        LambdaQueryWrapper<UserAddress> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!"".equals(userId),UserAddress::getUserId,userId);
        List<UserAddress> addressList = userAddressMapper.selectList(wrapper);

        if (addressList.size() > 0){
            UserAddress userAddress = addressList.get(0);
            LambdaUpdateWrapper<UserAddress> updateWrapper = new LambdaUpdateWrapper<>();

            updateWrapper.eq(!"".equals(userAddress.getAddressId()),UserAddress::getAddressId,userAddress.getAddressId())
                    .set(!"".equals(userAddress.getAddressId()),UserAddress::getIsDefault,0);

            int update = userAddressMapper.update(null, updateWrapper);
            delete += update;
        }
        return delete > 0 ?Result.ok("删除成功") : Result.err("删除失败");
    }

    /**
     * 得到所有用户地址
     *
     * @param userAddressPage 用户地址页面
     * @return {@link Result}
     */
    @Override
    public Result getAllUserAddress(UserAddressPage userAddressPage) {
        IPage<UserAddress> page = new Page<UserAddress>(userAddressPage.getCurrentPage(), userAddressPage.getPageSize());

        //条件查询
        LambdaQueryWrapper<UserAddress> wrapper = new LambdaQueryWrapper<>();

        if (!"".equals(userAddressPage.getUserName())){
            MallUser mallUser = mallUserService.getMallUserByName(userAddressPage.getUserName());
            wrapper.eq(!"".equals(mallUser.getUserId()),UserAddress::getUserId,mallUser.getUserId());
        }

        if (!"".equals(userAddressPage.getConsignee())){
            wrapper.like(!"".equals(userAddressPage.getConsignee()),UserAddress::getConsignee,userAddressPage.getConsignee());
        }

        IPage<UserAddress> userAddressIPage = userAddressMapper.selectPage(page, wrapper);

        UserAddressTdo tdo = new UserAddressTdo();
        tdo.setCurrentPage(userAddressPage.getCurrentPage());
        tdo.setPageSize(userAddressIPage.getPages());
        tdo.setSize(userAddressIPage.getSize());
        tdo.setTotal(userAddressIPage.getTotal());

        List<UserAddressDetailDto> d = new ArrayList<>();
        userAddressIPage.getRecords().forEach(a -> {
            MallUser mallUserByUserId = mallUserService.getMallUserByUserId(a.getUserId());
            UserAddressDetailDto userAddressDetailDto = new UserAddressDetailDto();
            BeanUtils.copyProperties(a,userAddressDetailDto);
            userAddressDetailDto.setUserName(mallUserByUserId.getRealName());

            d.add(userAddressDetailDto);
        });
        tdo.setUserAddressDetailDto(d);

        return Result.ok(tdo);
    }

    /**
     * 得到用户id
     *
     * @param addressId 地址标识
     * @return {@link UserAddress}
     */
    @Override
    public UserAddress getUserAddressById(String addressId) {
        LambdaQueryWrapper<UserAddress> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!"".equals(addressId),UserAddress::getAddressId,addressId);
        return userAddressMapper.selectOne(wrapper);
    }

}
