package com.eyh.mall.feign.client.pay;

import com.eyh.mall.entity.common.Result;
import com.eyh.mall.feign.config.FeignConfig;
import com.eyh.mall.feign.entity.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author 李平
 * @NAME OrderApiClient
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期日
 * @Date 2023-04-09 21:33:35
 * @Description 商品订单接口
 */
@FeignClient(value = "eyh-mall-pay", configuration = FeignConfig.class)
public interface OrderApiClient {

    /**
     * 得到订单细节
     *
     * @param orderId 订单id
     * @return {@link Result}
     */
    @GetMapping("/pay/order/getOrderDetail/{orderId}")
    Result getOrderDetail(@PathVariable String orderId);

    /**
     * 订单数量id
     *
     * @param orderNumber 订单号
     * @param orderId     订单id
     * @return {@link List}<{@link Order}>
     */
    @GetMapping("/pay/order/getOrderByNumberId/{orderNumber}/{orderId}")
    List<Order> getOrderByNumberId(@PathVariable String orderNumber, @PathVariable String orderId);

    /**
     * 得到订单号码
     *
     * @param orderNumber 订单号
     * @return {@link String}
     */
    @GetMapping("/pay/order/getOrderByNumber/{orderNumber}")
    String getOrderByNumber(@PathVariable String orderNumber);
}
