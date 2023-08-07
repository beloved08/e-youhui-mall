package com.eyh.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyh.mall.entity.LogisticsCompany;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @Author 李平
 * @NAME LogisticsCompanyMapper
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期五
 * @Date 2023-04-07 18:20:00
 * @Description 物流公司数据层
 */
@Component
@Mapper
public interface LogisticsCompanyMapper extends BaseMapper<LogisticsCompany> {
}
