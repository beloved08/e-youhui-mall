package com.eyh.mall.controller;

import com.eyh.mall.entity.common.Result;
import com.eyh.mall.service.UserBalanceChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 李平
 * @NAME UserBalanceChangeController
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期五
 * @Date 2023-03-31 12:21:46
 * @Description 用户余额变动明细控制类
 */
@RestController
@RequestMapping("/pay")
public class UserBalanceChangeController {

    @Autowired
    private UserBalanceChangeService userBalanceChangeService;

    /**
     * 获取用户平衡变化细节
     *
     * @param userId 用户id
     * @param type   类型
     * @return {@link Result}
     */
    @GetMapping("/getUserBalanceChangeDetail/{userId}/{type}")
    public Result getUserBalanceChangeDetail(@PathVariable String userId,@PathVariable Integer type){
        return userBalanceChangeService.getUserBalanceChangeDetail(userId,type);
    }
}
