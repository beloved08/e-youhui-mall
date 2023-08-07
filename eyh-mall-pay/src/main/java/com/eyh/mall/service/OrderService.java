package com.eyh.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyh.mall.entity.Order;
import com.eyh.mall.entity.UserOrder;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.dto.PendingPaymentOrder;
import com.eyh.mall.entity.vo.OrderPage;
import com.eyh.mall.entity.vo.OrderVo;
import com.eyh.mall.entity.logistics.ShipImmediatelyData;

import java.util.List;

/**
 * @Author 李平
 * @NAME OrderService
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-04-05 09:23:47
 * @Description 订单接口
 */
public interface OrderService extends IService<Order> {

    /**
     * 创建订单
     *
     * @param orderVo 为签证官
     * @return {@link Result}
     */
    Result createOrder(List<OrderVo> orderVo);

    /**
     * 等待支付订单id
     *
     * @param orderId 订单id
     * @return {@link Result}
     */
    Result getPendingPaymentOrderById(String orderId);

    /**
     * 继续等待付款
     *
     * @param pendingPaymentOrder 等待付款订单
     * @return {@link Result}
     */
    Result pendingPaymentContinue(List<PendingPaymentOrder> pendingPaymentOrder);

    /**
     * 收到你付款完成订单id
     *
     * @param orderId 订单id
     * @return {@link Result}
     */
    Result getPaymentCompletedOrderById(String orderId);

    /**
     * 把所有订单列表页面
     *
     * @param orderPage 订单页面
     * @return {@link Result}
     */
    Result getAllOrderListPage(OrderPage orderPage);

    /**
     * 得到订单细节
     *
     * @param orderId 订单id
     * @return {@link Result}
     */
    Result getOrderDetail(String orderId);

    /**
     * 获取用户订单
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    Result getUserOrder(String userId);

    /**
     *立即发货
     *
     * @param shipImmediatelyData 立即发货数据
     * @return {@link Result}
     */
    Result shipImmediately(ShipImmediatelyData shipImmediatelyData);

    /**
     * 被数量和订单id
     *
     * @param orderNumber 订单号
     * @param orderId     订单id
     * @return {@link List}<{@link Order}>
     */
    List<Order> getOrderByNumberAndId(String orderNumber, String orderId);

    /**
     * 得到订单号码
     *
     * @param orderNumber 订单号
     * @return {@link String}
     */
    String getOrderByNumber(String orderNumber);

    /**
     * 被数量订单详细信息
     *
     * @param orderNumber 订单号
     * @return {@link Result}
     */
    Result getOrderDetailByNumber(String orderNumber);

    /**
     * 用户确认收据
     *
     * @param expressOrderId 快递订单id
     * @param orderNumber    订单号
     * @return {@link Result}
     */
    Result userConfirmReceipt(String expressOrderId, String orderNumber);

    /**
     * 得到订单数量
     *
     * @return {@link Result}
     */
    Result getOrderCount();

    /**
     * 得到订单趋势
     *
     * @return {@link Result}
     */
    Result getOrderTrends();

    /**
     * 得到订单状态情况
     *
     * @return {@link Result}
     */
    Result getOrderStatusSituation();

    /**
     * 得到今天订单数量
     *
     * @return {@link Result}
     */
    Result getToadyOrderCount();

    /**
     * 获得业务总收入
     *
     * @return {@link Result}
     */
    Result getBusinessTotalIncome();

    /**
     * 变热销售商品比例
     *
     * @return {@link Result}
     */
    Result getHotSalesCommodityProportion();

    /**
     * 得到区域销售排名
     *
     * @return {@link Result}
     */
    Result getRegionalSalesRanking();

}
