package com.eyh.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.entity.UserIntegral;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.mapper.UserIntegralMapper;
import com.eyh.mall.service.UserIntegralChangeService;
import com.eyh.mall.service.UserIntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Author 李平
 * @NAME UserIntegralServiceImpl
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期五
 * @Date 2023-03-31 11:26:08
 * @Description 用户积分业务接口实现类
 */
@Service
public class UserIntegralServiceImpl extends ServiceImpl<UserIntegralMapper, UserIntegral> implements UserIntegralService {

    @Autowired
    private UserIntegralMapper userIntegralMapper;

    @Autowired
    private UserIntegralChangeService userIntegralChangeService;

    /**
     * 用户积分充电
     *
     * @param userIntegral 用户积分
     * @return {@link Result}
     */
    @Override
    public Result userIntegralRecharge(UserIntegral userIntegral) {
        LambdaQueryWrapper<UserIntegral> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(userIntegral.getUserId()),UserIntegral::getUserId,userIntegral.getUserId());
        UserIntegral selectOne = userIntegralMapper.selectOne(queryWrapper);

        int i = 0;
        if (selectOne == null) {
            //第一次充值
            userIntegral.setId(UUID.randomUUID().toString());
            userIntegral.setIntegralId(UUID.randomUUID().toString());
            i += userIntegralMapper.insert(userIntegral);
        }else {
            //不是第一次充值
            LambdaUpdateWrapper<UserIntegral> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(!"".equals(userIntegral.getUserId()),UserIntegral::getUserId,userIntegral.getUserId())
                    .set(!"".equals(userIntegral.getUserId()),UserIntegral::getAvailableIntegral,userIntegral.getAvailableIntegral() + selectOne.getAvailableIntegral());

            i += userIntegralMapper.update(null,updateWrapper);
        }
        if (i > 0){
            // 更新用户积分变动明细，插入记录
            userIntegralChangeService.updateUserIntegralChangeDetail(0,userIntegral.getAvailableIntegral(),userIntegral.getUserId());
            return Result.ok("用户积分变动更新失败");
        }else {
            return Result.err("用户积分变动更新失败");
        }

    }

    /**
     * 用户积分号码充电
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @Override
    public Result userIntegralNumberRecharge(String userId) {
        LambdaQueryWrapper<UserIntegral> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(userId),UserIntegral::getUserId,userId);
        UserIntegral userIntegral = userIntegralMapper.selectOne(queryWrapper);
        return userIntegral == null ? Result.ok(0) : Result.ok(userIntegral.getAvailableIntegral());
    }

}
