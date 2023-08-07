package com.eyh.mall.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME CardPage
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期六
 * @Date 2023-03-25 16:10:38
 * @Description 分页查询用户购物车数据的分页搜索数据封装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardPage {

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
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

}
