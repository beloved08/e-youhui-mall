package com.eyh.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyh.mall.entity.UserIntegral;
import com.eyh.mall.entity.common.Result;

/**
 * @Author 李平
 * @NAME UserIntegralService
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期五
 * @Date 2023-03-31 11:20:02
 * @Description 用户积分业务接口层
 */
public interface UserIntegralService extends IService<UserIntegral> {

    /**
     * 用户积分充电
     *
     * @param userIntegral 用户积分
     * @return {@link Result}
     */
    Result userIntegralRecharge(UserIntegral userIntegral);

    /**
     * 用户积分号码充电
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    Result userIntegralNumberRecharge(String userId);
}
