package com.eyh.mall.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME UniversalCouponDto
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-03-29 13:16:41
 * @Description 分页条件搜索表格列名称
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UniversalCouponDto {

    /**
     * 通用券id
     */
    private String universalCouponId;

    /**
     * 普遍优惠券名称
     */
    private String universalCouponName;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 折扣金额
     */
    private Integer discountAmount;
    /**
     * 完整可用
     */
    private Integer fullAvailable;

    /**
     * 状态
     */
    private Integer status;
    /**
     * 总量
     */
    private Integer totalQuantity;
}
