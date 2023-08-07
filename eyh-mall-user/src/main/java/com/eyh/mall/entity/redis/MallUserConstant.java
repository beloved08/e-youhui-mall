package com.eyh.mall.entity.redis;

/**
 * @author 李平
 * @Date 2023-3-6
 */
public class MallUserConstant {

    /**
     * 通过openID获取的用户
     */
    public static final String MALL_USER_OPENID = "mall_user_openid:";

    /**
     * 通过openID获取的用户的TTL
     */
    public static final long MALL_USER_OPENID_TTL = 300L;

    /**
     * 通过phone获取的用户
     */
    public static final String MALL_USER_PHONE = "mall_user_phone:";

    /**
     * 通过phone获取的用户的TTL
     */
    public static final long MALL_USER_PHONE_TTL = 300L;

    /**
     * 通过phone,password获取的用户
     */
    public static final String MALL_USER_PHONE_PASSWORD = "mall_user_phone_password:";

    /**
     * 通过phone,password获取的用户的TTL
     */
    public static final long MALL_USER_PHONE_PASSWORD_TTL = 300L;
}
