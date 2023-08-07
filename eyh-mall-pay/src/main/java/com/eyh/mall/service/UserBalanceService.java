package com.eyh.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyh.mall.entity.UserBalance;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.UserBalanceIntegralPage;

/**
 * @Author 李平
 * @NAME UserBalanceService
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期五
 * @Date 2023-03-31 11:18:15
 * @Description 用户余额业务接口层
 */
public interface UserBalanceService extends IService<UserBalance> {


    /**
     * 用户余额充值
     *
     * @param userBalance 用户平衡
     * @return {@link Result}
     */
    Result userBalanceRecharge(UserBalance userBalance);

    /**
     * 获取用户平衡数量
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    Result getUserBalanceNumber(String userId);

    /**
     * 用户余额扣除
     *
     * @param userId  用户id
     * @param balance 平衡
     */
    void userBalanceDeduction(String userId, double balance);

    /**
     * 得到用户平衡页面
     *
     * @param userBalancePage 用户平衡页面
     * @return {@link Result}
     */
    Result getUserBalancePage(UserBalanceIntegralPage userBalancePage);
}
