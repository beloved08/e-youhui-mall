package com.eyh.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.entity.PromotionActivity;
import com.eyh.mall.entity.PromotionActivityCommodity;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.dto.PromotionActivityCommodityDto;
import com.eyh.mall.entity.vo.PromotionActivityCommodityPage;
import com.eyh.mall.feign.client.commodity.CommodityServiceApiClient;
import com.eyh.mall.feign.entity.ActivityCommodity;
import com.eyh.mall.feign.entity.Commodity;
import com.eyh.mall.mapper.PromotionActivityCommodityMapper;
import com.eyh.mall.mapper.PromotionActivityMapper;
import com.eyh.mall.service.PromotionActivityCommodityService;
import com.eyh.mall.service.PromotionActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author 李平
 * @NAME PromotionActivityCommodityServiceImpl
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-04-17 10:19:11
 * @Description 促销活动商品
 */
@Service
public class PromotionActivityCommodityServiceImpl extends ServiceImpl<PromotionActivityCommodityMapper, PromotionActivityCommodity>
        implements PromotionActivityCommodityService {

    @Autowired
    private PromotionActivityCommodityMapper promotionActivityCommodityMapper;

    @Autowired
    private PromotionActivityService promotionActivityService;

    @Autowired
    private CommodityServiceApiClient commodityServiceApiClient;

    /**
     * 插入促销活动商品
     *
     * @param commodityList       商品列表
     * @param promotionActivityId 推广活动id
     * @return boolean
     */
    @Override
    public boolean insertPromotionActivityCommodity(List<Commodity> commodityList, String promotionActivityId) {
        List<PromotionActivityCommodity> list = new ArrayList<>();
        commodityList.forEach(c -> {
            PromotionActivityCommodity promotionActivityCommodity = new PromotionActivityCommodity();
            promotionActivityCommodity.setId(UUID.randomUUID().toString());
            promotionActivityCommodity.setCommodityId(c.getCommodityId());
            promotionActivityCommodity.setPromotionActivityId(promotionActivityId);
            list.add(promotionActivityCommodity);
        });
        return this.saveBatch(list);
    }

    /**
     * 促销活动商品页面
     *
     * @param promotionActivityCommodityPage 促销活动商品页面
     * @return {@link Result}
     */
    @Override
    public Result getPromotionActivityCommodityPage(PromotionActivityCommodityPage promotionActivityCommodityPage) {
        Page<PromotionActivityCommodity> page = new Page<PromotionActivityCommodity>(promotionActivityCommodityPage.getCurrentPage(), promotionActivityCommodityPage.getPageSize());
        LambdaQueryWrapper<PromotionActivityCommodity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!"".equals(promotionActivityCommodityPage.getPromotionActivityId()),
                PromotionActivityCommodity::getPromotionActivityId,
                promotionActivityCommodityPage.getPromotionActivityId());
        Page<PromotionActivityCommodity> selectPage = promotionActivityCommodityMapper.selectPage(page, wrapper);
        List<PromotionActivityCommodity> list = selectPage.getRecords();

        List<String> stringArrayList = new ArrayList<>();
        ActivityCommodity activityCommodity = new ActivityCommodity();
        list.forEach(l -> {
            stringArrayList.add(l.getCommodityId());
        });
        activityCommodity.setCommodityId(stringArrayList);
        PromotionActivityCommodityDto dto = new PromotionActivityCommodityDto();
        dto.setCurrentPage(selectPage.getCurrent());
        dto.setPageSize(selectPage.getPages());
        dto.setSize(selectPage.getSize());
        dto.setTotal(selectPage.getTotal());
        dto.setCommodityList(commodityServiceApiClient.getPromotionActivityCommodity(activityCommodity));
        return Result.ok(dto);
    }

    /**
     * 得到有限时间闪杀商品
     *
     * @return {@link Result}
     */
    @Override
    public Result getLimitedTimeFlashKillCommodity() {
        PromotionActivity promotionActivity = promotionActivityService.getProgressPromotionActivity();
        List<PromotionActivityCommodity> id = promotionActivityCommodityMapper.getPromotionActivityCommodityById(promotionActivity.getPromotionActivityId());
        List<String> stringArrayList = new ArrayList<>();
        ActivityCommodity activityCommodity = new ActivityCommodity();
        id.forEach(l -> {
            stringArrayList.add(l.getCommodityId());
        });
        activityCommodity.setCommodityId(stringArrayList);

        return Result.ok(commodityServiceApiClient.getPromotionActivityCommodityLimitThree(activityCommodity));
    }

    /**
     * 时间杀了商品页面
     *
     * @param currentPage 当前页面
     * @param pageSize    页面大小
     * @return {@link Result}
     */
    @Override
    public Result getTimeKillCommodityPage(Integer currentPage, Integer pageSize) {

        PromotionActivity promotionActivity = promotionActivityService.getProgressPromotionActivity();

        Page<PromotionActivityCommodity> page = new Page<PromotionActivityCommodity>(currentPage, pageSize);
        LambdaQueryWrapper<PromotionActivityCommodity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!"".equals(promotionActivity.getPromotionActivityId()),
                PromotionActivityCommodity::getPromotionActivityId,
                promotionActivity.getPromotionActivityId());
        List<PromotionActivityCommodity> records = promotionActivityCommodityMapper.selectPage(page, wrapper).getRecords();

        List<String> stringArrayList = new ArrayList<>();
        ActivityCommodity activityCommodity = new ActivityCommodity();
        records.forEach(l -> {
            stringArrayList.add(l.getCommodityId());
        });
        activityCommodity.setCommodityId(stringArrayList);

        return Result.ok(commodityServiceApiClient.getPromotionActivityCommodity(activityCommodity));
    }

    /**
     * 获得促销活动商品
     *
     * @param promotionActivityCommodityId 促销活动商品id
     * @return {@link Result}
     */
    @Override
    public Result getPromotionActivityCommodity(String promotionActivityCommodityId) {
        LambdaQueryWrapper<PromotionActivityCommodity> wrapper = new LambdaQueryWrapper<>();
        PromotionActivityCommodity commodity = promotionActivityCommodityMapper.selectOne(wrapper);
        List<String> stringArrayList = new ArrayList<>();
        ActivityCommodity activityCommodity = new ActivityCommodity();
        stringArrayList.add(commodity.getCommodityId());
        activityCommodity.setCommodityId(stringArrayList);
        return Result.ok(commodityServiceApiClient.getPromotionActivityCommodity(activityCommodity));

    }
}
