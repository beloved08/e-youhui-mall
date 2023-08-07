package com.eyh.mall.rabbitmq;

/**
 * @Author 李平
 * @NAME CommodityConstant
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期六
 * @Date 2023-03-18 23:45:04
 * @Description 商品服务RabbitMQ队列声明
 */
public class CommodityConstant {

    /**
     * 删除阿里云文件队列
     */
    public static final String DELETE_ALY_FILE_QUEUE = "delete.aly.file.queue";
    /**
     * 保存分类列表队列
     */
    public static final String SAVE_CLASSIFICATION_LIST_QUEUE = "save.classification.list.queue";
    /**
     * 保存商品数据列表队列
     */
    public static final String SAVE_COMMODITY_LIST_QUEUE = "save.commodity.list.queue";

    /**
     * 扣除库存商品队列
     */
    public static final String DEDUCTION_INVENTORY_COMMODITY_QUEUE = "deductionInventoryCommodity.queue";

}
