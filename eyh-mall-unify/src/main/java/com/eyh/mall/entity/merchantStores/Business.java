package com.eyh.mall.entity.merchantStores;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME Business
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期日
 * @Date 2023-03-12 16:44:40
 * @Description 商家店铺实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Business {

    /**
     * id
     */
    private String id;
    /**
     * 业务标识
     */
    private String businessId;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 业务名称
     */
    private String businessName;
    /**
     * 商务电话
     */
    private String businessPhone;
    /**
     * 商业城市
     */
    private String businessCity;
    /**
     * 业务标识
     */
    private String businessLogo;
    /**
     * 业务描述
     */
    private String businessDescribe;
    /**
     * 图标路径
     */
    private String iconPath;
    /**
     * 标记id
     */
    private long markerId;
    /**
     * callout内容
     */
    private String calloutContent;
    /**
     * 企业详细地址
     */
    private String businessDetailAddress;
    /**
     * 商店名字
     */
    private String shopName;
    /**
     * 商业城市纬度
     */
    private String businessCityLatitude;
    /**
     * 商业城市经度
     */
    private String businessCityLongitude;
    /**
     * 企业详细地址纬度
     */
    private String businessDetailAddressLatitude;
    /**
     * 企业详细地址经度
     */
    private String businessDetailAddressLongitude;
    /**
     * 业务状况
     * 商家状态(0：正常，1：已注销，2：未审核，3：已审核，4：信息有误)
     */
    private Integer businessStatus;
    /**
     * 验证状态
     * 管理员审核状态(0：审核通过，1：审核不通过，2：审核信息错误，3：待审核)
     */
    private Integer verifyStatus;

    /**
     * 验证desc
     */
    private String verifyDesc;

}
