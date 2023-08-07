package com.eyh.mall.controller;

import com.eyh.mall.entity.Order;
import com.eyh.mall.entity.UserOrder;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.dto.OrderGoEasyDto;
import com.eyh.mall.entity.dto.PendingPaymentOrder;
import com.eyh.mall.entity.vo.OrderPage;
import com.eyh.mall.entity.vo.OrderVo;
import com.eyh.mall.entity.logistics.ShipImmediatelyData;
import com.eyh.mall.goeasy.GoEasyUtil;
import com.eyh.mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 李平
 * @NAME OrderController
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-04-05 09:25:38
 * @Description 订单控制类
 */
@RestController
@RequestMapping("/pay/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getGoeasy")
    public Result testGetGoeasy() {
        OrderGoEasyDto orderGoEasyDto = new OrderGoEasyDto();
        orderGoEasyDto.setOrderId("7e9747a4-3fe1-4949-a310-468cfb27063d");
        orderGoEasyDto.setOrderStatus(1);
        orderGoEasyDto.setCreateTime("2023-04-07 21:48:06");
        orderGoEasyDto.setStrikeBargainTime("2023-04-07 21:48:06");
        orderGoEasyDto.setOrderNumber("0901501949463299078");
        GoEasyUtil.messagePush("createOrderManageSys_chancel", orderGoEasyDto);
        return Result.ok(orderGoEasyDto);
    }


    /**
     * 创建订单
     *
     * @param orderVo 为签证官
     * @return {@link Result}
     */
    @PostMapping("/createOrder")
    public Result createOrder(@RequestBody List<OrderVo> orderVo) {
        return orderService.createOrder(orderVo);
    }

    /**
     * 等待支付订单
     *
     * @param orderId 订单id
     * @return {@link Result}
     */
    @GetMapping("/getPendingPaymentOrderById/{orderId}")
    public Result getPendingPaymentOrderById(@PathVariable String orderId) {
        return orderService.getPendingPaymentOrderById(orderId);
    }

    /**
     * 收到你付款完成订单id
     *
     * @param orderId 订单id
     * @return {@link Result}
     */
    @GetMapping("/getPaymentCompletedOrderById/{orderId}")
    public Result getPaymentCompletedOrderById(@PathVariable String orderId) {
        return orderService.getPaymentCompletedOrderById(orderId);
    }

    /**
     * 继续等待付款
     *
     * @param pendingPaymentOrder 等待付款订单
     * @return {@link Result}
     */
    @PostMapping("/pendingPaymentContinue")
    public Result pendingPaymentContinue(@RequestBody List<PendingPaymentOrder> pendingPaymentOrder) {
        return orderService.pendingPaymentContinue(pendingPaymentOrder);
    }

    /**
     * 把所有订单列表页面
     *
     * @param orderPage 订单页面
     * @return {@link Result}
     */
    @PostMapping("/getAllOrderListPage")
    public Result getAllOrderListPage(@RequestBody OrderPage orderPage) {
        return orderService.getAllOrderListPage(orderPage);
    }

    /**
     * 得到订单细节
     *
     * @param orderId 订单id
     * @return {@link Result}
     */
    @GetMapping("/getOrderDetail/{orderId}")
    public Result getOrderDetail(@PathVariable String orderId) {
        return orderService.getOrderDetail(orderId);
    }

    /**
     * 获取用户订单
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @GetMapping("/getUserOrder/{userId}")
    public Result getUserOrder(@PathVariable String userId) {
        return orderService.getUserOrder(userId);
    }


    /**
     * 立即发货
     *
     * @param shipImmediatelyData 立即发货数据
     * @return {@link Result}
     */
    @PostMapping("/shipImmediately")
    public Result shipImmediately(@RequestBody ShipImmediatelyData shipImmediatelyData) {
        return orderService.shipImmediately(shipImmediatelyData);
    }

    /**
     * 订单数量id
     *
     * @param orderNumber 订单号
     * @param orderId     订单id
     * @return {@link List}<{@link Order}>
     */
    @GetMapping("/getOrderByNumberId/{orderNumber}/{orderId}")
    public List<Order> getOrderByNumberId(@PathVariable String orderNumber, @PathVariable String orderId) {
        return orderService.getOrderByNumberAndId(orderNumber, orderId);
    }

    /**
     * 得到订单号码
     *
     * @param orderNumber 订单号
     * @return {@link String}
     */
    @GetMapping("/getOrderByNumber/{orderNumber}")
    public String getOrderByNumber(@PathVariable String orderNumber) {
        return orderService.getOrderByNumber(orderNumber);
    }

    /**
     * 被数量订单详细信息
     *
     * @param orderNumber 订单号
     * @return {@link Result}
     */
    @GetMapping("/getOrderDetailByNumber/{orderNumber}")
    public Result getOrderDetailByNumber(@PathVariable String orderNumber){
        return orderService.getOrderDetailByNumber(orderNumber);
    }

    /**
     * 用户确认收据
     *
     * @param expressOrderId 快递订单id
     * @param orderNumber    订单号
     * @return {@link Result}
     */
    @GetMapping("/userConfirmReceipt/{expressOrderId}/{orderNumber}")
    public Result userConfirmReceipt(@PathVariable String expressOrderId,@PathVariable String orderNumber){
        return orderService.userConfirmReceipt(expressOrderId,orderNumber);
    }

    /**
     * 得到订单数量
     *
     * @return {@link Result}
     */
    @GetMapping("/getOrderCount")
    public Result getOrderCount(){
        return orderService.getOrderCount();
    }

    /**
     * 得到订单趋势
     *
     * @return {@link Result}
     */
    @GetMapping("/getOrderTrends")
    public Result getOrderTrends(){
        return orderService.getOrderTrends();
    }

    /**
     * 得到订单状态情况
     *
     * @return {@link Result}
     */
    @GetMapping("/getOrderStatusSituation")
    public Result getOrderStatusSituation(){
        return orderService.getOrderStatusSituation();
    }

    /**
     * 得到今天订单数量
     *
     * @return {@link Result}
     */
    @GetMapping("/getToadyOrderCount")
    public Result getToadyOrderCount(){
        return orderService.getToadyOrderCount();
    }

    /**
     * 获得业务总收入
     *
     * @return {@link Result}
     */
    @GetMapping("/getBusinessTotalIncome")
    public Result getBusinessTotalIncome(){
        return orderService.getBusinessTotalIncome();
    }

    /**
     * 变热销售商品比例
     *
     * @return {@link Result}
     */
    @GetMapping("/getHotSalesCommodityProportion")
    public Result getHotSalesCommodityProportion(){
        return orderService.getHotSalesCommodityProportion();
    }

    /**
     * 得到区域销售排名
     *
     * @return {@link Result}
     */
    @GetMapping("/getRegionalSalesRanking")
    public Result getRegionalSalesRanking(){
        return orderService.getRegionalSalesRanking();
    }

}
