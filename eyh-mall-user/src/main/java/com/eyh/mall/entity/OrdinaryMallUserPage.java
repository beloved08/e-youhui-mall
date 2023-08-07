package com.eyh.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME OrdinaryMallUserPage
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期五
 * @Date 2023-03-24 21:38:42
 * @Description 微信商城用户分页查询数据封装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdinaryMallUserPage {

    /**
     * 当前页面
     */
    private Integer currentPage;
    /**
     * 页面大小
     */
    private Integer pageSize;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 电话
     */
    private String phone;
    /**
     * 性
     */
    private String sex;
    /**
     * 类型
     */
    private String type;
    /**
     * 状态
     */
    private String status;

}
