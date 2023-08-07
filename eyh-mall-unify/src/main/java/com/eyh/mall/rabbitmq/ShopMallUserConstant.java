package com.eyh.mall.rabbitmq;

/**
 * @author 李平
 * @Date 2023-3-6
 */
public class ShopMallUserConstant {

    /**
     * 微信登录保存用户信息队列名称
     */
    public static final String SAVE_MALL_USER_QUEUE = "save.mall.user.queue";

    /**
     * 微信用户账号密码注册发送验证码队列
     */
    public static final String MALL_USER_REGISTER_SEND_CODE_QUEUE = "mall.user.register.send.code.queue";

    /**
     * 验证业务队列
     */
    public static final String VERIFY_BUSINESS_QUEUE = "verify.business.queue";

}
