package com.eyh.mall.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME PromotionActivityPage
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-04-17 11:40:16
 * @Description 促销活动
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionActivityPage {

    /**
     * 当前页面
     */
    private Integer currentPage;
    /**
     * 页面大小
     */
    private Integer pageSize;
    /**
     * 促销活动名
     */
    private String promotionActivityName;
    /**
     * 推广活动状态
     */
    private String promotionActivityStatus;
}
