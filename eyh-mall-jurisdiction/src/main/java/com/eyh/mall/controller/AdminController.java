package com.eyh.mall.controller;

import com.alibaba.fastjson.JSON;
import com.eyh.mall.config.JWTConfig;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.token.TokenSubject;
import com.eyh.mall.entity.user.Admin;
import com.eyh.mall.entity.Role;
import com.eyh.mall.service.MenuService;
import com.eyh.mall.service.UserRoleService;
import com.eyh.mall.util.TimeDateFormat;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author 李平
 * @Date 2023-1-27
 */
@RestController
@RequestMapping("/jurisdiction")
public class AdminController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private JWTConfig jwtConfig;

    /**
     * 管理员(超级管理员、商家管理员)登录
     * @param loginJurisdiction
     * @return Result
     */
    @PostMapping(value = "/login")
    public Result adminLogin(@RequestBody Admin loginJurisdiction) {
        Subject subject = SecurityUtils.getSubject();

        AuthenticationToken token = new UsernamePasswordToken(loginJurisdiction.getAccount(),loginJurisdiction.getPassword());
        try {
            subject.login(token);
            // 获取用户角色信息
            Role role = userRoleService.getRoleByAccount(loginJurisdiction.getAccount());
            TokenSubject tokenSubject = new TokenSubject();
            tokenSubject.setUserName(loginJurisdiction.getAccount());
            tokenSubject.setUserRole(role.getRoleName());
            tokenSubject.setSessionId(subject.getSession().getId().toString());
            tokenSubject.setLastAccessTime(TimeDateFormat.customFormat(subject.getSession().getLastAccessTime()));

            return Result.ok(jwtConfig.createToken(JSON.toJSONString(tokenSubject)),tokenSubject);
        } catch (IncorrectCredentialsException e) {
            return Result.err("密码错误!");
        } catch (LockedAccountException e) {
            return Result.err("登录失败，该用户已被冻结!");
        } catch (AuthenticationException e) {
            return Result.err("该用户不存在!");
        } catch (Exception e) {
            return Result.err("用户登录时发生异常");
        }
    }

    @GetMapping("/logout")
    public Result logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return Result.ok("退出成功");
    }

    /**
     * 根据用户编号查询用户所有菜单
     * @return Result
     */
    @GetMapping(value = "/selectUserMenu")
    public Result selectUserMenu(@RequestHeader("authorization") String authorization) {
        if(StringUtils.isEmpty(authorization)){
            return Result.err("登录失败，请重新登录");
        }

        // 根据用户编号查询用户所有可见菜单
        return Result.ok(menuService.selectUserMenu(authorization));
    }

}
