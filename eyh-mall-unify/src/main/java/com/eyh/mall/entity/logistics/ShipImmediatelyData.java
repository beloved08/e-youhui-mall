package com.eyh.mall.entity.logistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME shipImmediatelyData
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期日
 * @Date 2023-04-09 10:22:26
 * @Description 发货
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipImmediatelyData {

    /**
     * 订单号
     */
    private String orderNumber;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 下订单用户id
     */
    private String placeOrderUserId;
    /**
     * 物流公司
     */
    private String logisticsCompany;
    /**
     * 收件人身份证
     */
    private String addresseeId;
    /**
     * 发送者名字
     */
    private String senderName;
    /**
     * 发送方手机
     */
    private String senderPhone;
    /**
     * 发送方地址
     */
    private String senderAddress;

}
