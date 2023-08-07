package com.eyh.mall.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME OrderDto
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-04-05 10:05:43
 * @Description 1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderGoEasyDto {

    /**
     * 订单id
     */
    private String orderId;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 订单状态(0：待付款，1：待发货，2：待收货，3：已完成，4：售后)
     */
    private Integer orderStatus;
    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 成交时间
     */
    private String strikeBargainTime;
    /**
     * 交货时间
     */
    private String deliveryTime;
}
