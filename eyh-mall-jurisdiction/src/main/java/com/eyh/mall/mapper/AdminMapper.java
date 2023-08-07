package com.eyh.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyh.mall.entity.user.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author 李平
 * @Date 2023-2-13
 */
@Mapper
@Component
public interface AdminMapper extends BaseMapper<Admin> {

}
