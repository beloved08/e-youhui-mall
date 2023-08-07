package com.eyh.mall.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME PromotionActivityDto
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-04-17 11:49:44
 * @Description 促销活动
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionActivityDto {

    /**
     * 推广活动id
     */
    private String promotionActivityId;
    /**
     * 促销活动名
     */
    private String promotionActivityName;
    /**
     * 推广活动类别
     */
    private String promotionActivityCategory;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 活动描述
     */
    private String activityDescribe;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 目前
     */
    private Integer isCurrent;
}
