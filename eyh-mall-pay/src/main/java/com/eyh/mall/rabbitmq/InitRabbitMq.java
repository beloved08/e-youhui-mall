package com.eyh.mall.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 李平
 * @NAME InitRabbitMq
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-04-05 14:26:53
 * @Description 初始化RabbitMQ
 */
@Configuration
public class InitRabbitMq {

    /**
     * 创建队列扣除库存商品
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue createDeductionInventoryCommodityQueue() {
        return new Queue(CommodityConstant.DEDUCTION_INVENTORY_COMMODITY_QUEUE);
    }

    /**
     * 创建插入物流队列
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue createInsertLogisticsQueue() {
        return new Queue(LogisticsConstant.INSERT_LOGISTICS_QUEUE);
    }

    /**
     * 创建用户确认收据队列
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue createUserConfirmReceiptQueue() {
        return new Queue(LogisticsConstant.USER_CONFIRM_RECEIPT_QUEUE);
    }

}
