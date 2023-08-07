package com.eyh.mall.controller;

import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.dto.NationalPromotionGoEasyData;
import com.eyh.mall.entity.dto.PromotionPeopleGoEasyData;
import com.eyh.mall.entity.vo.PromotionPeoplePage;
import com.eyh.mall.goeasy.GoEasyUtil;
import com.eyh.mall.service.PromotionPeopleService;
import com.eyh.mall.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 李平
 * @NAME NationalPromotionController
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期四
 * @Date 2023-04-13 13:21:08
 * @Description 全民促销
 */
@RestController
@RequestMapping("/marketing")
public class NationalPromotionController {

    @Autowired
    private PromotionPeopleService promotionPeopleService;

    /**
     * 发送国家促进人代码
     *
     * @param phone 电话
     * @return {@link Result}
     */
    @GetMapping("/sendNationalPromotionPeopleCode/{phone}")
    public Result sendNationalPromotionPeopleCode(@PathVariable String phone) {
        return promotionPeopleService.sendNationalPromotionPeopleCode(phone);
    }

    /**
     * 验证国家推广
     *
     * @param phone 电话
     * @param code  代码
     * @return {@link Result}
     */
    @GetMapping("/verifyNationalPromotion/{phone}/{code}/{userId}")
    public Result verifyNationalPromotion(@PathVariable String phone, @PathVariable String code, @PathVariable String userId) {
        return promotionPeopleService.verifyNationalPromotion(phone, code, userId);
    }

    /**
     * 得到晋升用户id
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @GetMapping("/getPromotionUserId/{userId}")
    public Result getPromotionUserId(@PathVariable String userId) {
        return promotionPeopleService.getPromotionByUserId(userId);
    }

    /**
     * 促进人页
     *
     * @param promotionPeoplePage 促进人页面
     * @return {@link Result}
     */
    @PostMapping("/getPromotionPeoplePage")
    public Result getPromotionPeoplePage(@RequestBody PromotionPeoplePage promotionPeoplePage) {
        return promotionPeopleService.getPromotionPeoplePage(promotionPeoplePage);
    }

    /**
     * 通过推广人
     *
     * @param promotionPeopleId 促进人们id
     * @return {@link Result}
     */
    @GetMapping("/approvedPromotionPeople/{promotionPeopleId}/{status}")
    public Result approvedPromotionPeople(@PathVariable String promotionPeopleId, @PathVariable Integer status) {
        return promotionPeopleService.approvedPromotionPeople(promotionPeopleId, status);
    }

    @GetMapping("/goeasy")
    public void goeasy() {
        GoEasyUtil.messagePush("promotion_people_channel",
                new PromotionPeopleGoEasyData(
                        "15126484272",
                        TimeUtil.getCurrentTime(),
                        2));
    }
}
