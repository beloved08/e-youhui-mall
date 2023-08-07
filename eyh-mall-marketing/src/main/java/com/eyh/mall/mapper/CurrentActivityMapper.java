package com.eyh.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyh.mall.entity.CurrentActivity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @Author 李平
 * @NAME CurrentActivityMapper
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期二
 * @Date 2023-04-18 20:20:14
 * @Description 当前秒杀活动表
 */
@Component
@Mapper
public interface CurrentActivityMapper extends BaseMapper<CurrentActivity> {
}
