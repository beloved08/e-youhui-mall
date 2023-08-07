package com.eyh.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyh.mall.entity.UserIntegralChange;
import com.eyh.mall.entity.common.Result;

/**
 * @Author 李平
 * @NAME UserIntegralChangeService
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期五
 * @Date 2023-03-31 11:20:42
 * @Description 用户余额变动业务接口层
 */
public interface UserIntegralChangeService extends IService<UserIntegralChange>{

    /**
     * 更新用户积分变化细节
     *
     * @param changeType  变化类型
     * @param changeQuota 改变配额
     * @param userId      用户id
     * @return {@link Integer}
     */
    Integer updateUserIntegralChangeDetail(Integer changeType, Double changeQuota, String userId);

    /**
     * 获取用户积分变化细节
     *
     * @param userId 用户id
     * @param type   类型
     * @return {@link Result}
     */
    Result getUserIntegralChangeDetail(String userId, Integer type);
}
