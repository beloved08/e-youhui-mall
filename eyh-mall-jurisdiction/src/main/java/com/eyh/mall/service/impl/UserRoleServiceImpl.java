package com.eyh.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.entity.UserRole;
import com.eyh.mall.entity.redis.RedisConstant;
import com.eyh.mall.entity.user.Admin;
import com.eyh.mall.feign.client.user.UserApiClient;
import com.eyh.mall.entity.Role;
import com.eyh.mall.mapper.RoleMapper;
import com.eyh.mall.mapper.UserRoleMapper;
import com.eyh.mall.redis.RedisUtil;
import com.eyh.mall.service.UserRoleService;
import com.eyh.mall.util.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 李平
 * @Date 2023-2-14
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserApiClient userApiClient;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JsonUtil<Role> jsonUtil;

    @Override
    public Role getRoleByAccount(String account) {
        String userRolePrefix = RedisConstant.USER_ROLE_PREFIX + account;
        boolean hasKey = redisUtil.hasKey(userRolePrefix);
        if(!hasKey){
            // key不存在
            return selectRole(account);
        }
        //存在
        Role role = jsonUtil.toJavaBean(redisUtil.get(userRolePrefix), Role.class);
        if(role == null){
            //为空,查询数据库
            return selectRole(account);
        }
        //不为空,返回Redis数据
        return role;
    }

    public Role selectRole(String account) {
        //根据账号查询数据库获取用户信息
        Admin admin = userApiClient.selectAdmin(account);

        LambdaQueryWrapper<UserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRoleLambdaQueryWrapper.eq(admin != null, UserRole::getUserId,admin.getUserId());
        UserRole userRole = userRoleMapper.selectOne(userRoleLambdaQueryWrapper);

        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleLambdaQueryWrapper.eq(userRole != null,Role::getRoleId,userRole.getRoleId());
        Role role = roleMapper.selectOne(roleLambdaQueryWrapper);

        String userRolePrefix = RedisConstant.USER_ROLE_PREFIX + account;
        // 存入Redis
        redisUtil.set(userRolePrefix,role,RedisConstant.USER_ROLE_TIME);
        return role;
    }

}
