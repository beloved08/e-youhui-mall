package com.eyh.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.entity.UserBalanceChange;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.goeasy.GoEasyUtil;
import com.eyh.mall.mapper.UserBalanceChangeMapper;
import com.eyh.mall.service.UserBalanceChangeService;
import com.eyh.mall.util.OrderNumber;
import com.eyh.mall.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Author 李平
 * @NAME UserBalanceChangeServiceImpl
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期五
 * @Date 2023-03-31 11:24:55
 * @Description 用户余额变动业务接口实现类
 */
@Service
public class UserBalanceChangeServiceImpl extends ServiceImpl<UserBalanceChangeMapper, UserBalanceChange> implements UserBalanceChangeService {

    @Autowired
    private UserBalanceChangeMapper userBalanceChangeMapper;

    /**
     * 更新用户平衡变化细节
     *
     * @param changeType  变化类型
     * @param changeQuota 改变配额
     * @param userId      用户id
     * @return {@link Integer}
     */
    @Override
    public Integer updateUserBalanceChangeDetail(Integer changeType, Double changeQuota, String userId) {
        UserBalanceChange userBalanceChange = new UserBalanceChange();
        userBalanceChange.setId(UUID.randomUUID().toString());
        userBalanceChange.setBalanceChangeId(UUID.randomUUID().toString());
        userBalanceChange.setChangeTime(TimeUtil.getCurrentTime());
        userBalanceChange.setChangeType(changeType);
        userBalanceChange.setChangeQuota(changeQuota);
        userBalanceChange.setUserId(userId);
        userBalanceChange.setOrderNumber(OrderNumber.createOrderNumber());

        GoEasyUtil.messagePush("balance_channel",userBalanceChange);
        return userBalanceChangeMapper.insert(userBalanceChange);
    }

    /**
     * 获取用户平衡变化细节
     *
     * @param userId 用户id
     * @param type   类型
     * @return {@link Result}
     */
    @Override
    public Result getUserBalanceChangeDetail(String userId, Integer type) {
        LambdaQueryWrapper<UserBalanceChange> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(userId),UserBalanceChange::getUserId,userId);

        // wrapper.orderByAsc("实体类::查询字段"); //升序
        // wrapper.orderByDesc("实体类::查询字段");//降序
        queryWrapper.orderByDesc(UserBalanceChange::getChangeTime);
        if (type == 0){
            //最新6条
            //使用last方法拼接sql语句
            queryWrapper.last(true,"limit 6");
        }

        return Result.ok(userBalanceChangeMapper.selectList(queryWrapper));

    }


}
