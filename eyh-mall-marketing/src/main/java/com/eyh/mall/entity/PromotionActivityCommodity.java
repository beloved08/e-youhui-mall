package com.eyh.mall.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME PromotionActivityCommodity
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-04-17 10:04:34
 * @Description 促销活动商品
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionActivityCommodity {

    /**
     * id
     */
    private String id;
    /**
     * 促销活动商品id
     */
    private String commodityId;
    /**
     * 推广活动id
     */
    private String promotionActivityId;

}
