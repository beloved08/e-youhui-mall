package com.eyh.mall.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME ExpressOrderPage
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-04-10 16:19:05
 * @Description 快递订单物流
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpressOrderPage {

    /**
     * 当前页面
     */
    private Integer currentPage;
    /**
     * 页面大小
     */
    private Integer pageSize;

    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 发送者名字
     */
    private String senderName;
    /**
     * 发送方手机
     */
    private String senderPhone;
    /**
     * 接收器名字
     */
    private String receiverName;
    /**
     * 接收电话
     */
    private String receiverPhone;

    /**
     * 接收方地址
     */
    private String receiverAddress;

    /**
     * 发送方地址
     */
    private String senderAddress;

}
