package com.eyh.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyh.mall.entity.UserBalanceChange;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @Author 李平
 * @NAME UserBalanceChangeMapper
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期五
 * @Date 2023-03-31 11:14:31
 * @Description 用户余额变动数据层
 */
@Component
@Mapper
public interface UserBalanceChangeMapper extends BaseMapper<UserBalanceChange> {
}
