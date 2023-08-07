package com.eyh.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李平
 * @Date 2023-1-30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {

    private String id;
    private String account;
    private String password;
    private String userId;
    /**
     * 该账号是否被冻结
     * 0：正常
     * 1：被冻结
     */
    private Integer frozen;
    /**
     * 管理员类型
     * 0：超级管理员
     * 1：商家管理员
     */
    private Integer type;

}
