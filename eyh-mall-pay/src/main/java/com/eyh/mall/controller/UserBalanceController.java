package com.eyh.mall.controller;

import com.eyh.mall.entity.UserBalance;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.UserBalanceIntegralPage;
import com.eyh.mall.service.UserBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 李平
 * @NAME UserBalanceController
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期五
 * @Date 2023-03-31 12:21:17
 * @Description 用户余额控制类
 */
@RestController
@RequestMapping("/pay")
public class UserBalanceController {

    @Autowired
    private UserBalanceService userBalanceService;

    /**
     * 用户余额充值
     *
     * @param userBalance 用户平衡
     * @return {@link Result}
     */
    @PostMapping("/userBalanceRecharge")
    public Result userBalanceRecharge(@RequestBody UserBalance userBalance){
        return userBalanceService.userBalanceRecharge(userBalance);
    }

    /**
     * 获取用户平衡数量
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @GetMapping("/getUserBalanceNumber/{userId}")
    public Result getUserBalanceNumber(@PathVariable String userId){
        return userBalanceService.getUserBalanceNumber(userId);
    }

    /**
     * 得到用户平衡页面
     *
     * @param userBalancePage 用户平衡页面
     * @return {@link Result}
     */
    @PostMapping("/getUserBalancePage")
    public Result getUserBalancePage(@RequestBody UserBalanceIntegralPage userBalancePage){
        return userBalanceService.getUserBalancePage(userBalancePage);
    }

}
