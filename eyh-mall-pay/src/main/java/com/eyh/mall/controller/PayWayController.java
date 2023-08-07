package com.eyh.mall.controller;

import com.eyh.mall.entity.common.Result;
import com.eyh.mall.service.PayWayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 李平
 * @NAME PayWayController
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-04-10 15:54:59
 * @Description 支付方式
 */
@RestController
@RequestMapping("/pay")
public class PayWayController {

    @Autowired
    private PayWayService payWayService;

    /**
     * 得到所有支付方式
     *
     * @return {@link Result}
     */
    @GetMapping("/getAllPayWay")
    public Result getAllPayWay(){
        return Result.ok(payWayService.list());
    }

}
