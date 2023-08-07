package com.eyh.mall.entity.dto;

import com.eyh.mall.entity.UserOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 李平
 * @NAME OrderTdo
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-04-05 20:32:30
 * @Description 分页
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderTdo {
    /**
     * 当前页面
     */
    private long currentPage;
    /**
     * 页面大小
     */
    private long pageSize;
    /**
     * 大小
     */
    private long size;
    /**
     * 总计
     */
    private long total;

    /**
     * 用户订单列表
     */
    private List<UserOrderPage> userOrderPageList;

}
