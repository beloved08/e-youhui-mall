package com.eyh.mall.entity.dto;

import com.eyh.mall.feign.entity.Commodity;
import com.eyh.mall.feign.entity.MallUser;
import com.eyh.mall.feign.entity.UserAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 李平
 * @NAME UserOrderPage
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期四
 * @Date 2023-04-06 16:01:20
 * @Description 用户订单分页
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderPage {

    /**
     * id
     */
    private String id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 商城用户
     */
    private MallUser mallUser;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 订单状态(0：待付款，1：待发货，2：待收货，3：已完成，4：售后)
     */
    private Integer orderStatus;
    /**
     * 订单号
     */
    private String orderNumber;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 成交时间
     */
    private String strikeBargainTime;
    /**
     * 运输费用
     */
    private String transportationExpenses;
    /**
     * 总价格
     */
    private String totalPrice;
    /**
     * 会员折扣
     */
    private String memberDiscount;
    /**
     * 详细地址
     */
    private String addressDetail;
    /**
     * 订单话
     */
    private String orderRemarks;

    /**
     * 用户地址
     */
    private UserAddress userAddress;

    /**
     * 订单商品列表
     */
    private List<OrderCommodityList> orderCommodityList;
}
