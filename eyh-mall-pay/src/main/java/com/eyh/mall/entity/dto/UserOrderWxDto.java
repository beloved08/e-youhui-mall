package com.eyh.mall.entity.dto;

import com.eyh.mall.entity.Order;
import com.eyh.mall.entity.UserOrder;
import com.eyh.mall.entity.merchantStores.Business;
import com.eyh.mall.feign.entity.Commodity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME UserOrderWxDto
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期五
 * @Date 2023-04-07 15:22:32
 * @Description 订单
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderWxDto extends Order {

    /**
     * 商品
     */
    private Commodity commodity;
    /**
     * 业务
     */
    private Business business;

}
