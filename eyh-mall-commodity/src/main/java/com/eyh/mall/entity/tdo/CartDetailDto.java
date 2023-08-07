package com.eyh.mall.entity.tdo;

import com.eyh.mall.entity.Commodity;
import com.eyh.mall.entity.merchantStores.Business;
import com.eyh.mall.feign.entity.MallUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME CartDetailDto
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期六
 * @Date 2023-03-25 16:41:06
 * @Description 每一行购物车数据属性
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailDto {

    private String userName;
    private String businessName;
    private String addTime;
    private String commodityName;
    private Commodity commodity;
    private Business business;
    private MallUser mallUser;

}
