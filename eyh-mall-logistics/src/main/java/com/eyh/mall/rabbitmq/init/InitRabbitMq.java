package com.eyh.mall.rabbitmq.init;

import com.eyh.mall.rabbitmq.LogisticsConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 李平
 * @NAME InitRabbitMq
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期日
 * @Date 2023-04-09 16:07:20
 * @Description 初始化RabbitMQ
 */
@Configuration
public class InitRabbitMq {

    /**
     * 创建签名快递站队列
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue createExpressDeliveryStationSignatureQueue(){
        return new Queue(LogisticsConstant.EXPRESS_DELIVERY_STATION_SIGNATURE_QUEUE);
    }
}
