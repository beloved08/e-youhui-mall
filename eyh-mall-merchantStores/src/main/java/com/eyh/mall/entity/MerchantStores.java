package com.eyh.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李平
 * @date 2023-3-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantStores {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 业务用户名
     */
    private String businessUserName;
    /**
     * 业务用户身份证
     */
    private String businessUserIdCard;
    /**
     * 业务用户电话
     */
    private String businessUserPhone;
    /**
     * 业务用户电子邮件
     */
    private String businessUserEmail;
    /**
     * 业务用户性
     */
    private String businessUserSex;
    /**
     * 业务用户年龄
     */
    private Integer businessUserAge;
    /**
     * 业务名称
     */
    private String businessName;
    /**
     * 商店名字
     */
    private String shopName;
    /**
     * 商务电话
     */
    private String businessPhone;
    /**
     * 商业城市
     */
    private String businessCity;
    /**
     * 业务描述
     */
    private String businessDescribe;
    /**
     * 企业详细地址
     */
    private String businessDetailAddress;
    /**
     * 商业城市纬度
     */
    private String businessCityLatitude;
    /**
     * 商业城市经度
     */
    private String businessCityLongitude;
    /**
     * 业务标识
     */
    private String businessLogo;
    /**
     * 企业详细地址纬度
     */
    private String businessDetailAddressLatitude;
    /**
     * 企业详细地址经度
     */
    private String businessDetailAddressLongitude;

    /**
     * 图标路径
     */
    private String iconPath;

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
     * 标记id
     */
    private long markerId;
    /**
     * callout内容
     */
    private String calloutContent;

}
