package com.eyh.mall.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME OrderMonthData
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期五
 * @Date 2023-04-14 21:00:49
 * @Description 订单数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderMonthData {
    /**
     * 日期
     */
    private String date;

    /**
     * 数
     */
    private Integer count;
}
