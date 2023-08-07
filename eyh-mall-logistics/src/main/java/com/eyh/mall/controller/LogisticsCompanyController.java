package com.eyh.mall.controller;

import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.ExpressOrderPage;
import com.eyh.mall.service.LogisticsCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 李平
 * @NAME LogisticsCompanyController
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期五
 * @Date 2023-04-07 18:22:39
 * @Description 物流公司控制层
 */
@RestController
@RequestMapping("/logistics")
public class LogisticsCompanyController {

    @Autowired
    private LogisticsCompanyService logisticsCompanyService;

    /**
     * 让物流公司
     *
     * @return {@link Result}
     */
    @GetMapping("/getLogisticsCompany")
    public Result getLogisticsCompany(){
        return logisticsCompanyService.getLogisticsCompany();
    }

    /**
     * forbiddenis物流公司
     *
     * @param logisticsCompanyId 物流公司id
     * @param status             状态
     * @return {@link Result}
     */
    @GetMapping("/forbiddenisLogisticsCompany/{logisticsCompanyId}/{status}")
    public Result forbiddenisLogisticsCompany(@PathVariable String logisticsCompanyId,@PathVariable Integer status){
        return logisticsCompanyService.forbiddenisLogisticsCompany(logisticsCompanyId,status);
    }

    /**
     * 物流公司名称
     *
     * @return {@link Result}
     */
    @GetMapping("/getLogisticsCompanyName")
    public Result getLogisticsCompanyName(){
        return logisticsCompanyService.getLogisticsCompanyName();
    }

}
