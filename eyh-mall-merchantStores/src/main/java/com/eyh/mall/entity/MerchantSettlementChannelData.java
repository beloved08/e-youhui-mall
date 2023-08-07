package com.eyh.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME MerchantSettlementChannelData
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-03-22 18:59:56
 * @Description 商家入驻申请时服务端向客户端管理系统推送消息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantSettlementChannelData {

    /**
     * 业务名称
     */
    private String businessName;
    /**
     * 商店名字
     */
    private String shopName;

    /**
     * 店阿凡达
     */
    private String shopAvatar;

    /**
     * 当前时间
     */
    private String currentTime;

    /**
     * 味精
     */
    private String msg;

    /**
     * 标题
     */
    private String title;

}
