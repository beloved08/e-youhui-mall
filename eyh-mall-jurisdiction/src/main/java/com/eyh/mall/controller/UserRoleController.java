package com.eyh.mall.controller;

import com.eyh.mall.entity.Role;
import com.eyh.mall.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李平
 * @Date 2023-2-17
 */
@RestController
@RequestMapping("/jurisdiction")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 查询用户角色信息
     * @param account
     * @return Role
     */
    @GetMapping("/selectRole/{account}")
    public Role selectRole(@PathVariable(value = "account") String account){
        return userRoleService.getRoleByAccount(account);
    }
}
