package com.eyh.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyh.mall.entity.Admin;
import com.eyh.mall.entity.common.Result;

/**
 * @author 李平
 * @Date 2023-2-13
 */
public interface AdminService extends IService<Admin> {

    /**
     * 根据账号获取admin
     * @param account
     * @return Admin
     */
    Admin selectAdminOne(String account);

    /**
     * 获取超级管理集合
     * @return Result
     */
    Result getAllAdminList(Integer type);
}
