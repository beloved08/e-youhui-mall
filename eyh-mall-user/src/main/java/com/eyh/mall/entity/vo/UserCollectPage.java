package com.eyh.mall.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME UserCollectPage
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-03-27 21:42:34
 * @Description 分页+条件搜索
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCollectPage {

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

}
