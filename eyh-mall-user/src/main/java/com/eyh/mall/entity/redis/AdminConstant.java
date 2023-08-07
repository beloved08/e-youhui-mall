package com.eyh.mall.entity.redis;

/**
 * @author 李平
 * @Date 2023-2-17
 */
public class AdminConstant {

    /**
     * 超级管理员集合 key
     */
    public static final String USER_ADMIN_LIST = "admin_list:";

    /**
     * 超级管理员集合过期时间
     */
    public static final long USER_ADMIN_LIST_TIME = 300L;
}
