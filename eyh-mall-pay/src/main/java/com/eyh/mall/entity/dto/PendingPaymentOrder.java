package com.eyh.mall.entity.dto;

import com.eyh.mall.entity.Order;
import com.eyh.mall.entity.merchantStores.Business;
import com.eyh.mall.feign.entity.Commodity;
import com.eyh.mall.feign.entity.UserAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME PendingPaymentOrder
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-04-05 12:54:36
 * @Description 待付款订单
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendingPaymentOrder extends Order {

    /**
     * 业务
     */
    private Business business;
    /**
     * 商品
     */
    private Commodity commodity;
    /**
     * 用户地址
     */
    private UserAddress userAddress;

    /**
     * 折扣限制
     */
    private String discountLimit;

    /**
     * 订单
     */
    private Order order;

}
