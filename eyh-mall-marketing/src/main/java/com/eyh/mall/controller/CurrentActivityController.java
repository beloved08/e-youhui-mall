package com.eyh.mall.controller;

import com.eyh.mall.entity.common.Result;
import com.eyh.mall.service.CurrentActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 李平
 * @NAME CurrentActivityController
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期二
 * @Date 2023-04-18 20:51:59
 * @Description 当前活动
 */
@RestController
@RequestMapping("/marketing")
public class CurrentActivityController {

    @Autowired
    private CurrentActivityService currentActivityService;

    /**
     * 设置当前促销活动
     *
     * @param promotionActivityId 推广活动id
     * @return {@link Result}
     */
    @GetMapping("/setCurrentPromotionActivity/{promotionActivityId}")
    public Result setCurrentPromotionActivity(@PathVariable String promotionActivityId){
        return currentActivityService.setCurrentActivity(promotionActivityId);
    }

}
