package com.eyh.mall.rabbitmq.init;

import com.eyh.mall.rabbitmq.NationalPromotionConstant;
import com.eyh.mall.rabbitmq.ShopMallUserConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 李平
 * @NAME InitRabbitmq
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期四
 * @Date 2023-04-13 13:46:33
 * @Description 初始化RabbitMQ
 */
@Configuration
public class InitRabbitmq {

    /**
     * 创建国家推广人代码队列
     *
     * @return {@link Queue}
     */
    // @Bean
    // public Queue createNationalPromotionPeopleCodeQueue(){
    //     return new Queue(NationalPromotionConstant.NATIONAL_PROMOTION_PEOPLE_CODE_QUEUE);
    // }

    /**
     * 创建促进人们通过队列
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue createPromotionPeoplePassQueue(){
        return new Queue(NationalPromotionConstant.PROMOTION_PEOPLE_PASS_QUEUE);
    }
}
