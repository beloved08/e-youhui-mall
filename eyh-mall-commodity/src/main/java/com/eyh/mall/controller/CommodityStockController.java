package com.eyh.mall.controller;

import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.CommodityStockPage;
import com.eyh.mall.service.CommodityStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 李平
 * @NAME CommodityStockController
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-04-10 09:59:58
 * @Description 商品库存
 */
@RestController
@RequestMapping("/commodity/stock")
public class CommodityStockController {

    @Autowired
    private CommodityStockService commodityStockService;

    /**
     * 获得大宗商品股票页面
     *
     * @param commodityStockPage 大宗商品股票页面
     * @return {@link Result}
     */
    @PostMapping("/getCommodityStockPage")
    public Result getCommodityStockPage(@RequestBody CommodityStockPage commodityStockPage){
        return commodityStockService.getCommodityStockPage(commodityStockPage);
    }

    /**
     * 获得大宗商品股票细节
     *
     * @param orderNumber 订单号
     * @return {@link Result}
     */
    @GetMapping("/getCommodityStockDetail/{orderNumber}")
    public Result getCommodityStockDetail(@PathVariable String orderNumber){
        return commodityStockService.getCommodityStockDetail(orderNumber);
    }
}
