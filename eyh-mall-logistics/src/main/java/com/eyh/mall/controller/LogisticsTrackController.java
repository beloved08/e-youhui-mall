package com.eyh.mall.controller;

import com.eyh.mall.entity.common.Result;
import com.eyh.mall.service.LogisticsTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 李平
 * @NAME LogisticsTrackController
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-04-10 16:33:13
 * @Description 物流轨迹
 */
@RestController
@RequestMapping("/logistics")
public class LogisticsTrackController {

    @Autowired
    private LogisticsTrackService logisticsTrackService;

    /**
     * 把物流跟踪细节
     *
     * @param expressOrderId 快递订单id
     * @return {@link Result}
     */
    @GetMapping("/getLogisticsTrackDetail/{expressOrderId}")
    public Result getLogisticsTrackDetail(@PathVariable String expressOrderId){
        return logisticsTrackService.getLogisticsTrackDetail(expressOrderId);
    }

}
