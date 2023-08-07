package com.eyh.mall.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 李平
 * @NAME initRabbitMq
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期六
 * @Date 2023-03-11 17:28:57
 * @Description 初始化创建商家店铺所使用的消息队列
 */
@Configuration
public class initRabbitMq {

    /**
     * 创建保存商店标志队列
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue createSaveShopLogoQueue(){
        return new Queue(ShopLogoConstant.SHOP_LOGO_QUEUE);
    }

    /**
     * 创建验证业务队列
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue createVerifyBusinessQueue(){
        return new Queue(ShopMallUserConstant.VERIFY_BUSINESS_QUEUE);
    }


}
