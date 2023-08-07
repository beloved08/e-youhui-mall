package com.eyh.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.config.JWTConfig;
import com.eyh.mall.entity.*;
import com.eyh.mall.entity.redis.RedisConstant;
import com.eyh.mall.entity.token.TokenSubject;
import com.eyh.mall.entity.user.Admin;
import com.eyh.mall.feign.client.user.UserApiClient;
import com.eyh.mall.entity.Role;
import com.eyh.mall.mapper.*;
import com.eyh.mall.redis.RedisUtil;
import com.eyh.mall.service.MenuService;
import com.eyh.mall.service.UserRoleService;
import com.eyh.mall.util.json.JsonUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李平
 * @Date 2023-1-27
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private UserMenuMapper userMenuMapper;

    @Autowired
    private UserApiClient userApiClient;

    @Autowired
    private JWTConfig jwtConfig;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JsonUtil<Menu> jsonUtil;

    @Override
    public List<Menu> selectUserMenu(String authorization) {
        Claims claim = jwtConfig.getTokenClaim(authorization);
        TokenSubject tokenSubject = JSON.parseObject(claim.getSubject(),TokenSubject.class);
        //根据账号查询数据库获取用户信息
        Admin admin = userApiClient.selectAdmin(tokenSubject.getUserName());
        Role role = userRoleService.getRoleByAccount(admin.getAccount());

        String roleMenuPrefix = RedisConstant.USER_ROLE_MENU_PREFIX + role.getRoleName();
        if(!redisUtil.hasKey(roleMenuPrefix)){
            //key不存在，查询数据库
            return getMenuList(role);
        }
        // key存在
        List<Object> redisMenuList = redisUtil.lGet(roleMenuPrefix, 0, -1);
        if (redisMenuList.size() == 0){
            // 为空
            return getMenuList(role);
        }
        // 不为空
        return jsonUtil.toListBean(redisMenuList, Menu.class);
    }

    public List<Menu> getMenuList(Role role) {
        String menuKey = RedisConstant.USER_ROLE_MENU_PREFIX + role.getRoleName();
        if("admin".equals(role.getRoleName())){
            // 当前登录的角色为超级管理员
            LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
            List<Menu> menuList = menuMapper.selectList(menuLambdaQueryWrapper);
            if (menuList.size() == 0) {
                return null;
            }
            //存入Redis
            redisUtil.lSet(menuKey,menuList,RedisConstant.USER_ROLE_MENU_TIME);
            return menuList;
        } else {
            // 当前登录的用户为商家管理员
            // 获取登录用户的菜单按钮信息
            LambdaQueryWrapper<UserMenu> userMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userMenuLambdaQueryWrapper.eq(role.getRoleId() != null, UserMenu::getRoleId,role.getRoleId());
            List<UserMenu> userMenuList = userMenuMapper.selectList(userMenuLambdaQueryWrapper);
            List<Menu> menuList = new ArrayList<>();
            for (UserMenu um : userMenuList){
                LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();

                menuLambdaQueryWrapper.eq(um.getMenuId() != null, Menu::getId,um.getMenuId());
                Menu menu = menuMapper.selectOne(menuLambdaQueryWrapper);
                menuList.add(menu);
            }
            //存入Redis
            redisUtil.lSet(menuKey,menuList,RedisConstant.USER_ROLE_MENU_TIME);
            return menuList;
        }
    }

}
