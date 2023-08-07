package com.eyh.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyh.mall.entity.UserRole;
import com.eyh.mall.entity.Role;

/**
 * @author 李平
 * @Date 2023-1-27
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 根据用户账号获取用户角色
     * @param account
     * @return Role
     */
    Role getRoleByAccount(String account);

}
