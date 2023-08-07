package com.eyh.mall.entity.redis;

/**
 * @author 李平
 * @Date 2023-3-10
 */
public class MerchantStoreConstant {

    /**
     * 通过 businessName,businessName 获取商家店铺
     */
    public static final String BUSINESS_SHOP_NAME = "businessName_businessName:";

    /**
     * 通过 businessName,businessName 获取商家店铺的TTL
     */
    public static final long BUSINESS_SHOP_TTL = 300L;

    /**
     * 通过 userId 获取商家店铺所有信息集合
     */
    public static final String MERCHANT_STORES_LIST = "merchant_stores_list:";

    /**
     * 通过 userId 获取商家店铺所有信息集合的TTL
     */
    public static final long MERCHANT_STORES_LIST_TTL = 100L;

    /**
     * 通过 businessId 获取商家店铺人员信息
     */
    public static final String BUSINESS_USER_ID = "business_user:";

    /**
     * 通过 businessId 获取商家店铺人员信息的TTL
     */
    public static final long BUSINESS_USER_ID_TTL = 100L;

    /**
     * 通过 businessId 获取商家店铺信息
     */
    public static final String VERIFY_BUSINESS_ID = "verify_business:";

    /**
     * 通过 businessId 获取商家店铺信息的TTL
     */
    public static final long VERIFY_BUSINESS_ID_TTL = 100L;

    /**
     * 通过 businessId 获取商家店铺信息
     */
    public static final String BUSINESS_INFO_ID = "business_info_id:";

    /**
     * 通过 businessId 获取商家店铺信息的TTL
     */
    public static final long BUSINESS_INFO_ID_TTL = 100L;

}
