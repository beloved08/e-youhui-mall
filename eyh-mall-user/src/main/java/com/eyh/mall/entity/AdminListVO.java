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
public class AdminListVO {

    private String account;
    private Integer frozen;
    private String roleName;
    private String roleDesc;

}
