package com.eyh.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyh.mall.entity.PromotionPeople;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.PromotionPeoplePage;

/**
 * @Author 李平
 * @NAME PromotionPeopleService
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期四
 * @Date 2023-04-13 13:34:41
 * @Description 全民促销服务层
 */
public interface PromotionPeopleService extends IService<PromotionPeople> {

    /**
     * 发送国家促进人代码
     *
     * @param phone 电话
     * @return {@link Result}
     */
    Result sendNationalPromotionPeopleCode(String phone);

    /**
     * 验证国家推广
     *
     * @param phone  电话
     * @param code   代码
     * @param userId 用户id
     * @return {@link Result}
     */
    Result verifyNationalPromotion(String phone, String code, String userId);

    /**
     * 得到晋升用户id
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    Result getPromotionByUserId(String userId);

    /**
     * 促进人页
     *
     * @param promotionPeoplePage 促进人页面
     * @return {@link Result}
     */
    Result getPromotionPeoplePage(PromotionPeoplePage promotionPeoplePage);

    /**
     * 通过推广人
     *
     * @param promotionPeopleId 促进人们id
     * @param status            状态
     * @return {@link Result}
     */
    Result approvedPromotionPeople(String promotionPeopleId, Integer status);
}
