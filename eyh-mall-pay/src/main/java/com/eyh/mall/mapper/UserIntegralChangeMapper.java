package com.eyh.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyh.mall.entity.UserIntegralChange;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @Author 李平
 * @NAME UserIntegralChangeMapper
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期五
 * @Date 2023-03-31 11:16:34
 * @Description 用户积分变动数据层
 */
@Component
@Mapper
public interface UserIntegralChangeMapper extends BaseMapper<UserIntegralChange> {
}
