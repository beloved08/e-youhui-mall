package com.eyh.mall.entity.dto;

import com.eyh.mall.entity.Order;
import com.eyh.mall.entity.merchantStores.Business;
import com.eyh.mall.feign.entity.Commodity;
import com.eyh.mall.feign.entity.MerchantCoupon;
import com.eyh.mall.feign.entity.UniversalCoupon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME OrderCommodityList
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-04-05 20:00:24
 * @Description 每个订单下的所有商品
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCommodityList {

    /**
     * 订单
     */
    private Order order;
    /**
     * 商品
     */
    private Commodity commodity;
    /**
     * 业务
     */
    private Business business;
    /**
     * 普遍优惠券
     */
    private UniversalCoupon universalCoupon;
    /**
     * 商家优惠券
     */
    private MerchantCoupon merchantCoupon;
    /**
     * 采购量
     */
    private Integer purchaseQuantity;

}
