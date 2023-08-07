package com.eyh.mall.controller;

import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.PromotionActivityCommodityPage;
import com.eyh.mall.service.PromotionActivityCommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author 李平
 * @NAME PromotionActivityCommodityController
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-04-17 10:20:36
 * @Description 促销活动商品
 */
@RestController
@RequestMapping("/marketing")
public class PromotionActivityCommodityController {

    @Autowired
    private PromotionActivityCommodityService promotionActivityCommodityService;

    /**
     * 促销活动商品页面
     *
     * @param promotionActivityCommodityPage 促销活动商品页面
     * @return {@link Result}
     */
    @PostMapping("/getPromotionActivityCommodityPage")
    public Result getPromotionActivityCommodityPage(@RequestBody PromotionActivityCommodityPage promotionActivityCommodityPage){
        return promotionActivityCommodityService.getPromotionActivityCommodityPage(promotionActivityCommodityPage);
    }

    /**
     * 得到有限时间闪杀商品
     *
     * @return {@link Result}
     */
    @GetMapping("/getLimitedTimeFlashKillCommodity")
    public Result getLimitedTimeFlashKillCommodity(){
        return promotionActivityCommodityService.getLimitedTimeFlashKillCommodity();
    }

    /**
     * 时间杀了商品页面
     *
     * @param currentPage 当前页面
     * @param pageSize    页面大小
     * @return {@link Result}
     */
    @GetMapping("/getTimeKillCommodityPage/{currentPage}/{pageSize}")
    public Result getTimeKillCommodityPage(@PathVariable Integer currentPage, @PathVariable Integer pageSize){
        return promotionActivityCommodityService.getTimeKillCommodityPage(currentPage,pageSize);
    }

    /**
     * 通过id获取商品促销活动
     *
     * @param promotionActivityCommodityId 促销活动商品id
     * @return {@link Result}
     */
    @GetMapping("/getPromotionActivityCommodityById/{promotionActivityCommodityId}")
    public Result getPromotionActivityCommodityById(@PathVariable String promotionActivityCommodityId){
        return promotionActivityCommodityService.getPromotionActivityCommodity(promotionActivityCommodityId);
    }

}
