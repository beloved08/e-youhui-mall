package com.eyh.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyh.mall.entity.LogisticsCompany;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.ExpressOrderPage;

/**
 * @Author 李平
 * @NAME LogisticsCompanyService
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期五
 * @Date 2023-04-07 18:20:52
 * @Description 物流公司业务接口层
 */
public interface LogisticsCompanyService extends IService<LogisticsCompany> {

    /**
     * 让物流公司
     *
     * @return {@link Result}
     */
    Result getLogisticsCompany();

    /**
     * forbiddenis物流公司
     *
     * @param logisticsCompanyId 物流公司id
     * @param status             状态
     * @return {@link Result}
     */
    Result forbiddenisLogisticsCompany(String logisticsCompanyId,Integer status);

    /**
     * 物流公司名称
     *
     * @return {@link Result}
     */
    Result getLogisticsCompanyName();

    /**
     * 选择物流公司名字
     *
     * @param logisticsCompanyName 物流公司名称
     * @return {@link LogisticsCompany}
     */
    LogisticsCompany selectLogisticsCompanyByName(String logisticsCompanyName);

}
