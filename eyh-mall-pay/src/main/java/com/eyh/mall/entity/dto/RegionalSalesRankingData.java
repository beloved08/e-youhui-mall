package com.eyh.mall.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME RegionalSalesRankingData
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期五
 * @Date 2023-04-21 14:00:47
 * @Description 地区销量
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionalSalesRankingData {

    private String name;
    private Integer value;

}
