package com.eyh.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.entity.UserCoupon;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.marketing.CouponMQData;
import com.eyh.mall.entity.tdo.UserCouponDto;
import com.eyh.mall.entity.vo.UserCouponVo;
import com.eyh.mall.feign.client.marketing.CouponApiClient;
import com.eyh.mall.feign.entity.MerchantCoupon;
import com.eyh.mall.feign.entity.UniversalCoupon;
import com.eyh.mall.mapper.UserCouponMapper;
import com.eyh.mall.rabbitmq.CouponConstant;
import com.eyh.mall.service.UserCouponService;
import com.eyh.mall.util.TimeUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author 李平
 * @NAME UserCouponServiceImpl
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-03-29 20:17:37
 * @Description 用户优惠券业务接口实现类
 */
@Service
public class UserCouponServiceImpl extends ServiceImpl<UserCouponMapper, UserCoupon> implements UserCouponService {

    @Autowired
    private UserCouponMapper userCouponMapper;

    @Autowired
    private CouponApiClient couponApiClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 画优惠券
     *
     * @param userCouponVo 用户优惠券签证官
     * @return {@link Result}
     */
    @Override
    public Result drawCoupon(UserCouponVo userCouponVo) {
        LambdaQueryWrapper<UserCoupon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(userCouponVo.getCouponId()),UserCoupon::getCouponId,userCouponVo.getCouponId());
        queryWrapper.eq(!"".equals(userCouponVo.getUserId()),UserCoupon::getUserId,userCouponVo.getUserId());

        if (userCouponMapper.selectOne(queryWrapper) != null){
            return Result.err("您已领取该优惠券，不能重复领取");
        }
        UserCoupon userCoupon = new UserCoupon();
        boolean b;
        //判断优惠券余额
        if (userCouponVo.getType() == 0){
            //通用优惠券
            UniversalCoupon universalCoupon = couponApiClient.selectUniversalCouponById(userCouponVo.getCouponId());
            b = universalCoupon.getTotalQuantity() > 0;
            userCoupon.setIsType(0);
        }else{
            //商家优惠券
            MerchantCoupon merchantCoupon = couponApiClient.selectMerchantCouponById(userCouponVo.getCouponId());
            b = merchantCoupon.getTotalQuantity() > 0;
            userCoupon.setIsType(1);
        }
        if (!b){
            return Result.err("该优惠券已被抢光啦");
        }

        // TODO 修改优惠券总量 => -1
        rabbitTemplate.convertAndSend(CouponConstant.UPDATE_COUPON_NUMBER_QUEUE, JSON.toJSONString(
                new CouponMQData(userCouponVo.getCouponId(),userCouponVo.getType())
        ));

        userCoupon.setCouponId(userCouponVo.getCouponId());
        userCoupon.setUserId(userCouponVo.getUserId());
        userCoupon.setId(UUID.randomUUID().toString());
        userCoupon.setUserCouponId(UUID.randomUUID().toString());
        userCoupon.setDrawTime(TimeUtil.getCurrentTime());
        userCoupon.setIsUse(0);
        return userCouponMapper.insert(userCoupon) > 0 ? Result.ok("领取成功") : Result.err("领取失败");
    }

    /**
     * 获取用户优惠券计数
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @Override
    public Result getUserCouponCount(String userId) {

        LambdaQueryWrapper<UserCoupon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(userId),UserCoupon::getUserId,userId);

        return Result.ok(userCouponMapper.selectCount(queryWrapper));
    }

    /**
     * 得到用户优惠券列表
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @Override
    public Result getUserCouponList(String userId) {
        LambdaQueryWrapper<UserCoupon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(userId),UserCoupon::getUserId,userId);

        List<UserCoupon> userCouponList = userCouponMapper.selectList(queryWrapper);

        List<UserCouponDto> list = new ArrayList<>();
        userCouponList.forEach(c -> {
            UserCouponDto dto = new UserCouponDto();
            BeanUtils.copyProperties(c,dto);

            //优惠券的时间范围
            if (c.getIsType() == 0){
                UniversalCoupon universalCoupon = couponApiClient.selectUniversalCouponById(c.getCouponId());
                dto.setStartTime(universalCoupon.getStartTime());
                dto.setEndTime(universalCoupon.getEndTime());
                dto.setCouponName(universalCoupon.getUniversalCouponName());
                dto.setDiscountAmount(universalCoupon.getDiscountAmount());
                dto.setFullAvailable(universalCoupon.getFullAvailable());
            }else{
                MerchantCoupon merchantCoupon = couponApiClient.selectMerchantCouponById(c.getCouponId());
                dto.setStartTime(merchantCoupon.getStartTime());
                dto.setEndTime(merchantCoupon.getEndTime());
                dto.setCouponName(merchantCoupon.getMerchantCouponName());
                dto.setDiscountAmount(merchantCoupon.getDiscountAmount());
                dto.setFullAvailable(merchantCoupon.getFullAvailable());
                dto.setBusinessId(merchantCoupon.getBusinessId());
            }
            String currentTime = TimeUtil.getCurrentTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
            long current = 0;
            long start = 0;
            long end = 0;
            try {
                current = sdf.parse(currentTime).getTime();
                start = sdf.parse(dto.getStartTime()).getTime();
                end = sdf.parse(dto.getEndTime()).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (current > start && current < end) {
                dto.setIsExpire(0);
            }else if(current < start){
                dto.setIsExpire(1);
            }else if(current > end){
                dto.setIsExpire(2);
            }

            list.add(dto);
        });
        return Result.ok(list);
    }

    /**
     * 通过id获取用户优惠券
     *
     * @param couponId 优惠券id
     * @return {@link UserCoupon}
     */
    @Override
    public UserCoupon getUserCouponById(String couponId) {
        LambdaQueryWrapper<UserCoupon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(couponId),UserCoupon::getCouponId,couponId);
        return userCouponMapper.selectOne(queryWrapper);
    }

    /**
     * 更新用户使用优惠券
     *
     * @param couponId 优惠券id
     */
    @Override
    public void updateUserCouponIsUse(String couponId) {
        LambdaUpdateWrapper<UserCoupon> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(!"".equals(couponId),UserCoupon::getCouponId,couponId)
                .set(!"".equals(couponId),UserCoupon::getIsUse,1);
        userCouponMapper.update(null,updateWrapper);
    }

}
