package com.eyh.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyh.mall.entity.UserBalanceChange;
import com.eyh.mall.entity.common.Result;

/**
 * @Author 李平
 * @NAME UserBalanceChangeService
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期五
 * @Date 2023-03-31 11:19:10
 * @Description 用户余额变动业务接口层
 */
public interface UserBalanceChangeService extends IService<UserBalanceChange> {

    /**
     * 更新用户平衡变化细节
     *
     * @param changeType  变化类型
     * @param changeQuota 改变配额
     * @param userId      用户id
     * @return {@link Integer}
     */
    Integer updateUserBalanceChangeDetail(Integer changeType, Double changeQuota, String userId);

    /**
     * 获取用户平衡变化细节
     *
     * @param userId 用户id
     * @param type   类型
     * @return {@link Result}
     */
    Result getUserBalanceChangeDetail(String userId, Integer type);
}
