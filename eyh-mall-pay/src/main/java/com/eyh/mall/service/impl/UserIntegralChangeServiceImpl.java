package com.eyh.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.entity.UserBalanceChange;
import com.eyh.mall.entity.UserIntegralChange;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.goeasy.GoEasyUtil;
import com.eyh.mall.mapper.UserIntegralChangeMapper;
import com.eyh.mall.service.UserIntegralChangeService;
import com.eyh.mall.util.OrderNumber;
import com.eyh.mall.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Author 李平
 * @NAME UserIntegralChangeServiceImpl
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期五
 * @Date 2023-03-31 11:27:15
 * @Description 用户积分变动业务接口实现类
 */
@Service
public class UserIntegralChangeServiceImpl extends ServiceImpl<UserIntegralChangeMapper, UserIntegralChange> implements UserIntegralChangeService {

    @Autowired
    private UserIntegralChangeMapper userIntegralChangeMapper;

    /**
     * 更新用户积分变化细节
     *
     * @param changeType  变化类型
     * @param changeQuota 改变配额
     * @param userId      用户id
     * @return {@link Integer}
     */
    @Override
    public Integer updateUserIntegralChangeDetail(Integer changeType, Double changeQuota,String userId) {
        UserIntegralChange userIntegralChange = new UserIntegralChange();
        userIntegralChange.setId(UUID.randomUUID().toString());
        userIntegralChange.setIntegralChangeId(UUID.randomUUID().toString());
        userIntegralChange.setChangeTime(TimeUtil.getCurrentTime());
        userIntegralChange.setChangeType(changeType);
        userIntegralChange.setChangeQuota(changeQuota);
        userIntegralChange.setUserId(userId);
        userIntegralChange.setOrderNumber(OrderNumber.createOrderNumber());

        GoEasyUtil.messagePush("integral_channel",userIntegralChange);

        return userIntegralChangeMapper.insert(userIntegralChange);
    }

    /**
     * 获取用户积分变化细节
     *
     * @param userId 用户id
     * @param type   类型
     * @return {@link Result}
     */
    @Override
    public Result getUserIntegralChangeDetail(String userId, Integer type) {
        LambdaQueryWrapper<UserIntegralChange> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(userId),UserIntegralChange::getUserId,userId);

        queryWrapper.orderByDesc(UserIntegralChange::getChangeTime);
        if (type == 0){
            //最新6条
            //使用last方法拼接sql语句
            queryWrapper.last(true,"limit 6");
        }
        return Result.ok(userIntegralChangeMapper.selectList(queryWrapper));
    }
}
