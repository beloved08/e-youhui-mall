package com.eyh.mall.rabbitmq;

import com.eyh.mall.entity.logistics.ShipImmediatelyData;
import com.eyh.mall.service.ExpressOrderService;
import com.eyh.mall.service.LogisticsTrackService;
import com.eyh.mall.util.json.JsonUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @Author 李平
 * @NAME InsertLogisticListener
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期日
 * @Date 2023-04-09 11:03:05
 * @Description 插入订单物流监听类
 */
@Component
public class LogisticRabbitmqListener {

    @Autowired
    private JsonUtil<ShipImmediatelyData> shipImmediatelyDataJsonUtil;

    @Autowired
    private ExpressOrderService expressOrderService;

    @Autowired
    private LogisticsTrackService logisticsTrackService;

    /**
     * 插入订单物流侦听器
     *
     * @param data 数据
     */
    @RabbitListener(queues = LogisticsConstant.INSERT_LOGISTICS_QUEUE)
    public void insertOrderLogisticListener(String data){
        ShipImmediatelyData shipImmediatelyData = shipImmediatelyDataJsonUtil.toJavaBean(data, ShipImmediatelyData.class);
        String expressOrderId = UUID.randomUUID().toString();
        expressOrderService.saveExpressOrder(shipImmediatelyData,expressOrderId);
        logisticsTrackService.saveLogisticsTrack(expressOrderId,shipImmediatelyData);
    }

    /**
     * 插入用户确认收据侦听器
     *
     * @param data 数据
     */
    @RabbitListener(queues = LogisticsConstant.USER_CONFIRM_RECEIPT_QUEUE)
    public void insertUserConfirmReceiptListener(String data){
        logisticsTrackService.userConfirmReceiptTask(data);

    }


}
