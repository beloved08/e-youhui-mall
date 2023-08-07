package com.eyh.mall.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 李平
 * @NAME MerchantCouponTdo
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-03-29 16:21:31
 * @Description 商家优惠券
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantCouponTdo {

    /**
     * 当前页面
     */
    private long currentPage;
    /**
     * 页面大小
     */
    private long pageSize;
    /**
     * 大小
     */
    private long size;
    /**
     * 总计
     */
    private long total;

    /**
     * 商家优惠券dto列表
     */
    private List<MerchantCouponDto> merchantCouponDtoList;

}
