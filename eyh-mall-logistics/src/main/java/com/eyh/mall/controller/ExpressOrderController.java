package com.eyh.mall.controller;

import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.ExpressOrderPage;
import com.eyh.mall.service.ExpressOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 李平
 * @NAME ExpressOrderController
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-04-10 16:27:42
 * @Description 快递订单物流
 */
@RestController
@RequestMapping("/logistics")
public class ExpressOrderController {

    @Autowired
    private ExpressOrderService expressOrderService;

    /**
     * 得到快递订单页面
     *
     * @param expressOrderPage 快递订单页面
     * @return {@link Result}
     */
    @PostMapping("/getExpressOrderPage")
    public Result getExpressOrderPage(@RequestBody ExpressOrderPage expressOrderPage){
        return expressOrderService.getExpressOrderPage(expressOrderPage);
    }

    /**
     * 得到快递订单细节
     *
     * @param orderNumber 订单号
     * @return {@link Result}
     */
    @GetMapping("/getExpressOrderDetail/{orderNumber}")
    public Result getExpressOrderDetail(@PathVariable String orderNumber){
        return expressOrderService.getExpressOrderDetail(orderNumber);
    }

}
