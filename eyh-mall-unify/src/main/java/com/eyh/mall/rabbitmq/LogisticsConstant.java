package com.eyh.mall.rabbitmq;

/**
 * @Author 李平
 * @NAME LogisticsConstant
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期日
 * @Date 2023-04-09 10:37:26
 * @Description 订单物流
 */
public class LogisticsConstant {

    /**
     * 插入队列物流
     */
    public static final String INSERT_LOGISTICS_QUEUE = "insert.logistics.queue";

    /**
     * 快递站签名队列
     */
    public static final String EXPRESS_DELIVERY_STATION_SIGNATURE_QUEUE = "express.delivery.station.signature.queue";


    /**
     * 用户确认接收队列
     */
    public static final String USER_CONFIRM_RECEIPT_QUEUE = "user.confirm.receipt.queue";

}
