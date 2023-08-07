package com.eyh.mall.controller;

import com.eyh.mall.entity.UserBalance;
import com.eyh.mall.entity.UserIntegral;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.service.UserIntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 李平
 * @NAME UserIntegralController
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期五
 * @Date 2023-03-31 12:22:14
 * @Description 用户积分控制类
 */
@RestController
@RequestMapping("/pay")
public class UserIntegralController {

    @Autowired
    private UserIntegralService userIntegralService;

    /**
     * 用户积分充电
     *
     * @param userIntegral 用户积分
     * @return {@link Result}
     */
    @PostMapping("/userIntegralRecharge")
    public Result userIntegralRecharge(@RequestBody UserIntegral userIntegral){
        return userIntegralService.userIntegralRecharge(userIntegral);
    }

    /**
     * 获取用户积分数量
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @GetMapping("/getUserIntegralNumber/{userId}")
    public Result getUserIntegralNumber(@PathVariable String userId){
        return userIntegralService.userIntegralNumberRecharge(userId);
    }

}
