package com.eyh.mall.entity.dto;

import com.eyh.mall.entity.merchantStores.Business;
import com.eyh.mall.feign.entity.Commodity;
import com.eyh.mall.feign.entity.MerchantCoupon;
import com.eyh.mall.feign.entity.UniversalCoupon;
import com.eyh.mall.feign.entity.UserAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 李平
 * @NAME OrderPageDto
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-04-05 19:45:17
 * @Description 订单分页
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPageDto {

    /**
     * 订单id
     */
    private String orderId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 订单号
     */
    private String orderNumber;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 订单状态
     */
    private Integer orderStatus;
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
     * 用户地址
     */
    private UserAddress userAddress;
    /**
     * 普遍优惠券
     */
    private UniversalCoupon universalCoupon;
    /**
     * 普遍优惠折扣金额
     */
    private Integer universalCouponDiscountAmount;
    /**
     * 通用券全部可用
     */
    private Integer universalCouponFullAvailable;
    /**
     * 商家优惠券
     */
    private MerchantCoupon merchantCoupon;
    /**
     * 商家优惠券折扣金额
     */
    private Integer merchantCouponDiscountAmount;
    /**
     * 商家优惠券完整可用
     */
    private Integer merchantCouponFullAvailable;

    /**
     * 商品名称
     */
    private String commodityName;
    /**
     * 业务名称
     */
    private String businessName;
    /**
     * 采购量
     */
    private Integer purchaseQuantity;
    /**
     * 订单话
     */
    private String orderRemarks;

    /**
     * 订单商品清单列表
     */
    private List<OrderCommodityList> orderCommodityListList;

}
