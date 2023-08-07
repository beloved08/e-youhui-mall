package com.eyh.mall.entity.dto;

import com.eyh.mall.entity.Order;
import com.eyh.mall.entity.merchantStores.Business;
import com.eyh.mall.feign.entity.Commodity;
import com.eyh.mall.feign.entity.UserAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 李平
 * @NAME ReceivedOrderDto
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期二
 * @Date 2023-04-11 10:17:29
 * @Description 待收货订单
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceivedOrderDto {

    /**
     * 用户地址
     */
    private UserAddress userAddress;

    /**
     * 收到订单商品列表
     */
    private List<ReceiveOrderCommodity> receiveOrderCommodityList;

}
