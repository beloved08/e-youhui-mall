package com.eyh.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.entity.Admin;
import com.eyh.mall.entity.AdminListVO;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.redis.AdminConstant;
import com.eyh.mall.entity.redis.RedisConstant;
import com.eyh.mall.feign.client.jurisdiction.JurisdictionAdminClient;
import com.eyh.mall.feign.entity.Role;
import com.eyh.mall.mapper.AdminMapper;
import com.eyh.mall.redis.RedisUtil;
import com.eyh.mall.service.AdminService;
import com.eyh.mall.util.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李平
 * @Date 2023-1-27
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JsonUtil<AdminListVO> jsonUtil;

    @Autowired
    private JsonUtil<Admin> jsonUtil2;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private JurisdictionAdminClient jurisdictionAdminClient;

    @Override
    public Admin selectAdminOne(String account) {
        String adminKey = RedisConstant.ADMIN_PREFIX + account;
        // 判断是否存在此key
        if(!redisUtil.hasKey(adminKey)){
            //不存在
            return getAdmin(account);
        }
        //存在,查询redis
        Admin admin = jsonUtil2.toJavaBean(redisUtil.get(adminKey), Admin.class);
        if(admin == null){
            //为空,查询数据库
            return getAdmin(account);
        }
        //不为空,返回Redis数据
        return admin;
    }

    public Admin getAdmin(String account) {
        String adminKey = RedisConstant.ADMIN_PREFIX + account;

        LambdaQueryWrapper<Admin> lqw = new LambdaQueryWrapper<>();
        lqw.eq(account != null,Admin::getAccount,account);
        Admin admin = adminMapper.selectOne(lqw);
        if(admin == null){
            return null;
        }
        redisUtil.set(adminKey, JSON.toJSONString(admin),RedisConstant.ADMIN_TIME);
        return admin;
    }

    @Override
    public Result getAllAdminList(Integer type) {
        String key = AdminConstant.USER_ADMIN_LIST + type;
        // 判断是否存在key
        if (!redisUtil.hasKey(key)){
            // 不存在，查询数据库
            List<AdminListVO> adminList = selectAdminList(type);
            return adminList.size() == 0 ? Result.err("超级管理员数据获取失败") : Result.ok(adminList);
        }
        // 存在
        List<Object> objectList = redisUtil.lGet(key, 0, -1);
        if(objectList.size() == 0){
            // 查询数据库
            List<AdminListVO> adminList = selectAdminList(type);
            return adminList.size() == 0 ? Result.err("超级管理员数据获取失败") : Result.ok(adminList);
        }

        List<AdminListVO> adminList = jsonUtil.toListBean(objectList, AdminListVO.class);
        if (adminList.size() == 0){
            //查询数据库
            List<AdminListVO> adminLists = selectAdminList(type);
            return adminLists.size() == 0 ? Result.err("超级管理员数据获取失败") : Result.ok(adminLists);
        }

        return Result.ok(adminList);
    }

    public List<AdminListVO> selectAdminList(Integer type){
        String key = AdminConstant.USER_ADMIN_LIST + type;

        LambdaQueryWrapper<Admin> lqw = new LambdaQueryWrapper<>();
        lqw.eq(type != null,Admin::getType,type);
        List<Admin> selectList = adminMapper.selectList(lqw);

        if(selectList.size() == 0){
            return null;
        }
        // 查询该类管理员的角色信息
        Role role = jurisdictionAdminClient.selectRole(selectList.get(0).getAccount());
        if(role == null){
            return null;
        }
        List<AdminListVO> adminListVO = new ArrayList<>();
        for (Admin admin : selectList){
            AdminListVO vo = new AdminListVO();
            vo.setAccount(admin.getAccount());
            vo.setFrozen(admin.getFrozen());
            vo.setRoleName(role.getRoleName());
            vo.setRoleDesc(role.getRoleDesc());
            adminListVO.add(vo);
        }
        // 存入Redis
        redisUtil.lSet(key,adminListVO, AdminConstant.USER_ADMIN_LIST_TIME);
        return adminListVO;
    }
}
