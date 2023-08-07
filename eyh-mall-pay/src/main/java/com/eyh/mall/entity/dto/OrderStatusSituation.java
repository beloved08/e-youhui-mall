package com.eyh.mall.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME OrderStatusSituation
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期六
 * @Date 2023-04-15 15:10:16
 * @Description 订单状态情况
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusSituation {

    /**
     * 名字
     */
    private String name;
    /**
     * 价值
     */
    private Long value;
}
