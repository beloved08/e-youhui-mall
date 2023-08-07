package com.eyh.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyh.mall.entity.UserCoupon;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @Author 李平
 * @NAME UserCouponMapper
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期三
 * @Date 2023-03-29 20:15:54
 * @Description 用户优惠券数据层
 */
@Component
@Mapper
public interface UserCouponMapper extends BaseMapper<UserCoupon> {
}
