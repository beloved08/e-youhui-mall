package com.eyh.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.entity.CurrentActivity;
import com.eyh.mall.entity.PromotionActivity;
import com.eyh.mall.entity.PromotionPeople;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.dto.PromotionActivityDto;
import com.eyh.mall.entity.dto.PromotionActivityTdo;
import com.eyh.mall.entity.vo.PromotionActivityPage;
import com.eyh.mall.feign.client.commodity.CommodityServiceApiClient;
import com.eyh.mall.feign.client.user.UserApiClient;
import com.eyh.mall.feign.entity.Commodity;
import com.eyh.mall.feign.entity.MallUser;
import com.eyh.mall.mapper.PromotionActivityMapper;
import com.eyh.mall.mapper.PromotionPeopleMapper;
import com.eyh.mall.service.CurrentActivityService;
import com.eyh.mall.service.PromotionActivityCommodityService;
import com.eyh.mall.service.PromotionActivityService;
import com.eyh.mall.util.QRCodeUtil;
import com.eyh.mall.util.TimeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @Author 李平
 * @NAME PromotionActivityServiceImpl
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-04-17 09:22:25
 * @Description 促销活动
 */
@Service
public class PromotionActivityServiceImpl extends ServiceImpl<PromotionActivityMapper, PromotionActivity> implements PromotionActivityService {

    @Autowired
    private PromotionActivityMapper promotionActivityMapper;

    @Autowired
    private PromotionPeopleMapper promotionPeopleMapper;

    @Autowired
    private CommodityServiceApiClient commodityServiceApiClient;

    @Autowired
    private UserApiClient userApiClient;

    @Autowired
    private CurrentActivityService currentActivityService;

    @Autowired
    private PromotionActivityCommodityService promotionActivityCommodityService;

    /**
     * 创建促销活动
     *
     * @param promotionActivity 推广活动
     * @return {@link Result}
     */
    @Override
    public Result createPromotionActivity(PromotionActivity promotionActivity) {
        promotionActivity.setPromotionActivityId(UUID.randomUUID().toString());
        promotionActivity.setId(UUID.randomUUID().toString());
        promotionActivity.setCreateTime(TimeUtil.getCurrentTime());
        int insert = promotionActivityMapper.insert(promotionActivity);
        //设置该活动商品
        List<Commodity> list = commodityServiceApiClient.getTimeKillCommodity();
        boolean b = promotionActivityCommodityService.insertPromotionActivityCommodity(list, promotionActivity.getPromotionActivityId());

        currentActivityService.setCurrentActivity(promotionActivity.getPromotionActivityId());

        return insert > 0 && b ? Result.ok("活动创建成功", promotionActivity.getPromotionActivityId()) : Result.err("活动创建失败");
    }

    /**
     * 获得促销活动页面
     *
     * @param promotionActivityPage 促销活动页面
     * @return {@link Result}
     */
    @Override
    public Result getPromotionActivityPage(PromotionActivityPage promotionActivityPage) {
        Page<PromotionActivity> page = new Page<>(promotionActivityPage.getCurrentPage(), promotionActivityPage.getPageSize());

        String currentTime = TimeUtil.getCurrentTime();
        LambdaQueryWrapper<PromotionActivity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(
                !"".equals(promotionActivityPage.getPromotionActivityName()),
                PromotionActivity::getPromotionActivityName,
                promotionActivityPage.getPromotionActivityName());
        if (!"-1".equals(promotionActivityPage.getPromotionActivityStatus())) {

            if ("0".equals(promotionActivityPage.getPromotionActivityStatus())) {
                //未开始
                queryWrapper.ge(true, PromotionActivity::getStartTime, currentTime);
            } else if ("1".equals(promotionActivityPage.getPromotionActivityStatus())) {
                //进行中
                queryWrapper.le(true, PromotionActivity::getStartTime, currentTime);
                queryWrapper.ge(true, PromotionActivity::getEndTime, currentTime);
            } else if ("2".equals(promotionActivityPage.getPromotionActivityStatus())) {
                //已结束
                queryWrapper.le(true, PromotionActivity::getEndTime, currentTime);
            }
        }
        Page<PromotionActivity> activityPage = promotionActivityMapper.selectPage(page, queryWrapper);
        PromotionActivityTdo tdo = new PromotionActivityTdo();
        tdo.setCurrentPage(activityPage.getCurrent());
        tdo.setPageSize(activityPage.getPages());
        tdo.setTotal(activityPage.getTotal());
        tdo.setSize(activityPage.getSize());

        ArrayList<PromotionActivityDto> list = new ArrayList<>();
        CurrentActivity currentActivity = currentActivityService.getCurrentActivity();

        activityPage.getRecords().forEach(a -> {
            PromotionActivityDto dto = new PromotionActivityDto();
            BeanUtils.copyProperties(a, dto);

            //查询是否为当前活动
            if (currentActivity != null) {
                if (currentActivity.getPromotionActivityId().equals(a.getPromotionActivityId())){
                    dto.setIsCurrent(0);
                }else{
                    dto.setIsCurrent(1);
                }
            }

            // 状态
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
            long current = 0;
            long start = 0;
            long end = 0;
            try {
                current = sdf.parse(currentTime).getTime();
                start = sdf.parse(a.getStartTime()).getTime();
                end = sdf.parse(a.getEndTime()).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (current > start && current < end) {
                //进行中
                dto.setStatus(1);
            } else if (current < start) {
                //未开始
                dto.setStatus(0);
            } else if (current > end) {
                //已结束
                dto.setStatus(2);
            }
            list.add(dto);
        });
        tdo.setPromotionActivityDtoList(list);
        return Result.ok(tdo);
    }

    /**
     * 得到进步推广活动
     *
     * @return {@link PromotionActivity}
     */
    @Override
    public PromotionActivity getProgressPromotionActivity() {

        CurrentActivity currentActivity = currentActivityService.getCurrentActivity();
        LambdaQueryWrapper<PromotionActivity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!"".equals(currentActivity.getPromotionActivityId()),PromotionActivity::getPromotionActivityId,currentActivity.getPromotionActivityId());
        PromotionActivity promotionActivity1 = promotionActivityMapper.selectOne(wrapper);

        PromotionActivity promotionActivity = new PromotionActivity();
        promotionActivity.setPromotionActivityId(currentActivity.getPromotionActivityId());

        return promotionActivity1;

    }

    /**
     * 得到所有推广活动
     *
     * @return {@link Result}
     */
    @Override
    public Result getAllPromotionActivity() {
        LambdaQueryWrapper<PromotionActivity> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(true, PromotionActivity::getEndTime,TimeUtil.getCurrentTime());
        return Result.ok(promotionActivityMapper.selectList(wrapper));
    }

    /**
     * 得到推广活动qr分享
     *
     * @param userId              用户id
     * @param promotionActivityId 推广活动id
     * @return {@link Result}
     */
    @Override
    public Result getPromotionActivityShareQr(String userId, String promotionActivityId) {
        LambdaQueryWrapper<PromotionActivity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!"".equals(promotionActivityId), PromotionActivity::getPromotionActivityId, promotionActivityId);

        PromotionActivity promotionActivity = promotionActivityMapper.selectOne(wrapper);
        MallUser mallUser = userApiClient.getMallUserByUserId(userId);

        LambdaQueryWrapper<PromotionPeople> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(userId),PromotionPeople::getUserId,userId);
        PromotionPeople promotionPeople = promotionPeopleMapper.selectOne(queryWrapper);

        String content = "用户名：" + mallUser.getRealName() + "\n"
                + "用户ID：" + mallUser.getUserId() + "\n"
                + "联系电话：" + mallUser.getPhone() + "\n"
                + "促销号码：" + promotionPeople.getPromotionPeoplePhone() + "\n"
                + "促销ID：" + promotionPeople.getPromotionPeopleId() + "\n"
                + "活动名称：" + promotionActivity.getPromotionActivityName() + "\n"
                + "创建时间：" + promotionActivity.getCreateTime() + "\n"
                + "活动类型：" + promotionActivity.getPromotionActivityCategory() + "\n"
                + "活动ID：" + promotionActivity.getPromotionActivityId() + "\n"
                + "开始时间：" + promotionActivity.getStartTime() + "\n"
                + "结束时间：" + promotionActivity.getEndTime() + "\n"
                + "活动描述：" + promotionActivity.getActivityDescribe() + "\n";

        String logoUrl = "https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/logo/logo.png";
        return Result.ok(QRCodeUtil.getBase64QRCode(content, logoUrl));

    }


}
