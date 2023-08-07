package com.eyh.mall.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME UserAddressPage
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-03-27 17:11:29
 * @Description 分页和条件搜索数据封装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressPage {

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
     * 收货人
     */
    private String consignee;

}
