package com.eyh.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.entity.CurrentActivity;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.mapper.CurrentActivityMapper;
import com.eyh.mall.service.CurrentActivityService;
import com.eyh.mall.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Author 李平
 * @NAME CurrentActivityServiceImpl
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期二
 * @Date 2023-04-18 20:22:19
 * @Description 当前活动
 */
@Service
public class CurrentActivityServiceImpl extends ServiceImpl<CurrentActivityMapper, CurrentActivity> implements CurrentActivityService {

    @Autowired
    private CurrentActivityMapper currentActivityMapper;

    /**
     * 设置当前活动
     *
     * @param promotionActivityId 推广活动id
     */
    @Override
    public Result setCurrentActivity(String promotionActivityId) {
        int insert = currentActivityMapper.insert(new CurrentActivity(
                UUID.randomUUID().toString(),
                promotionActivityId,
                TimeUtil.getCurrentTime(),
                UUID.randomUUID().toString()
        ));
        return insert > 0 ? Result.ok("设置成功",null) : Result.err("设置失败");
    }

    /**
     * 得到当前活动
     *
     * @return {@link CurrentActivity}
     */
    @Override
    public CurrentActivity getCurrentActivity() {
        LambdaQueryWrapper<CurrentActivity> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(true,CurrentActivity::getInsertTime)
                .last(true, "limit 1");
        return currentActivityMapper.selectOne(wrapper);
    }

}
