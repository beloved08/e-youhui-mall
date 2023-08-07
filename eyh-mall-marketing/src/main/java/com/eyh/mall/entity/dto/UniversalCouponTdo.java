package com.eyh.mall.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 李平
 * @NAME UniversalCouponTdo
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-03-29 13:15:42
 * @Description 分页条件搜索数据封装
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UniversalCouponTdo {

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
     * 通用券dto列表
     */
    private List<UniversalCouponDto> universalCouponDtoList;

}
