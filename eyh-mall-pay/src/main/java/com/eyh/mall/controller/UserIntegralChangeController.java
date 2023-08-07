package com.eyh.mall.controller;

import com.eyh.mall.entity.common.Result;
import com.eyh.mall.service.UserIntegralChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

/**
 * @Author 李平
 * @NAME UserIntegralChangeController
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期五
 * @Date 2023-03-31 12:22:37
 * @Description 用户积分变动明细控制类
 */
@RestController
@RequestMapping("/pay")
public class UserIntegralChangeController {

    @Autowired
    private UserIntegralChangeService userIntegralChangeService;

    /**
     * 获取用户积分变化细节
     *
     * @param userId 用户id
     * @param type   类型
     * @return {@link Result}
     */
    @GetMapping("/getUserIntegralChangeDetail/{userId}/{type}")
    public Result getUserIntegralChangeDetail(@PathVariable String userId, @PathVariable Integer type){
        return userIntegralChangeService.getUserIntegralChangeDetail(userId,type);
    }

}
