package com.eyh.mall.entity.dto;

import com.eyh.mall.entity.Order;
import com.eyh.mall.entity.merchantStores.Business;
import com.eyh.mall.feign.entity.Commodity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME ReceiveOrderCommodity
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期二
 * @Date 2023-04-11 10:20:57
 * @Description 待收货商品
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiveOrderCommodity {
    /**
     * 业务
     */
    private Business business;
    /**
     * 商品
     */
    private Commodity commodity;

    /**
     * 订单
     */
    private Order order;
    /**
     * 折扣限制
     */
    private Integer discountLimit;

    /**
     * 采购量
     */
    private Integer purchaseQuantity;
}
