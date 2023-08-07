package com.eyh.mall.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 李平
 * @Date 2023-3-6
 */
@Configuration
public class initRabbitMq {

    @Bean
    public Queue createSaveMallUserQueue(){
        return new Queue(ShopMallUserConstant.SAVE_MALL_USER_QUEUE);
    }

    @Bean
    public Queue createUserRegisterCodeQueue(){
        return new Queue(ShopMallUserConstant.MALL_USER_REGISTER_SEND_CODE_QUEUE);
    }

}
