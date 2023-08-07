package com.eyh.mall.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME OrderCount
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期五
 * @Date 2023-04-14 18:53:44
 * @Description 订单数量统计
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCount {
    /**
     * 订单总数
     */
    private long totalOrderCount;
    /**
     * 今天订单数量
     */
    private long todayOrderCount;

    /**
     * 订单总价格
     */
    private double totalOrderPrice;
    /**
     * 今天订单价格
     */
    private double todayOrderPrice;
}
