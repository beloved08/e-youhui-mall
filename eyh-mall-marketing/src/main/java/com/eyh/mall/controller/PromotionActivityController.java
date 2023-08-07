package com.eyh.mall.controller;

import com.eyh.mall.entity.PromotionActivity;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.PromotionActivityPage;
import com.eyh.mall.service.PromotionActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author 李平
 * @NAME PromotionActivityController
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-04-17 09:23:37
 * @Description 促销活动
 */
@RestController
@RequestMapping("/marketing")
public class PromotionActivityController {

    @Autowired
    private PromotionActivityService promotionActivityService;

    /**
     * 创建促销活动
     *
     * @param promotionActivity 推广活动
     * @return {@link Result}
     */
    @PostMapping("/createPromotionActivity")
    public Result createPromotionActivity(@RequestBody PromotionActivity promotionActivity) {
        return promotionActivityService.createPromotionActivity(promotionActivity);
    }

    /**
     * 获得促销活动页面
     *
     * @param promotionActivityPage 促销活动页面
     * @return {@link Result}
     */
    @PostMapping("/getPromotionActivityPage")
    public Result getPromotionActivityPage(@RequestBody PromotionActivityPage promotionActivityPage) {
        return promotionActivityService.getPromotionActivityPage(promotionActivityPage);
    }

    /**
     * 得到进步推广活动
     *
     * @return {@link Result}
     */
    @GetMapping("/getProgressPromotionActivity")
    public Result getProgressPromotionActivity() {
        return Result.ok(promotionActivityService.getProgressPromotionActivity());
    }

    /**
     * 得到所有推广活动
     *
     * @return {@link Result}
     */
    @GetMapping("/getAllPromotionActivity")
    public Result getAllPromotionActivity() {
        return promotionActivityService.getAllPromotionActivity();
    }

    /**
     * 得到推广活动qr分享
     *
     * @param userId              用户id
     * @param promotionActivityId 推广活动id
     * @return {@link Result}
     */
    @GetMapping("/getPromotionActivityShareQr/{userId}/{promotionActivityId}")
    public Result getPromotionActivityShareQr(@PathVariable String userId, @PathVariable String promotionActivityId) {
        return promotionActivityService.getPromotionActivityShareQr(userId, promotionActivityId);
    }

}
