package com.eyh.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyh.mall.entity.Menu;

import java.util.List;

/**
 * @author 李平
 * @Date 2023-2-13
 */
public interface MenuService extends IService<Menu> {

    /**
     * 获取用户菜单
     * @param authorization
     * @return List<Menu>
     */
    List<Menu> selectUserMenu(String authorization);


}
