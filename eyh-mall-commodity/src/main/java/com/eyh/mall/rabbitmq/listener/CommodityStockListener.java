package com.eyh.mall.rabbitmq.listener;

import com.eyh.mall.entity.logistics.ShipImmediatelyData;
import com.eyh.mall.rabbitmq.CommodityConstant;
import com.eyh.mall.rabbitmq.LogisticsConstant;
import com.eyh.mall.service.CommodityService;
import com.eyh.mall.util.json.JsonUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @Author 李平
 * @NAME CommodityStockListener
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期日
 * @Date 2023-04-09 21:21:17
 * @Description 商品库存监听类
 */
@Component
public class CommodityStockListener {

    @Autowired
    private JsonUtil<ShipImmediatelyData> shipImmediatelyDataJsonUtil;

    @Autowired
    private CommodityService commodityService;

    /**
     * 扣除商品股票侦听器
     *
     * @param data 数据
     */
    @RabbitListener(queues = CommodityConstant.DEDUCTION_INVENTORY_COMMODITY_QUEUE)
    public void deductionCommodityStockListener(String data){
        ShipImmediatelyData shipImmediatelyData = shipImmediatelyDataJsonUtil.toJavaBean(data, ShipImmediatelyData.class);
        commodityService.deductionInventoryCommodity(shipImmediatelyData.getOrderNumber(),shipImmediatelyData.getOrderId());
    }

}
