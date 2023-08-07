package com.eyh.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyh.mall.entity.Commodity;
import com.eyh.mall.entity.tdo.DataVDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author 李平
 * @Date 2023-2-18
 */
@Mapper
@Component
public interface CommodityMapper extends BaseMapper<Commodity> {

    /**
     * 得到畅销商品
     *
     * @return {@link List}<{@link Commodity}>
     */
    @Select("SELECT * FROM eyh_commodity WHERE commodity_status = 0 AND is_delete = 2 ORDER BY rand() LIMIT 2")
    List<Commodity> getBestSellersCommodity();


    /**
     * 要旋转图商品
     *
     * @return {@link List}<{@link Commodity}>
     */
    @Select("SELECT * FROM eyh_commodity WHERE commodity_status = 0 and commodity_recommend = 1 AND is_delete = 2 ORDER BY rand() LIMIT 3")
    List<Commodity> getRotationChartCommodity();

    /**
     * 获得商品推荐
     *
     * @return {@link List}<{@link Commodity}>
     */
    @Select("SELECT * FROM eyh_commodity WHERE commodity_status = 0 and commodity_recommend = 0 AND is_delete = 2 ORDER BY rand() LIMIT 2")
    List<Commodity> getCommodityRecommend();


    /**
     * 让时间杀了商品
     *
     * @return {@link List}<{@link Commodity}>
     */
    @Select("SELECT * FROM eyh_commodity WHERE commodity_status = 0 AND is_delete = 2 ORDER BY rand() LIMIT 50")
    List<Commodity> getTimeKillCommodity();

    /**
     * 获得商品数据vlist
     *
     * @return {@link List}<{@link Commodity}>
     */
    @Select("SELECT * FROM eyh_commodity WHERE commodity_status = 0 AND is_delete = 2 ORDER BY rand() LIMIT 60")
    List<Commodity> getCommodityDataVList();

    /**
     * 获得促销活动商品限制三个
     *
     * @param id id
     * @return {@link List}<{@link Commodity}>
     */
    @Select("SELECT * FROM eyh_commodity WHERE commodity_id = #{id} AND is_delete = 2 ORDER BY rand() LIMIT 50")
    Commodity getPromotionActivityCommodityLimitThree(@Param("id") String id);

    /**
     * 得到分类商品比例
     *
     * @return {@link Object}
     */
    @Select("SELECT cla.classification_name as name,COUNT(*) as value " +
            "FROM eyh_commodity com,eyh_classification cla " +
            "WHERE com.one_classification_id = cla.classification_id " +
            "GROUP BY one_classification_id")
    List<DataVDto> getClassificationCommodityProportion();

}
