package com.eyh.mall.entity.redis;

/**
 * @author 李平
 * @Date 2023-3-7
 */
public class SendCode {

    public static final String ACCOUNT_PWD_REGISTER_CODE = "account_pwd_register_code:";
    public static final long ACCOUNT_PWD_REGISTER_CODE_TTL = 300L;

    public static final String PHONE_LOGIN_CODE = "phone_login_code:";
    public static final long PHONE_LOGIN_CODE_TTL = 300L;

    public static final String WX_BIND_PHONE_CODE = "wx_bind_phone_code:";
    public static final long WX_BIND_PHONE_CODE_TTL = 300L;

    public static final String NATIONAL_PROMOTION_PEOPLE_CODE = "national_promotion_people_code:";
    public static final long NATIONAL_PROMOTION_PEOPLE_CODE_TTL = 300L;
}
