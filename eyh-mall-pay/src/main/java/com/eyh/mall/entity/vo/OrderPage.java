package com.eyh.mall.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME OrderPage
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-04-05 19:22:12
 * @Description 订单分页
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPage {

    /**
     * 当前页面
     */
    private Integer currentPage;
    /**
     * 页面大小
     */
    private Integer pageSize;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 用户名
     */
    private String userName;
    /**
     * 订单号
     */
    private String orderNumber;
}
