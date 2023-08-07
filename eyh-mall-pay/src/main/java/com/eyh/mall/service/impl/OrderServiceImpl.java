package com.eyh.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.entity.Order;
import com.eyh.mall.entity.UserOrder;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.dto.*;
import com.eyh.mall.entity.marketing.CouponMQData;
import com.eyh.mall.entity.merchantStores.Business;
import com.eyh.mall.entity.vo.OrderPage;
import com.eyh.mall.entity.vo.OrderVo;
import com.eyh.mall.entity.logistics.ShipImmediatelyData;
import com.eyh.mall.feign.client.commodity.CommodityApiClient;
import com.eyh.mall.feign.client.commodity.CommodityServiceApiClient;
import com.eyh.mall.feign.client.marketing.CouponApiClient;
import com.eyh.mall.feign.client.merchantStores.BusinessApiClient;
import com.eyh.mall.feign.client.user.UserApiClient;
import com.eyh.mall.feign.entity.*;
import com.eyh.mall.goeasy.GoEasyUtil;
import com.eyh.mall.mapper.OrderMapper;
import com.eyh.mall.mapper.UserOrderMapper;
import com.eyh.mall.rabbitmq.CommodityConstant;
import com.eyh.mall.rabbitmq.CouponConstant;
import com.eyh.mall.rabbitmq.LogisticsConstant;
import com.eyh.mall.service.OrderService;
import com.eyh.mall.service.UserBalanceService;
import com.eyh.mall.util.OrderNumber;
import com.eyh.mall.util.TimeUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Author 李平
 * @NAME OrderServiceImpl
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-04-05 09:24:33
 * @Description 订单接口实现
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserOrderMapper userOrderMapper;

    @Autowired
    private UserBalanceService userBalanceService;

    @Autowired
    private CommodityApiClient commodityApiClient;

    @Autowired
    private CommodityServiceApiClient commodityServiceApiClient;

    @Autowired
    private UserApiClient userApiClient;

    @Autowired
    private CouponApiClient couponApiClient;

    @Autowired
    private BusinessApiClient businessApiClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 创建订单
     *
     * @param orderVo 为签证官
     * @return {@link Result}
     */
    @Override
    public Result createOrder(List<OrderVo> orderVo) {
        String orderId = UUID.randomUUID().toString();
        String orderNumber = OrderNumber.createOrderNumber();
        String createTime = TimeUtil.getCurrentTime();

        if (orderVo.get(0).getOrderStatus() == 0) {
            //待付款
            AtomicInteger insert = new AtomicInteger();
            orderVo.forEach(o -> {
                Order order = new Order();
                BeanUtils.copyProperties(o, order);

                order.setId(UUID.randomUUID().toString());
                order.setOrderId(orderId);
                //订单状态(0：待付款，1：待发货，2：待收货，3：已完成，4：售后)
                order.setOrderStatus(0);
                order.setCreateTime(createTime);
                order.setOrderNumber(orderNumber);
                order.setStrikeBargainTime("");
                insert.addAndGet(orderMapper.insert(order));

            });
            UserOrder userOrder = new UserOrder();
            userOrder.setId(UUID.randomUUID().toString());
            userOrder.setOrderId(orderId);
            userOrder.setUserId(orderVo.get(0).getUserId());
            userOrder.setOrderStatus(0);
            userOrder.setOrderNumber(orderNumber);
            userOrder.setCreateTime(TimeUtil.getCurrentTime());

            userOrderMapper.insert(userOrder);
            if (insert.get() > 0) {
                // 推送消息
                OrderGoEasyDto orderGoEasyDto = new OrderGoEasyDto();
                orderGoEasyDto.setOrderId(orderId);
                orderGoEasyDto.setOrderStatus(0);
                orderGoEasyDto.setCreateTime(createTime);
                orderGoEasyDto.setOrderNumber(orderNumber);
                GoEasyUtil.messagePush("createOrder_chancel", orderGoEasyDto);

                return Result.ok(orderGoEasyDto);
            } else {
                return Result.err("待付款订单创建失败");
            }
        } else {
            //付款
            AtomicInteger insert = new AtomicInteger();
            orderVo.forEach(o -> {
                Order order = new Order();
                BeanUtils.copyProperties(o, order);

                order.setId(UUID.randomUUID().toString());
                order.setOrderId(orderId);
                //订单状态(0：待付款，1：待发货，2：待收货，3：已完成，4：售后)
                order.setOrderStatus(1);
                order.setCreateTime(createTime);
                order.setOrderNumber(orderNumber);
                order.setStrikeBargainTime(createTime);
                insert.addAndGet(orderMapper.insert(order));
            });
            UserOrder userOrder = new UserOrder();
            userOrder.setId(UUID.randomUUID().toString());
            userOrder.setOrderId(orderId);
            userOrder.setUserId(orderVo.get(0).getUserId());
            userOrder.setOrderStatus(1);
            userOrder.setOrderNumber(orderNumber);
            userOrder.setCreateTime(TimeUtil.getCurrentTime());

            userOrderMapper.insert(userOrder);
            if (insert.get() > 0) {
                // 用户余额扣除
                userBalanceService.userBalanceDeduction(orderVo.get(0).getUserId(), Double.parseDouble(orderVo.get(0).getTotalPrice()));
                // 扣除商品库存
                // List<DeductionCommodityData> list = new ArrayList<>();
                // orderVo.forEach(c -> {
                //     list.add(new DeductionCommodityData(c.getCommodityId(),c.getPurchaseQuantity()));
                // });
                // rabbitTemplate.convertAndSend(CommodityConstant.DEDUCTION_INVENTORY_COMMODITY_QUEUE, JSON.toJSONString(list));
                // 将使用到的优惠券标记为已使用
                orderVo.forEach(c -> {
                    CouponMQData mqData = new CouponMQData();
                    if (!"".equals(c.getMerchantCoupon())) {
                        mqData.setCouponId(c.getMerchantCoupon());
                    }
                    if (!"".equals(c.getUniversalCoupon())) {
                        mqData.setCouponId(c.getUniversalCoupon());
                    }
                    rabbitTemplate.convertAndSend(CouponConstant.UPDATE_COUPON_IS_USED_QUEUE, JSON.toJSONString(mqData));
                });
                // 推送消息
                OrderGoEasyDto orderGoEasyDto = new OrderGoEasyDto();
                orderGoEasyDto.setOrderId(orderId);
                orderGoEasyDto.setOrderStatus(1);
                orderGoEasyDto.setCreateTime(createTime);
                orderGoEasyDto.setStrikeBargainTime(createTime);
                orderGoEasyDto.setOrderNumber(orderNumber);
                GoEasyUtil.messagePush("createOrder_chancel", orderGoEasyDto);
                GoEasyUtil.messagePush("createOrderManageSys_chancel", orderGoEasyDto);

                return Result.ok(orderGoEasyDto);
            } else {
                return Result.err("订单创建失败");
            }
        }

    }

    /**
     * 等待支付订单id
     *
     * @param orderId 订单id
     * @return {@link Result}
     */
    @Override
    public Result getPendingPaymentOrderById(String orderId) {
        return Result.ok(selectOrderList(orderId, 0));
    }

    /**
     * 选择订单列表
     *
     * @param orderId 订单id
     * @param status  状态
     * @return {@link List}<{@link PendingPaymentOrder}>
     */
    List<PendingPaymentOrder> selectOrderList(String orderId, Integer status) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(orderId), Order::getOrderId, orderId);
        queryWrapper.eq(true, Order::getOrderStatus, status);
        List<Order> orders = orderMapper.selectList(queryWrapper);

        List<PendingPaymentOrder> list = new ArrayList<>();
        orders.forEach(o -> {
            PendingPaymentOrder paymentOrder = new PendingPaymentOrder();

            Business businessById = commodityApiClient.getBusinessById(o.getBusinessId());
            Commodity commodityById = commodityServiceApiClient.getCommodityById(o.getCommodityId());
            UserAddress userAddressById = userApiClient.getUserAddressById(o.getAddressId());

            paymentOrder.setBusiness(businessById);
            paymentOrder.setCommodity(commodityById);
            paymentOrder.setUserAddress(userAddressById);
            BeanUtils.copyProperties(o, paymentOrder);

            int discount = 0;
            if (!"".equals(o.getMerchantCoupon())) {
                UserCoupon userMerchantCouponById = userApiClient.getUserCouponById(o.getMerchantCoupon());
                MerchantCoupon merchantCoupon = couponApiClient.selectMerchantCouponById(userMerchantCouponById.getCouponId());
                discount += merchantCoupon.getDiscountAmount();
            }
            if (!"".equals(o.getUniversalCoupon())) {
                UserCoupon userUniversalCouponById = userApiClient.getUserCouponById(o.getUniversalCoupon());
                UniversalCoupon universalCoupon = couponApiClient.selectUniversalCouponById(userUniversalCouponById.getCouponId());
                discount += universalCoupon.getDiscountAmount();
            }
            paymentOrder.setDiscountLimit(String.valueOf(discount));

            if (status == 1) {
                paymentOrder.setOrder(o);
            }

            list.add(paymentOrder);
        });

        return list;
    }

    /**
     * 继续等待付款
     *
     * @param pendingPaymentOrder 等待付款订单
     * @return {@link Result}
     */
    @Override
    public Result pendingPaymentContinue(List<PendingPaymentOrder> pendingPaymentOrder) {

        String strikeBargainTime = TimeUtil.getCurrentTime();
        AtomicInteger update = new AtomicInteger();
        pendingPaymentOrder.forEach(c -> {
            LambdaUpdateWrapper<Order> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(!"".equals(c.getOrderId()), Order::getOrderId, c.getOrderId())
                    .set(!"".equals(c.getOrderId()), Order::getOrderStatus, 1)
                    .set(!"".equals(c.getOrderId()), Order::getStrikeBargainTime, strikeBargainTime);

            update.addAndGet(orderMapper.update(null, updateWrapper));
        });

        LambdaUpdateWrapper<UserOrder> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(!"".equals(pendingPaymentOrder.get(0).getOrderId()), UserOrder::getOrderId, pendingPaymentOrder.get(0).getOrderId())
                .set(!"".equals(pendingPaymentOrder.get(0).getOrderId()), UserOrder::getOrderStatus, 1);
        update.addAndGet(userOrderMapper.update(null, wrapper));

        if (update.get() > 0) {
            // 扣款
            // 用户余额扣除
            userBalanceService.userBalanceDeduction(pendingPaymentOrder.get(0).getUserId(), Double.parseDouble(pendingPaymentOrder.get(0).getTotalPrice()));
            // 扣除商品库存
            // List<DeductionCommodityData> list = new ArrayList<>();
            // pendingPaymentOrder.forEach(c -> {
            //     list.add(new DeductionCommodityData(c.getCommodityId(),c.getPurchaseQuantity()));
            // });
            // rabbitTemplate.convertAndSend(CommodityConstant.DEDUCTION_INVENTORY_COMMODITY_QUEUE, JSON.toJSONString(list));
            // 将使用到的优惠券标记为已使用
            pendingPaymentOrder.forEach(c -> {
                CouponMQData mqData = new CouponMQData();
                if (!"".equals(c.getMerchantCoupon())) {
                    mqData.setCouponId(c.getMerchantCoupon());
                }
                if (!"".equals(c.getUniversalCoupon())) {
                    mqData.setCouponId(c.getUniversalCoupon());
                }
                rabbitTemplate.convertAndSend(CouponConstant.UPDATE_COUPON_IS_USED_QUEUE, JSON.toJSONString(mqData));
            });
            // 推送消息
            OrderGoEasyDto orderGoEasyDto = new OrderGoEasyDto();
            orderGoEasyDto.setOrderId(pendingPaymentOrder.get(0).getOrderId());
            orderGoEasyDto.setOrderStatus(1);
            orderGoEasyDto.setCreateTime(pendingPaymentOrder.get(0).getCreateTime());
            orderGoEasyDto.setOrderNumber(pendingPaymentOrder.get(0).getOrderNumber());
            orderGoEasyDto.setStrikeBargainTime(strikeBargainTime);
            GoEasyUtil.messagePush("createOrder_chancel", orderGoEasyDto);
            GoEasyUtil.messagePush("createOrderManageSys_chancel", orderGoEasyDto);
            return Result.ok("下单成功");
        } else {
            return Result.err("失败");
        }

    }

    /**
     * 收到你付款完成订单id
     *
     * @param orderId 订单id
     * @return {@link Result}
     */
    @Override
    public Result getPaymentCompletedOrderById(String orderId) {
        return Result.ok(selectOrderList(orderId, 1));
    }

    /**
     * 把所有订单列表页面
     *
     * @param orderPage 订单页面
     * @return {@link Result}
     */
    @Override
    public Result getAllOrderListPage(OrderPage orderPage) {
        Page<UserOrder> page = new Page<>(orderPage.getCurrentPage(), orderPage.getPageSize());
        LambdaQueryWrapper<UserOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(orderPage.getOrderNumber()), UserOrder::getOrderNumber, orderPage.getOrderNumber());
        // -1  -> 全部
        if (orderPage.getOrderStatus() != -1) {
            queryWrapper.eq(true, UserOrder::getOrderStatus, orderPage.getOrderStatus());
        }

        if (!"".equals(orderPage.getUserName())) {
            MallUser mallUserByName = userApiClient.getMallUserByName(orderPage.getUserName());
            queryWrapper.eq(!"".equals(mallUserByName.getUserId()), UserOrder::getUserId, mallUserByName.getUserId());
        }

        Page<UserOrder> selectPage = userOrderMapper.selectPage(page, queryWrapper);
        OrderTdo orderTdo = new OrderTdo();
        orderTdo.setCurrentPage(selectPage.getCurrent());
        orderTdo.setPageSize(selectPage.getPages());
        orderTdo.setSize(selectPage.getSize());
        orderTdo.setTotal(selectPage.getTotal());

        ArrayList<UserOrderPage> list = new ArrayList<>();

        selectPage.getRecords().forEach(c -> {
            UserOrderPage userOrderPage = new UserOrderPage();
            String orderId = c.getOrderId();
            // 查询order表
            LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(!"".equals(orderId), Order::getOrderId, orderId);
            List<Order> orders = orderMapper.selectList(wrapper);

            Order order = orders.get(0);
            BeanUtils.copyProperties(order, userOrderPage);
            // 用户
            MallUser mallUserByUserId = userApiClient.getMallUserByUserId(order.getUserId());
            userOrderPage.setUserName(mallUserByUserId.getRealName());
            userOrderPage.setMallUser(mallUserByUserId);
            // 收货地址
            UserAddress userAddressById = userApiClient.getUserAddressById(order.getAddressId());
            userOrderPage.setAddressDetail(userAddressById.getDetailedAddress());
            userOrderPage.setUserAddress(userAddressById);

            list.add(userOrderPage);
        });
        orderTdo.setUserOrderPageList(list);
        return Result.ok(orderTdo);
    }

    /**
     * 得到订单细节
     *
     * @param orderId 订单id
     * @return {@link Result}
     */
    @Override
    public Result getOrderDetail(String orderId) {
        // 查询order表
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!"".equals(orderId), Order::getOrderId, orderId);
        List<Order> orders = orderMapper.selectList(wrapper);
        List<OrderCommodityList> orderCommodityLists = new ArrayList<>();
        orders.forEach(o -> {
            OrderCommodityList commodityList = new OrderCommodityList();
            //订单
            commodityList.setOrder(o);

            commodityList.setPurchaseQuantity(o.getPurchaseQuantity());
            // 商品
            commodityList.setCommodity(commodityServiceApiClient.getCommodityById(o.getCommodityId()));
            // 商家
            commodityList.setBusiness(commodityApiClient.getBusinessById(o.getBusinessId()));
            // 通用优惠券
            if (!"".equals(o.getUniversalCoupon())) {
                UniversalCoupon universalCoupon = couponApiClient.selectUniversalCouponById(o.getUniversalCoupon());
                commodityList.setUniversalCoupon(universalCoupon);
            }
            // 商家优惠券
            if (!"".equals(o.getMerchantCoupon())) {
                MerchantCoupon merchantCoupon = couponApiClient.selectMerchantCouponById(o.getMerchantCoupon());
                commodityList.setMerchantCoupon(merchantCoupon);
            }
            orderCommodityLists.add(commodityList);
        });

        return Result.ok(orderCommodityLists);
    }

    /**
     * 获取用户订单
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @Override
    public Result getUserOrder(String userId) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(userId), Order::getUserId, userId);
        List<UserOrderWxDto> userOrderWxDtos = new ArrayList<>();
        orderMapper.selectList(queryWrapper).forEach(o -> {
            UserOrderWxDto dto = new UserOrderWxDto();
            BeanUtils.copyProperties(o, dto);
            // 商品
            dto.setCommodity(commodityServiceApiClient.getCommodityById(o.getCommodityId()));
            // 商家
            dto.setBusiness(commodityApiClient.getBusinessById(o.getBusinessId()));
            userOrderWxDtos.add(dto);
        });
        return Result.ok(userOrderWxDtos);
    }

    /**
     * 立即发货
     *
     * @param shipImmediatelyData 立即发货数据
     * @return {@link Result}
     */
    @Override
    public Result shipImmediately(ShipImmediatelyData shipImmediatelyData) {
        //1、修改更新该订单状态【eyh_order/eyh_user_order -> order_status => 1-2】
        LambdaUpdateWrapper<Order> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        orderLambdaUpdateWrapper.eq(!"".equals(shipImmediatelyData.getOrderNumber()),
                        Order::getOrderNumber, shipImmediatelyData.getOrderNumber())
                .set(!"".equals(shipImmediatelyData.getOrderNumber()),
                        Order::getOrderStatus, 2);
        int update = orderMapper.update(null, orderLambdaUpdateWrapper);

        LambdaUpdateWrapper<UserOrder> userOrderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        userOrderLambdaUpdateWrapper.eq(!"".equals(shipImmediatelyData.getOrderNumber()),
                        UserOrder::getOrderNumber, shipImmediatelyData.getOrderNumber())
                .set(!"".equals(shipImmediatelyData.getOrderNumber()),
                        UserOrder::getOrderStatus, 2);
        int update1 = userOrderMapper.update(null, userOrderLambdaUpdateWrapper);
        if (update + update1 > 0) {
            //RabbitMQ
            //2、物流表中添加订单物流信息【eyh_express_order/eyh_logistics_task】
            rabbitTemplate.convertAndSend(LogisticsConstant.INSERT_LOGISTICS_QUEUE, JSON.toJSONString(shipImmediatelyData));
            //3、TODO 修改订单中商品库存数量
            rabbitTemplate.convertAndSend(CommodityConstant.DEDUCTION_INVENTORY_COMMODITY_QUEUE, JSON.toJSONString(shipImmediatelyData));
            //4、推送消息到小程序
            OrderGoEasyDto orderGoEasyDto = new OrderGoEasyDto();
            orderGoEasyDto.setOrderId(shipImmediatelyData.getOrderId());
            orderGoEasyDto.setOrderStatus(2);
            orderGoEasyDto.setOrderNumber(shipImmediatelyData.getOrderNumber());
            orderGoEasyDto.setDeliveryTime(TimeUtil.getCurrentTime());
            GoEasyUtil.messagePush("createOrder_chancel", orderGoEasyDto);
        }

        return update + update1 > 0 ? Result.ok("发货成功", null) : Result.err("发货失败");
    }

    /**
     * 被数量和订单id
     *
     * @param orderNumber 订单号
     * @param orderId     订单id
     * @return {@link List}<{@link Order}>
     */
    @Override
    public List<Order> getOrderByNumberAndId(String orderNumber, String orderId) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(orderNumber), Order::getOrderNumber, orderNumber);
        queryWrapper.eq(!"".equals(orderId), Order::getOrderId, orderId);
        return orderMapper.selectList(queryWrapper);
    }

    /**
     * 得到订单号码
     *
     * @param orderNumber 订单号
     * @return {@link UserOrder}
     */
    @Override
    public String getOrderByNumber(String orderNumber) {
        LambdaQueryWrapper<UserOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(orderNumber), UserOrder::getOrderNumber, orderNumber);
        return userOrderMapper.selectOne(queryWrapper).getOrderId();
    }

    /**
     * 被数量订单详细信息
     *
     * @param orderNumber 订单号
     * @return {@link Result}
     */
    @Override
    public Result getOrderDetailByNumber(String orderNumber) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(orderNumber), Order::getOrderNumber, orderNumber);

        List<Order> orders = orderMapper.selectList(queryWrapper);
        ReceivedOrderDto dto = new ReceivedOrderDto();

        UserAddress userAddressById = userApiClient.getUserAddressById(orders.get(0).getAddressId());
        dto.setUserAddress(userAddressById);

        List<ReceiveOrderCommodity> list = new ArrayList<>();
        orderMapper.selectList(queryWrapper).forEach(o -> {
            ReceiveOrderCommodity orderCommodityList = new ReceiveOrderCommodity();

            orderCommodityList.setOrder(o);

            orderCommodityList.setPurchaseQuantity(o.getPurchaseQuantity());
            // 商品
            orderCommodityList.setCommodity(commodityServiceApiClient.getCommodityById(o.getCommodityId()));
            // 商家
            orderCommodityList.setBusiness(commodityApiClient.getBusinessById(o.getBusinessId()));

            int discount = 0;
            if (!"".equals(o.getMerchantCoupon())) {
                UserCoupon userMerchantCouponById = userApiClient.getUserCouponById(o.getMerchantCoupon());
                MerchantCoupon merchantCoupon = couponApiClient.selectMerchantCouponById(userMerchantCouponById.getCouponId());
                discount += merchantCoupon.getDiscountAmount();
            }
            if (!"".equals(o.getUniversalCoupon())) {
                UserCoupon userUniversalCouponById = userApiClient.getUserCouponById(o.getUniversalCoupon());
                UniversalCoupon universalCoupon = couponApiClient.selectUniversalCouponById(userUniversalCouponById.getCouponId());
                discount += universalCoupon.getDiscountAmount();
            }
            orderCommodityList.setDiscountLimit(discount);
            list.add(orderCommodityList);
        });
        dto.setReceiveOrderCommodityList(list);
        return Result.ok(dto);
    }

    /**
     * 用户确认收据
     *
     * @param expressOrderId 快递订单id
     * @param orderNumber    订单号
     * @return {@link Result}
     */
    @Override
    public Result userConfirmReceipt(String expressOrderId, String orderNumber) {
        //修改order、userOrder表中订单状态
        //订单状态(0：待付款，1：待发货，2：待收货，3：已完成，4：售后)
        LambdaUpdateWrapper<Order> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        orderLambdaUpdateWrapper.eq(!"".equals(orderNumber), Order::getOrderNumber, orderNumber)
                .set(!"".equals(orderNumber), Order::getOrderStatus, 3);
        int update = orderMapper.update(null, orderLambdaUpdateWrapper);

        LambdaUpdateWrapper<UserOrder> userOrderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        userOrderLambdaUpdateWrapper.eq(!"".equals(orderNumber), UserOrder::getOrderNumber, orderNumber)
                .set(!"".equals(orderNumber), UserOrder::getOrderStatus, 3);
        int update1 = userOrderMapper.update(null, userOrderLambdaUpdateWrapper);

        //在订单物流轨迹表中新增已签收记录
        rabbitTemplate.convertAndSend(LogisticsConstant.USER_CONFIRM_RECEIPT_QUEUE, expressOrderId);

        LambdaQueryWrapper<UserOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(orderNumber), UserOrder::getOrderNumber, orderNumber);

        UserOrder userOrder = userOrderMapper.selectOne(queryWrapper);

        OrderGoEasyDto orderGoEasyDto = new OrderGoEasyDto();
        orderGoEasyDto.setOrderId(userOrder.getOrderId());
        orderGoEasyDto.setOrderStatus(3);
        orderGoEasyDto.setCreateTime(TimeUtil.getCurrentTime());
        orderGoEasyDto.setOrderNumber(orderNumber);
        GoEasyUtil.messagePush("createOrder_chancel", orderGoEasyDto);

        return update + update1 > 0 ? Result.ok("签收成功", null) : Result.err("签收失败");
    }

    /**
     * 得到订单数量
     *
     * @return {@link Result}
     */
    @Override
    public Result getOrderCount() {
        OrderCount count = new OrderCount();
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(true, Order::getOrderStatus, 0);
        queryWrapper.groupBy(true, Order::getOrderId);

        List<Order> orderList = orderMapper.selectList(queryWrapper);
        count.setTotalOrderCount(orderList.size());

        if (orderList.size() > 0) {
            double price = 0;
            for (Order o : orderList) {
                price += Double.parseDouble(o.getTotalPrice());
            }
            count.setTotalOrderPrice(price);
        }else{
            count.setTotalOrderPrice(0);
        }

        return Result.ok(count);
    }

    /**
     * 得到订单趋势
     *
     * @return {@link Result}
     */
    @Override
    public Result getOrderTrends() {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 获取20天前的时间
        LocalDateTime twentyDaysAgo = now.minusDays(30);

        // 构建LambdaQueryWrapper
        QueryWrapper<UserOrder> wrapper = new QueryWrapper<>();
        wrapper.select("DATE(create_time) as date", "COUNT(*) as count")
                .ge("create_time", twentyDaysAgo)
                .le("create_time", now)
                .groupBy("date")
                .orderByAsc("date");

        List<Map<String, Object>> result = userOrderMapper.selectMaps(wrapper);

        Map<String, Integer> resultMap = new LinkedHashMap<>();
        LocalDate currentDate = LocalDate.now().minusDays(29);
        for (Map<String, Object> map : result) {
            String dateStr = map.get("date").toString();
            Integer count = ((Long) map.get("count")).intValue();
            LocalDate date = LocalDate.parse(dateStr);
            while (!date.equals(currentDate)) {
                resultMap.put(currentDate.toString(), 0);
                currentDate = currentDate.plusDays(1);
            }
            resultMap.put(dateStr, count);
            currentDate = currentDate.plusDays(1);
        }
        while (resultMap.size() < 30) {
            resultMap.put(currentDate.toString(), 0);
            currentDate = currentDate.plusDays(1);
        }

        List<OrderMonthData> list = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : resultMap.entrySet()) {
            OrderMonthData data = new OrderMonthData();
            data.setDate(entry.getKey());
            data.setCount(entry.getValue());
            list.add(data);
        }

        return Result.ok(list);
    }

    /**
     * 得到订单状态情况
     *
     * @return {@link Result}
     */
    @Override
    public Result getOrderStatusSituation() {
        List<OrderStatusSituation> list = new ArrayList<>();
        // 订单状态(0：待付款，1：待发货，2：待收货，3：已完成，4：售后)
        String[] myArray = {"待付款", "待发货", "待收货", "已完成", "售后"};

        for (int i = 0; i < myArray.length; i++) {
            LambdaQueryWrapper<UserOrder> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(true, UserOrder::getOrderStatus, i);
            OrderStatusSituation situation = new OrderStatusSituation();
            situation.setName(myArray[i]);
            situation.setValue(userOrderMapper.selectCount(wrapper));
            list.add(situation);
        }
        return Result.ok(list);
    }

    @Override
    public Result getToadyOrderCount() {
        OrderCount count = new OrderCount();
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(true, Order::getCreateTime, LocalDate.now().toString() + " 00:00:00",
                LocalDate.now().toString() + " 23:59:59");
        queryWrapper.ne(true, Order::getOrderStatus, 0);
        queryWrapper.groupBy(true, Order::getOrderId);

        List<Order> orderList = orderMapper.selectList(queryWrapper);

        count.setTodayOrderCount(orderList.size());

       if(orderList.size() == 0){
           count.setTodayOrderPrice(0);
       }else{
           double price = 0;
           for (Order o : orderList) {
               price += Double.parseDouble(o.getTotalPrice());
           }
           count.setTodayOrderPrice(price);
       }

        return Result.ok(count);
    }

    /**
     * 获得业务总收入
     *
     * @return {@link Result}
     */
    @Override
    public Result getBusinessTotalIncome() {
        List<BusinessTotalIncome> businessTotalIncomes = new ArrayList<>();
        //获取所有商家
        List<Business> businessList = businessApiClient.getAllBusiness();
        businessList.forEach(b -> {
            LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.ne(true, Order::getOrderStatus, 0);
            queryWrapper.eq(true, Order::getBusinessId, b.getBusinessId());
            queryWrapper.groupBy(true, Order::getOrderId);

            BusinessTotalIncome income = new BusinessTotalIncome();
            income.setName(b.getCalloutContent());
            List<Order> orderList = orderMapper.selectList(queryWrapper);

            double price = 0;
            for (Order o : orderList) {
                price += Double.parseDouble(o.getTotalPrice());
            }
            income.setValue(price);

            businessTotalIncomes.add(income);
        });
        return Result.ok(businessTotalIncomes);
    }

    /**
     * 变热销售商品比例
     *
     * @return {@link Result}
     */
    @Override
    public Result getHotSalesCommodityProportion() {
        ArrayList<BusinessTotalIncome> list = new ArrayList<>();

        List<Classification> oneClassificationList = commodityServiceApiClient.getAllOneClassificationList();
        oneClassificationList.forEach(c -> {
            // c.getClassificationId()
        });

        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();

        BusinessTotalIncome income = new BusinessTotalIncome();

        return Result.ok(list);
    }

    /**
     * 得到区域销售排名
     *
     * @return {@link Result}
     */
    @Override
    public Result getRegionalSalesRanking() {
        List<RegionalSalesRankingData> regionalSalesRanking = orderMapper.getRegionalSalesRanking();
        List<RegionalSalesRankingData> list = new ArrayList<>();

        regionalSalesRanking.forEach(r -> {
            RegionalSalesRankingData data = new RegionalSalesRankingData();

            UserAddress userAddressById = userApiClient.getUserAddressById(r.getName());
            data.setName(userAddressById.getLocation());
            data.setValue(r.getValue());

            list.add(data);
        });

        return Result.ok(list);
    }

}
