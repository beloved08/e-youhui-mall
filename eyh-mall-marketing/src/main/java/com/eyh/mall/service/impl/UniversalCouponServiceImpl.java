package com.eyh.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.entity.MerchantCoupon;
import com.eyh.mall.entity.UniversalCoupon;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.dto.UniversalCouponDto;
import com.eyh.mall.entity.dto.UniversalCouponTdo;
import com.eyh.mall.entity.dto.UniversalCouponWxDto;
import com.eyh.mall.entity.vo.UniversalCouponVoPage;
import com.eyh.mall.feign.client.commodity.CommodityServiceApiClient;
import com.eyh.mall.feign.entity.Commodity;
import com.eyh.mall.mapper.UniversalCouponMapper;
import com.eyh.mall.service.UniversalCouponService;
import com.eyh.mall.util.TimeUtil;
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
 * @NAME UniversalCouponServiceImpl
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-03-29 10:38:17
 * @Description 通用优惠券业务接口实现类
 */
@Service
public class UniversalCouponServiceImpl extends ServiceImpl<UniversalCouponMapper, UniversalCoupon> implements UniversalCouponService {

    @Autowired
    private UniversalCouponMapper universalCouponMapper;

    @Autowired
    private CommodityServiceApiClient commodityServiceApiClient;

    /**
     * 添加通用券
     *
     * @param universalCoupon 普遍优惠券
     * @return {@link Result}
     */
    @Override
    public Result addUniversalCoupon(UniversalCoupon universalCoupon) {
        universalCoupon.setId(UUID.randomUUID().toString());
        universalCoupon.setUniversalCouponId(UUID.randomUUID().toString());
        return universalCouponMapper.insert(universalCoupon) > 0 ? Result.ok("添加成功") : Result.err("添加失败");
    }

    /**
     * 得到所有通用优惠券页面
     *
     * @param universalCouponVoPage 通用券vo页面
     * @return {@link Result}
     */
    @Override
    public Result getAllUniversalCouponPage(UniversalCouponVoPage universalCouponVoPage) {
        IPage<UniversalCoupon> page = new Page<UniversalCoupon>(universalCouponVoPage.getCurrentPage(), universalCouponVoPage.getPageSize());

        //条件搜索
        LambdaQueryWrapper<UniversalCoupon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!"".equals(universalCouponVoPage.getUniversalCouponName()),
                UniversalCoupon::getUniversalCouponName,
                universalCouponVoPage.getUniversalCouponName());

        /**
         * 状态
         * 0：使用中，1：未开始，2：已结束
         * ge:大于等于
         * le:小于等于
         */
        String currentTime = TimeUtil.getCurrentTime();
        if (universalCouponVoPage.getStatus() == 0){
            // 使用中，当前时间>开始时间，当前时间<结束时间
            queryWrapper.le(true,UniversalCoupon::getStartTime,currentTime);
            queryWrapper.ge(true,UniversalCoupon::getEndTime,currentTime);
        }
        if (universalCouponVoPage.getStatus() == 1){
            //未开始，当前时间<开始时间
            queryWrapper.ge(true,UniversalCoupon::getStartTime,currentTime);
        }
        if (universalCouponVoPage.getStatus() == 2){
            //未开始，当前时间>结束时间
            queryWrapper.le(true,UniversalCoupon::getEndTime,currentTime);
        }
        IPage<UniversalCoupon> selectPage = universalCouponMapper.selectPage(page, queryWrapper);
        UniversalCouponTdo tdo = new UniversalCouponTdo();
        tdo.setCurrentPage(selectPage.getCurrent());
        tdo.setPageSize(selectPage.getPages());
        tdo.setTotal(selectPage.getTotal());
        tdo.setSize(selectPage.getSize());

        List<UniversalCouponDto> universalCouponDtos = new ArrayList<>();
        selectPage.getRecords().forEach(c -> {
            UniversalCouponDto dto = new UniversalCouponDto();
            dto.setUniversalCouponId(c.getUniversalCouponId());
            dto.setUniversalCouponName(c.getUniversalCouponName());
            dto.setDiscountAmount(c.getDiscountAmount());
            dto.setFullAvailable(c.getFullAvailable());
            dto.setStartTime(c.getStartTime());
            dto.setEndTime(c.getEndTime());
            dto.setTotalQuantity(c.getTotalQuantity());

            // 状态
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
            long current = 0;
            long start = 0;
            long end = 0;
            try {
                 current = sdf.parse(currentTime).getTime();
                 start = sdf.parse(c.getStartTime()).getTime();
                 end = sdf.parse(c.getEndTime()).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (current > start && current < end) {
                dto.setStatus(0);
            }else if(current < start){
                dto.setStatus(1);
            }else if(current > end){
                dto.setStatus(2);
            }
            universalCouponDtos.add(dto);
        });
        tdo.setUniversalCouponDtoList(universalCouponDtos);
        return Result.ok(tdo);
    }

    /**
     * 删除通用券
     *
     * @param universalCouponId 通用券id
     * @return {@link Result}
     */
    @Override
    public Result deleteUniversalCoupon(String universalCouponId) {
        LambdaQueryWrapper<UniversalCoupon> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!"".equals(universalCouponId),UniversalCoupon::getUniversalCouponId,universalCouponId);

        UniversalCoupon universalCoupon = universalCouponMapper.selectOne(wrapper);
        String currentTime = TimeUtil.getCurrentTime();
        // 状态
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        long current = 0;
        long start = 0;
        long end = 0;
        try {
            current = sdf.parse(currentTime).getTime();
            start = sdf.parse(universalCoupon.getStartTime()).getTime();
            end = sdf.parse(universalCoupon.getEndTime()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (current > start && current < end) {
            //使用中
            return Result.err("该优惠券正在使用中，无法删除！");
        }
        return universalCouponMapper.delete(wrapper) > 0 ? Result.ok("删除成功") : Result.err("删除失败");

    }

    /**
     * 选择通用券
     *
     * @return {@link Result}
     */
    @Override
    public Result selectUniversalCoupon() {
        LambdaQueryWrapper<UniversalCoupon> wrapper = new LambdaQueryWrapper<>();
        String currentTime = TimeUtil.getCurrentTime();
        wrapper.le(true,UniversalCoupon::getStartTime,currentTime);
        wrapper.ge(true,UniversalCoupon::getEndTime,currentTime);

        List<UniversalCoupon> selectList = universalCouponMapper.selectList(wrapper);

        ArrayList<UniversalCouponWxDto> universalCouponWxDtos = new ArrayList<>();
        selectList.forEach(c -> {
            UniversalCouponWxDto universalCouponWxDto = new UniversalCouponWxDto();
            BeanUtils.copyProperties(c,universalCouponWxDto);

            List<Commodity> commodityList = commodityServiceApiClient.getCommodityList();
            universalCouponWxDto.setCommodityList(commodityList);

            universalCouponWxDtos.add(universalCouponWxDto);
        });

        return Result.ok(universalCouponWxDtos);
    }

    /**
     * 选择通用优惠券通过id
     *
     * @param couponId 优惠券id
     * @return {@link UniversalCoupon}
     */
    @Override
    public UniversalCoupon selectUniversalCouponById(String couponId) {
        LambdaQueryWrapper<UniversalCoupon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(couponId),UniversalCoupon::getUniversalCouponId,couponId);


        return universalCouponMapper.selectOne(queryWrapper);
    }

    /**
     * 更新环球优惠券
     *
     * @param couponId 优惠券id
     */
    @Override
    public void updateUniversalCoupon(String couponId) {
        LambdaQueryWrapper<UniversalCoupon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(couponId),UniversalCoupon::getUniversalCouponId,couponId);
        UniversalCoupon universalCoupon = universalCouponMapper.selectOne(queryWrapper);

        LambdaUpdateWrapper<UniversalCoupon> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(!"".equals(couponId),UniversalCoupon::getUniversalCouponId,couponId)
                .set(!"".equals(couponId),UniversalCoupon::getTotalQuantity,universalCoupon.getTotalQuantity() - 1);

        universalCouponMapper.update(null,updateWrapper);
    }
}
