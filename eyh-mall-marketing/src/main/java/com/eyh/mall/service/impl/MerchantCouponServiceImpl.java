package com.eyh.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.entity.MerchantCoupon;
import com.eyh.mall.entity.UniversalCoupon;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.dto.MerchantCouponDto;
import com.eyh.mall.entity.dto.MerchantCouponTdo;
import com.eyh.mall.entity.dto.MerchantCouponWxDto;
import com.eyh.mall.entity.merchantStores.Business;
import com.eyh.mall.entity.vo.MerchantCouponPage;
import com.eyh.mall.entity.vo.MerchantCouponVo;
import com.eyh.mall.feign.client.commodity.CommodityServiceApiClient;
import com.eyh.mall.feign.client.merchantStores.BusinessApiClient;
import com.eyh.mall.feign.entity.Commodity;
import com.eyh.mall.mapper.MerchantCouponMapper;
import com.eyh.mall.service.MerchantCouponService;
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
 * @NAME MerchantCouponServiceImpl
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-03-29 15:39:13
 * @Description 商家优惠券业务接口实现类
 */
@Service
public class MerchantCouponServiceImpl extends ServiceImpl<MerchantCouponMapper, MerchantCoupon> implements MerchantCouponService {

    @Autowired
    private MerchantCouponMapper merchantCouponMapper;

    @Autowired
    private BusinessApiClient businessApiClient;

    @Autowired
    private CommodityServiceApiClient commodityServiceApiClient;

    /**
     * 添加商户优惠券
     *
     * @param merchantCouponVo 商家优惠券签证官
     * @return {@link Result}
     */
    @Override
    public Result addMerchantCoupon(MerchantCouponVo merchantCouponVo) {
        Business business = businessApiClient.getBusiness(merchantCouponVo.getBusinessName());

        MerchantCoupon merchantCoupon = new MerchantCoupon();
        merchantCoupon.setId(UUID.randomUUID().toString());
        merchantCoupon.setMerchantCouponId(UUID.randomUUID().toString());
        merchantCoupon.setBusinessId(business.getBusinessId());

        BeanUtils.copyProperties(merchantCouponVo,merchantCoupon);

        return merchantCouponMapper.insert(merchantCoupon) > 0 ? Result.ok("添加成功") : Result.err("添加失败");
    }

    /**
     * 得到所有商家优惠券页面
     *
     * @param merchantCouponPage 商家优惠券页面
     * @return {@link Result}
     */
    @Override
    public Result getAllMerchantCouponPage(MerchantCouponPage merchantCouponPage) {
        IPage<MerchantCoupon> page = new Page<MerchantCoupon>(merchantCouponPage.getCurrentPage(), merchantCouponPage.getPageSize());

        //条件搜索
        LambdaQueryWrapper<MerchantCoupon> queryWrapper = new LambdaQueryWrapper<>();
        //名称
        queryWrapper.like(!"".equals(merchantCouponPage.getMerchantCouponName()),
                MerchantCoupon::getMerchantCouponName,
                merchantCouponPage.getMerchantCouponName());

        //商家
        if (!"".equals(merchantCouponPage.getBusinessName())){
            Business business = businessApiClient.getBusiness(merchantCouponPage.getBusinessName());
            queryWrapper.eq(!"".equals(merchantCouponPage.getBusinessName()),
                    MerchantCoupon::getBusinessId,business.getBusinessId());
        }

        /**
         * 状态
         * 0：使用中，1：未开始，2：已结束
         * ge:大于等于
         * le:小于等于
         */
        String currentTime = TimeUtil.getCurrentTime();
        if (merchantCouponPage.getStatus() == 0){
            // 使用中，当前时间>开始时间，当前时间<结束时间
            queryWrapper.le(true,MerchantCoupon::getStartTime,currentTime);
            queryWrapper.ge(true,MerchantCoupon::getEndTime,currentTime);
        }
        if (merchantCouponPage.getStatus() == 1){
            //未开始，当前时间<开始时间
            queryWrapper.ge(true,MerchantCoupon::getStartTime,currentTime);
        }
        if (merchantCouponPage.getStatus() == 2){
            //已结束，当前时间>结束时间
            queryWrapper.le(true,MerchantCoupon::getEndTime,currentTime);
        }

        IPage<MerchantCoupon> selectPage = merchantCouponMapper.selectPage(page, queryWrapper);
        MerchantCouponTdo tdo = new MerchantCouponTdo();
        tdo.setCurrentPage(selectPage.getCurrent());
        tdo.setPageSize(selectPage.getPages());
        tdo.setTotal(selectPage.getTotal());
        tdo.setSize(selectPage.getSize());

        List<MerchantCouponDto> merchantCouponDtos = new ArrayList<>();

        selectPage.getRecords().forEach(c -> {
            MerchantCouponDto dto = new MerchantCouponDto();

            dto.setMerchantCouponId(c.getMerchantCouponId());
            dto.setMerchantCouponName(c.getMerchantCouponName());

            Business businessById = businessApiClient.getBusinessById(c.getBusinessId());
            dto.setBusinessName(businessById.getCalloutContent());

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
            merchantCouponDtos.add(dto);
        });
        tdo.setMerchantCouponDtoList(merchantCouponDtos);

        return Result.ok(tdo);
    }

    /**
     * 删除商户优惠券通过id
     *
     * @param merchantCouponId 商家优惠券id
     * @return {@link Result}
     */
    @Override
    public Result deleteMerchantCouponById(String merchantCouponId) {
        LambdaQueryWrapper<MerchantCoupon> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!"".equals(merchantCouponId),MerchantCoupon::getMerchantCouponId,merchantCouponId);

        MerchantCoupon merchantCoupon = merchantCouponMapper.selectOne(wrapper);
        String currentTime = TimeUtil.getCurrentTime();
        // 状态
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        long current = 0;
        long start = 0;
        long end = 0;
        try {
            current = sdf.parse(currentTime).getTime();
            start = sdf.parse(merchantCoupon.getStartTime()).getTime();
            end = sdf.parse(merchantCoupon.getEndTime()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (current > start && current < end) {
            //使用中
            return Result.err("该优惠券正在使用中，无法删除！");
        }
        return merchantCouponMapper.delete(wrapper) > 0 ? Result.ok("删除成功") : Result.err("删除失败");
    }

    /**
     * 选择所有商家优惠券
     *
     * @return {@link Result}
     */
    @Override
    public Result selectAllMerchantCoupon() {
        LambdaQueryWrapper<MerchantCoupon> wrapper = new LambdaQueryWrapper<>();
        //状态为使用中
        String currentTime = TimeUtil.getCurrentTime();
        wrapper.le(true,MerchantCoupon::getStartTime,currentTime);
        wrapper.ge(true,MerchantCoupon::getEndTime,currentTime);

        List<MerchantCoupon> selectList = merchantCouponMapper.selectList(wrapper);
        List<MerchantCouponWxDto> merchantCouponWxDtoArrayList = new ArrayList<>();
        selectList.forEach(c -> {
            MerchantCouponWxDto merchantCouponWxDto = new MerchantCouponWxDto();
            BeanUtils.copyProperties(c,merchantCouponWxDto);
            //商家
            Business businessById = businessApiClient.getBusinessById(c.getBusinessId());
            merchantCouponWxDto.setBusinessName(businessById.getCalloutContent());
            //商品
            List<Commodity> commodityList = commodityServiceApiClient.getCommodityByBusinessId(c.getBusinessId());
            merchantCouponWxDto.setCommodityList(commodityList);

            merchantCouponWxDtoArrayList.add(merchantCouponWxDto);
        });

        return Result.ok(merchantCouponWxDtoArrayList);
    }

    /**
     * 选择商家优惠券通过id
     *
     * @param couponId 优惠券id
     * @return {@link MerchantCoupon}
     */
    @Override
    public MerchantCoupon selectMerchantCouponById(String couponId) {
        LambdaQueryWrapper<MerchantCoupon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(couponId),MerchantCoupon::getMerchantCouponId,couponId);
        return merchantCouponMapper.selectOne(queryWrapper);
    }

    /**
     * 更新商户优惠券
     *
     * @param couponId 优惠券id
     */
    @Override
    public void updateMerchantCoupon(String couponId) {
        LambdaQueryWrapper<MerchantCoupon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(couponId),MerchantCoupon::getMerchantCouponId,couponId);
        MerchantCoupon merchantCoupon = merchantCouponMapper.selectOne(queryWrapper);

        LambdaUpdateWrapper<MerchantCoupon> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(!"".equals(couponId),MerchantCoupon::getMerchantCouponId,couponId)
                .set(!"".equals(couponId),MerchantCoupon::getTotalQuantity,merchantCoupon.getTotalQuantity() - 1);

        merchantCouponMapper.update(null,updateWrapper);
    }
}
