package com.eyh.mall.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME PromotionPeopleGoEasyData
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期五
 * @Date 2023-04-14 14:15:31
 * @Description 促销人员
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionPeopleGoEasyData {

    /**
     * 电话
     */
    private String phone;

    /**
     * 当前时间
     */
    private String currentTime;

    /**
     * 验证
     * 审核状态(0：待审核，1：审核通过，2：审核不通过)
     */
    private Integer verify;
}
