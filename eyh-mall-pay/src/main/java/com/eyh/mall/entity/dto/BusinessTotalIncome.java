package com.eyh.mall.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME BusinessTotalIncome
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期四
 * @Date 2023-04-20 16:55:36
 * @Description 商家总收入
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessTotalIncome {

    /**
     * 名字
     */
    private String name;
    /**
     * 价值
     */
    private Double value;

}
