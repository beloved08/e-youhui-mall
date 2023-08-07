package com.eyh.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyh.mall.entity.PromotionActivityCommodity;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.PromotionActivityCommodityPage;
import com.eyh.mall.feign.entity.Commodity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author 李平
 * @NAME PromotionActivityCommodityService
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-04-17 10:18:27
 * @Description 促销活动商品
 */
public interface PromotionActivityCommodityService extends IService<PromotionActivityCommodity> {

    /**
     * 插入促销活动商品
     *
     * @param commodityList       商品列表
     * @param promotionActivityId 推广活动id
     * @return boolean
     */
    boolean insertPromotionActivityCommodity(List<Commodity> commodityList,String promotionActivityId);

    /**
     * 促销活动商品页面
     *
     * @param promotionActivityCommodityPage 促销活动商品页面
     * @return {@link Result}
     */
    Result getPromotionActivityCommodityPage(PromotionActivityCommodityPage promotionActivityCommodityPage);

    /**
     * 得到有限时间闪杀商品
     *
     * @return {@link Result}
     */
    Result getLimitedTimeFlashKillCommodity();

    /**
     * 时间杀了商品页面
     *
     * @param currentPage 当前页面
     * @param pageSize    页面大小
     * @return {@link Result}
     */
    Result getTimeKillCommodityPage(Integer currentPage, Integer pageSize);

    /**
     * 获得促销活动商品
     *
     * @param promotionActivityCommodityId 促销活动商品id
     * @return {@link Result}
     */
    Result getPromotionActivityCommodity(String promotionActivityCommodityId);
}
