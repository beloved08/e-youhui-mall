package com.eyh.mall.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME UniversalCouponVoPage
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-03-29 11:34:55
 * @Description 通用优惠券条件搜索数据封装
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UniversalCouponVoPage {

    /**
     * 当前页面
     */
    private Integer currentPage;
    /**
     * 页面大小
     */
    private Integer pageSize;

    /**
     * 普遍优惠券名称
     */
    private String universalCouponName;

    /**
     * 状态
     * 0：使用中，1：未开始，2：已结束，3：所有
     */
    private Integer status;
}
