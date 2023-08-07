package com.eyh.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyh.mall.entity.PromotionActivityCommodity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author 李平
 * @NAME PromotionActivityCommodityMapper
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-04-17 10:17:48
 * @Description 促销活动商品
 */
@Component
@Mapper
public interface PromotionActivityCommodityMapper extends BaseMapper<PromotionActivityCommodity> {

    /**
     * 通过id获取商品促销活动
     *
     * @param id id
     * @return {@link List}<{@link PromotionActivityCommodity}>
     */
    @Select("SELECT * FROM eyh_promotion_activity_commodity WHERE promotion_activity_id = #{id} ORDER BY rand() LIMIT 3")
    List<PromotionActivityCommodity> getPromotionActivityCommodityById(@Param("id") String id);

}
