package com.eyh.mall.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 李平
 * @NAME UserBalanceTdo
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期六
 * @Date 2023-04-01 09:58:06
 * @Description 用户余额和变动明细
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBalanceIntegralTdo {

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
     * 用户平衡dto列表
     */
    private List<UserBalanceIntegralDto> userBalanceDtoList;

}
