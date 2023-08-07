package com.eyh.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyh.mall.entity.CommodityComment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author 李平
 * @Date 2023-2-18
 */
@Mapper
@Component
public interface CommodityCommentMapper extends BaseMapper<CommodityComment> {

}
