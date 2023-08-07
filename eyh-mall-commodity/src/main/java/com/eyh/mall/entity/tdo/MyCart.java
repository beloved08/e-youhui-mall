package com.eyh.mall.entity.tdo;

import com.eyh.mall.entity.Commodity;
import com.eyh.mall.entity.merchantStores.Business;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME MyCart
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期日
 * @Date 2023-04-02 14:06:39
 * @Description 我的购物车
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyCart {

    /**
     * 业务
     */
    private Business business;
    /**
     * 商品
     */
    private Commodity commodity;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 选择
     */
    private Boolean selected;

}
