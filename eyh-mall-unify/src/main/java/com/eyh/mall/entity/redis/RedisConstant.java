package com.eyh.mall.entity.redis;

/**
 * @author 李平
 * @Date 2023-2-15
 */
public class RedisConstant {
    /**
     * 用户为管理员前缀
     */
    public static final String ADMIN_PREFIX = "user_admin:";

    /**
     * 用户为管理员类型过期时间
     */
    public static final Integer ADMIN_TIME = 120*60;

    /**
     * 用户角色菜单信息前缀
     */
    public static final String USER_ROLE_MENU_PREFIX = "user_role_menu:";

    /**
     * 用户角色菜单信息过期时间
     */
    public static final Integer USER_ROLE_MENU_TIME = 15*24*60*60;

    /**
     * 用户角色信息前缀
     */
    public static final String USER_ROLE_PREFIX = "user_role:";

    /**
     * 用户角色信息过期时间
     */
    public static final Integer USER_ROLE_TIME = 15*24*60*60;

}
