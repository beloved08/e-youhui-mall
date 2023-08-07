package com.eyh.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyh.mall.entity.Order;
import com.eyh.mall.entity.dto.RegionalSalesRankingData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author 李平
 * @NAME OrderMapper
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-04-05 09:22:57
 * @Description 订单数据层
 */
@Component
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 得到区域销售排名
     *
     * @return {@link List}<{@link RegionalSalesRankingData}>
     */
    @Select("SELECT o.address_id as name, COUNT(*) as value FROM eyh_order o GROUP BY address_id")
    List<RegionalSalesRankingData> getRegionalSalesRanking();

}
