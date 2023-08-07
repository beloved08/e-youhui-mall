package com.eyh.mall.rabbitmq.init;

import com.eyh.mall.rabbitmq.ShopMallUserConstant;
import com.eyh.mall.rabbitmq.UserBalanceIntegralConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 李平
 * @NAME InitRabbitMQ
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期六
 * @Date 2023-04-01 16:00:16
 * @Description 初始化RabbitMQ
 */
@Configuration
public class InitRabbitMQ {

    /**
     * 创建更新商城用户平衡积分变化队列
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue createUpdateMallUserBalanceIntegralChangeQueue(){
        return new Queue(UserBalanceIntegralConstant.USER_BALANCE_INTEGRAL_QUEUE);
    }

}
