package com.eyh.mall.controller;

import com.eyh.mall.entity.Admin;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 李平
 * @Date 2023-1-30
 */
@RestController
@RequestMapping("/user")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 根据账号查询
     * @param account
     * @return Result
     */
    @GetMapping("/selectAdmin")
    public Admin selectAdminOne(@RequestParam String account){
        return adminService.selectAdminOne(account);
    }

    /**
     * 获取超级管理集合
     * @return Result
     */
    @GetMapping("/adminList/{type}")
    public Result adminList(@PathVariable Integer type){
        return adminService.getAllAdminList(type);
    }

}
