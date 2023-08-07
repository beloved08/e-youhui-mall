package com.eyh.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyh.mall.feign.entity.MallUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author 李平
 * @Date 2023-3-5
 */
@Mapper
@Component
public interface MallUserMapper extends BaseMapper<MallUser> {

}
