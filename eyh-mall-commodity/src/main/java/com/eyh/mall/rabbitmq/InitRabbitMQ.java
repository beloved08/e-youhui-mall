package com.eyh.mall.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 李平
 * @NAME InitRabbitMQ
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期六
 * @Date 2023-03-18 10:56:20
 * @Description RabbitMQ队列创建声明类
 */
@Configuration
public class InitRabbitMQ {

    /**
     * 创建保存分类图标队列
     *
     * @return {@link Queue}
     */
    // @Bean
    // public Queue createSaveClassificationIconQueue(){
    //     return new Queue(ClassificationIconConstant.CLASSIFICATION_ICON_QUEUE);
    // }

    /**
     * 创建删除阿里文件队列
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue createDeleteAlyFileQueue(){
        return new Queue(CommodityConstant.DELETE_ALY_FILE_QUEUE);
    }

    /**
     * 创建保存分类队列
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue createSaveClassificationQueue(){
        return new Queue(CommodityConstant.SAVE_CLASSIFICATION_LIST_QUEUE);
    }

    // /**
    //  * 创建保存商品队列
    //  *
    //  * @return {@link Queue}
    //  */
    // @Bean
    // public Queue createSaveCommodityQueue(){
    //     return new Queue(CommodityConstant.SAVE_COMMODITY_LIST_QUEUE);
    // }


}
