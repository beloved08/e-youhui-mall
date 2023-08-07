package com.eyh.mall.entity.dto;

import com.eyh.mall.entity.ExpressOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME ExpressOrderDto
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-04-10 17:06:53
 * @Description 快递订单物流
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpressOrderDto extends ExpressOrder {

    /**
     * 物流公司名称
     */
    private String logisticsCompanyName;

}
