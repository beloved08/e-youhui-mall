package com.eyh.mall.entity.dto;

import com.eyh.mall.feign.entity.Commodity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 李平
 * @NAME UniversalCouponWxDto
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-03-29 17:52:42
 * @Description 商家通用优惠券
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UniversalCouponWxDto {

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
     * 总量
     */
    private Integer totalQuantity;

    /**
     * 商品列表
     */
    private List<Commodity> commodityList;
}
