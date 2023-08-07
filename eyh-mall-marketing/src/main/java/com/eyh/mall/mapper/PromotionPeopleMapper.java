package com.eyh.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyh.mall.entity.PromotionPeople;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @Author 李平
 * @NAME PromotionPeopleMapper
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期四
 * @Date 2023-04-13 13:33:33
 * @Description 促销人员数据层
 */
@Component
@Mapper
public interface PromotionPeopleMapper extends BaseMapper<PromotionPeople> {

}
