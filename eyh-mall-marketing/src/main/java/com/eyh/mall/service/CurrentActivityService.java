package com.eyh.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyh.mall.entity.CurrentActivity;
import com.eyh.mall.entity.common.Result;

/**
 * @Author 李平
 * @NAME CurrentActivityService
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期二
 * @Date 2023-04-18 20:21:46
 * @Description 当前活动
 */
public interface CurrentActivityService extends IService<CurrentActivity> {

    /**
     * 设置当前活动
     *
     * @param promotionActivityId 推广活动id
     */
    Result setCurrentActivity(String promotionActivityId);

    /**
     * 得到当前活动
     *
     * @return {@link CurrentActivity}
     */
    CurrentActivity getCurrentActivity();

}
