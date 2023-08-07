package com.eyh.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.entity.PromotionPeople;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.dto.NationalPromotionGoEasyData;
import com.eyh.mall.entity.dto.PromotionPeopleGoEasyData;
import com.eyh.mall.entity.redis.SendCode;
import com.eyh.mall.entity.sms.SmsData;
import com.eyh.mall.entity.vo.PromotionPeoplePage;
import com.eyh.mall.goeasy.GoEasyUtil;
import com.eyh.mall.mapper.PromotionPeopleMapper;
import com.eyh.mall.rabbitmq.NationalPromotionConstant;
import com.eyh.mall.rabbitmq.ShopMallUserConstant;
import com.eyh.mall.redis.RedisUtil;
import com.eyh.mall.service.PromotionPeopleService;
import com.eyh.mall.util.TimeUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

/**
 * @Author 李平
 * @NAME PromotionPeopleServiceImpl
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期四
 * @Date 2023-04-13 13:35:37
 * @Description 全民促销人员业务接口实现类
 */
@Service
public class PromotionPeopleServiceImpl extends ServiceImpl<PromotionPeopleMapper, PromotionPeople> implements PromotionPeopleService {

    @Autowired
    private PromotionPeopleMapper promotionPeopleMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 发送国家促进人代码
     *
     * @param phone 电话
     * @return {@link Result}
     */
    @Override
    public Result sendNationalPromotionPeopleCode(String phone) {

        String key = SendCode.NATIONAL_PROMOTION_PEOPLE_CODE + phone;
        Integer code = (Integer) redisUtil.get(key);
        if (code != null) {
            return Result.err("验证码未过期", null);
        }

        // 发送消息
        rabbitTemplate.convertAndSend(ShopMallUserConstant.MALL_USER_REGISTER_SEND_CODE_QUEUE, JSON.toJSONString(
                new SmsData(phone, "SMS_276350264", key, SendCode.NATIONAL_PROMOTION_PEOPLE_CODE_TTL)
        ));

        return Result.ok("验证码发送成功", null);
    }

    /**
     * 验证国家推广
     *
     * @param phone  电话
     * @param code   代码
     * @param userId 用户id
     * @return {@link Result}
     */
    @Override
    public Result verifyNationalPromotion(String phone, String code, String userId) {
        String key = SendCode.NATIONAL_PROMOTION_PEOPLE_CODE + phone;
        Integer c = (Integer) redisUtil.get(key);
        if (c == null) {
            return Result.err("验证码已过期", null);
        }
        if (!Objects.equals(code, String.valueOf(c))) {
            return Result.err("验证码错误", null);
        }

        promotionPeopleMapper.insert(new PromotionPeople(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                phone,
                TimeUtil.getCurrentTime(),
                0,
                code,
                userId
        ));
        //推送消息到管理系统
        GoEasyUtil.messagePush("national_promotion_channel", new NationalPromotionGoEasyData(phone, TimeUtil.getCurrentTime()));
        return Result.ok("申请提交成功，等待审核", null);
    }

    /**
     * 得到晋升用户id
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @Override
    public Result getPromotionByUserId(String userId) {
        LambdaQueryWrapper<PromotionPeople> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(!"".equals(userId), PromotionPeople::getUserId, userId);
        return Result.ok(promotionPeopleMapper.selectOne(queryWrapper));
    }

    /**
     * 促进人页
     *
     * @param promotionPeoplePage 促进人页面
     * @return {@link Result}
     */
    @Override
    public Result getPromotionPeoplePage(PromotionPeoplePage promotionPeoplePage) {
        Page<PromotionPeople> page = new Page<>(promotionPeoplePage.getCurrentPage(), promotionPeoplePage.getPageSize());

        LambdaQueryWrapper<PromotionPeople> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(promotionPeoplePage.getPromotionPeoplePhone()), PromotionPeople::getPromotionPeoplePhone, promotionPeoplePage.getPromotionPeoplePhone());

        if (promotionPeoplePage.getVerify() != -1) {
            queryWrapper.eq(promotionPeoplePage.getVerify() != null, PromotionPeople::getVerify, promotionPeoplePage.getVerify());
        }

        return Result.ok(promotionPeopleMapper.selectPage(page, queryWrapper));
    }

    /**
     * 通过推广人
     *
     * @param promotionPeopleId 促进人们id
     * @param status            状态
     * @return {@link Result}
     */
    @Override
    public Result approvedPromotionPeople(String promotionPeopleId, Integer status) {
        //修改该条记录的状态为(0：待审核，1：审核通过，2：审核不通过)
        LambdaUpdateWrapper<PromotionPeople> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(!"".equals(promotionPeopleId), PromotionPeople::getPromotionPeopleId, promotionPeopleId)
                .set(status != null, PromotionPeople::getVerify, status);
        int update = promotionPeopleMapper.update(null, updateWrapper);

        //发送审核通过短信通知
        String template = status != null && status == 1 ? "SMS_276460251" : "SMS_276455514";
        LambdaQueryWrapper<PromotionPeople> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(promotionPeopleId), PromotionPeople::getPromotionPeopleId, promotionPeopleId);
        PromotionPeople promotionPeople = promotionPeopleMapper.selectOne(queryWrapper);
        rabbitTemplate.convertAndSend(NationalPromotionConstant.PROMOTION_PEOPLE_PASS_QUEUE, JSON.toJSONString(
                new SmsData(promotionPeople.getPromotionPeoplePhone(), template, "", null)
        ));

        //推送消息到小程序提醒
        GoEasyUtil.messagePush("promotion_people_channel",
                new PromotionPeopleGoEasyData(
                        promotionPeople.getPromotionPeoplePhone(),
                        TimeUtil.getCurrentTime(),
                        status));

        return update > 0 ? Result.ok("审核通过操作成功", null) : Result.err("审核通过操作不成功");
    }
}
