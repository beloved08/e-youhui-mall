package com.eyh.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.entity.OrdinaryMallUserPage;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.redis.MallUserConstant;
import com.eyh.mall.entity.tdo.UserPurchaseVip;
import com.eyh.mall.entity.user.UserBalanceIntegralMqData;
import com.eyh.mall.feign.client.pay.UserBalanceApiClient;
import com.eyh.mall.feign.entity.MallUser;
import com.eyh.mall.goeasy.GoEasyUtil;
import com.eyh.mall.mapper.MallUserMapper;
import com.eyh.mall.rabbitmq.UserBalanceIntegralConstant;
import com.eyh.mall.redis.RedisUtil;
import com.eyh.mall.service.MallUserService;
import com.eyh.mall.util.TimeUtil;
import com.eyh.mall.util.json.JsonUtil;
import org.apache.xmlbeans.UserType;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 李平
 * @Date 2023-3-5
 */
@Service
public class MallUserServiceImpl extends ServiceImpl<MallUserMapper, MallUser> implements MallUserService {

    @Autowired
    private MallUserMapper mallUserMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JsonUtil<MallUser> jsonUtil;

    @Autowired
    private UserBalanceApiClient userBalanceApiClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 通过openid获取用户
     * @param openid
     * @return MallUser
     */
    @Override
    public MallUser getMallUser(String openid) {
        String key = MallUserConstant.MALL_USER_OPENID + openid;
        if(!redisUtil.hasKey(key)) {
            return selectMallUser(openid, null,null, key, MallUserConstant.MALL_USER_OPENID_TTL);
        }
        MallUser mallUser = jsonUtil.toJavaBean(redisUtil.get(key), MallUser.class);
        if(mallUser == null){
            //为空,查询数据库
            return selectMallUser(openid, null,null, key, MallUserConstant.MALL_USER_OPENID_TTL);
        }
        //不为空,返回Redis数据
        return mallUser;
    }

    /**
     * 通过phone获取用户
     * @param phone
     * @return MallUser
     */
    @Override
    public MallUser getMallUserByPhone(String phone) {
        String key = MallUserConstant.MALL_USER_PHONE + phone;
        if(!redisUtil.hasKey(key)) {
            return selectMallUser(null,phone,null, key, MallUserConstant.MALL_USER_PHONE_TTL);
        }
        MallUser mallUser = jsonUtil.toJavaBean(redisUtil.get(key), MallUser.class);
        if(mallUser == null){
            //为空,查询数据库
            return selectMallUser(null,phone,null, key, MallUserConstant.MALL_USER_PHONE_TTL);
        }
        //不为空,返回Redis数据
        return mallUser;
    }

    /**
     * 用户通过电话pwd得到商场
     *
     * @param phone    电话
     * @param password 密码
     * @return {@link MallUser}
     */
    @Override
    public MallUser getMallUserByPhonePwd(String phone, String password) {
        String key = MallUserConstant.MALL_USER_PHONE_PASSWORD + phone + password;
        if(!redisUtil.hasKey(key)) {
            return selectMallUser(null,phone,password, key,MallUserConstant.MALL_USER_PHONE_PASSWORD_TTL);
        }
        MallUser mallUser = jsonUtil.toJavaBean(redisUtil.get(key), MallUser.class);
        if(mallUser == null){
            //为空,查询数据库
            return selectMallUser(null,phone,password, key,MallUserConstant.MALL_USER_PHONE_PASSWORD_TTL);
        }
        //不为空,返回Redis数据
        return mallUser;
    }

    /**
     * 商城用户用户id
     *
     * @param userId 用户id
     * @return {@link MallUser}
     */
    @Override
    public MallUser getMallUserByUserId(String userId) {
        LambdaQueryWrapper<MallUser> mallUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        mallUserLambdaQueryWrapper.eq(!"".equals(userId),MallUser::getUserId,userId);
        return mallUserMapper.selectOne(mallUserLambdaQueryWrapper);
    }

    /**
     * wx绑定手机
     *
     * @param phone  电话
     * @param userId 用户id
     * @return {@link Result}
     */
    @Override
    public Result wxBindingPhone(String phone, String userId) {

        LambdaQueryWrapper<MallUser> mallUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        mallUserLambdaQueryWrapper.eq(!"".equals(phone),MallUser::getPhone,phone);
        MallUser mallUser = mallUserMapper.selectOne(mallUserLambdaQueryWrapper);

        if (mallUser != null){
            LambdaUpdateWrapper<MallUser> mallUserLambdaUpdateWrapper = new LambdaUpdateWrapper<>();

            LambdaQueryWrapper<MallUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(!"".equals(userId),MallUser::getUserId,userId);
            MallUser one = mallUserMapper.selectOne(wrapper);

            mallUserLambdaUpdateWrapper.eq(!"".equals(mallUser.getPhone()),MallUser::getPhone,phone)
                    .set(!"".equals(mallUser.getPhone()),MallUser::getOpenId,one.getOpenId());

            int update = mallUserMapper.update(null,mallUserLambdaUpdateWrapper);

            int delete = mallUserMapper.delete(wrapper);
            return  delete + update > 0 ? Result.ok("绑定成功") : Result.err("绑定错误");
        }

        // 更新wx的记录
        LambdaUpdateWrapper<MallUser> mallUserLambdaUpdateWrapper = new LambdaUpdateWrapper<>();

        mallUserLambdaUpdateWrapper.eq(!"".equals(userId),MallUser::getUserId,userId)
                .set(!"".equals(userId),MallUser::getPhone,phone);
        int update = mallUserMapper.update(null, mallUserLambdaUpdateWrapper);
        return  update > 0 ? Result.ok("绑定成功") : Result.err("绑定错误");
    }

    /**
     * 得到所有普通商城用户页面
     *
     * @param ordinaryMallUserPage 普通商城用户页面
     * @return {@link Result}
     */
    @Override
    public Result getAllOrdinaryMallUserByPage(OrdinaryMallUserPage ordinaryMallUserPage) {
        IPage<MallUser> page = new Page<MallUser>(ordinaryMallUserPage.getCurrentPage(), ordinaryMallUserPage.getPageSize());

        LambdaQueryWrapper<MallUser> wrapper = new LambdaQueryWrapper<>();

        if (!"".equals(ordinaryMallUserPage.getStatus())){
            wrapper.eq(!"".equals(ordinaryMallUserPage.getStatus()),MallUser::getStatus,Integer.valueOf(ordinaryMallUserPage.getStatus()));
        }

        if (!"".equals(ordinaryMallUserPage.getType())){
            wrapper.eq(!"".equals(ordinaryMallUserPage.getType()),MallUser::getType,Integer.valueOf(ordinaryMallUserPage.getType()));
        }

        if (!"".equals(ordinaryMallUserPage.getSex())){
            wrapper.eq(!"".equals(ordinaryMallUserPage.getSex()),MallUser::getSex,Integer.valueOf(ordinaryMallUserPage.getSex()));
        }
        if (!"".equals(ordinaryMallUserPage.getUserName())){
            wrapper.like(!"".equals(ordinaryMallUserPage.getUserName()),MallUser::getRealName,ordinaryMallUserPage.getUserName());
        }

        if (!"".equals(ordinaryMallUserPage.getPhone())){
            wrapper.like(!"".equals(ordinaryMallUserPage.getPhone()),MallUser::getPhone,ordinaryMallUserPage.getPhone());
        }

        return Result.ok(mallUserMapper.selectPage(page, wrapper));

    }

    /**
     * 更新pwd
     *
     * @param phone 电话
     * @param pwd   松材线虫病
     * @return {@link Result}
     */
    @Override
    public Result updatePwd(String phone, String pwd) {
        LambdaUpdateWrapper<MallUser> mallUserLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        mallUserLambdaUpdateWrapper.eq(!"".equals(phone),MallUser::getPhone,phone)
                .set(!"".equals(pwd),MallUser::getPassword,pwd);
        int update = mallUserMapper.update(null, mallUserLambdaUpdateWrapper);
        return update > 0 ? Result.ok("ok") : Result.err("err");
    }

    /**
     * 让商城用户名字
     *
     * @param userName 用户名
     * @return {@link Result}
     */
    @Override
    public MallUser getMallUserByName(String userName) {
        LambdaQueryWrapper<MallUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!"".equals(userName),MallUser::getRealName,userName);

        return mallUserMapper.selectOne(wrapper);
    }

    /**
     * 得到所有商城用户名
     *
     * @return {@link Result}
     */
    @Override
    public Result getAllMallUserName() {
        LambdaQueryWrapper<MallUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(true,MallUser::getStatus,0);

        List<MallUser> mallUserList = mallUserMapper.selectList(wrapper);
        List<String> list = new ArrayList<>();

        mallUserList.forEach(u -> {
            list.add(u.getRealName());
        });
        return Result.ok(list);
    }

    /**
     * 用户购买贵宾
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @Override
    public Result userPurchaseVip(String userId) {
        //查询该用户是否已经购买会员
        LambdaQueryWrapper<MallUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(userId),MallUser::getUserId,userId);
        MallUser mallUser = mallUserMapper.selectOne(queryWrapper);

        if (mallUser.getType() == 1){
            return Result.err("您已购买会员，不能重复购买");
        }

        //查询该用户余额是否足够
        Result result = userBalanceApiClient.getUserBalanceNumber(userId);
        double data = (double)result.getData();
        if (data < 399){
            return Result.err("您的可用余额不足，请先充值");
        }
        //更新用户类型 普通用户->会员用户
        LambdaUpdateWrapper<MallUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(!"".equals(userId),MallUser::getUserId,userId)
                .set(!"".equals(userId),MallUser::getType,1)
                .set(!"".equals(userId),MallUser::getPurchaseVipTime,TimeUtil.getCurrentTime());

        int update = mallUserMapper.update(null, updateWrapper);
        if (update < 1){
            return Result.err("会员开通失败");
        }

        //利用RabbitMQ修改用户余额和增加用户积分
        UserBalanceIntegralMqData mqData = new UserBalanceIntegralMqData();
        mqData.setUserId(userId);
        mqData.setUserBalanceDeduction(399);
        mqData.setUserIntegralDeduction(3000);
        rabbitTemplate.convertAndSend(UserBalanceIntegralConstant.USER_BALANCE_INTEGRAL_QUEUE,JSON.toJSONString(mqData));

        UserPurchaseVip userPurchaseVip = new UserPurchaseVip();
        userPurchaseVip.setUserName(mallUser.getRealName());
        userPurchaseVip.setCurrentTime(TimeUtil.getCurrentTime());

        GoEasyUtil.messagePush("userPurchaseVip_channel",userPurchaseVip);

        // 查询当前用户信息，返回
        LambdaQueryWrapper<MallUser> queryWrapperNow = new LambdaQueryWrapper<>();
        queryWrapperNow.eq(!"".equals(userId),MallUser::getUserId,userId);
        MallUser mallUserNow = mallUserMapper.selectOne(queryWrapperNow);

        return Result.ok("恭喜你，会员开通成功", mallUserNow);
    }

    /**
     * 得到总成员
     *
     * @return {@link Result}
     */
    @Override
    public Result getTotalUser() {
        return Result.ok(mallUserMapper.selectCount(null));
    }

    /**
     * 得到总成员
     *
     * @return {@link Result}
     */
    @Override
    public Result getTotalMember() {
        LambdaQueryWrapper<MallUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(true,MallUser::getType,1);
        return Result.ok(mallUserMapper.selectCount(wrapper));
    }

    /**
     * 今天得到添加成员
     *
     * @return {@link Result}
     */
    @Override
    public Result getTodayAddMember() {
        LambdaQueryWrapper<MallUser> wrapper = new LambdaQueryWrapper<>();

        wrapper.between(true,MallUser::getPurchaseVipTime, LocalDate.now().toString() + " 00:00:00",
                LocalDate.now().toString() + " 23:59:59");

        return Result.ok(mallUserMapper.selectCount(wrapper));
    }

    /**
     * 选择商城用户
     *
     * @param openid   openid
     * @param phone    电话
     * @param password 密码
     * @param key      关键
     * @param ttl      ttl
     * @return {@link MallUser}
     */
    MallUser selectMallUser(String openid, String phone, String password, String key,long ttl) {
        LambdaQueryWrapper<MallUser> mallUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        mallUserLambdaQueryWrapper.eq(openid != null,MallUser::getOpenId,openid);

        mallUserLambdaQueryWrapper.eq(phone != null,MallUser::getPhone,phone);

        mallUserLambdaQueryWrapper.eq(password != null,MallUser::getPassword,password);
        MallUser mallUser = mallUserMapper.selectOne(mallUserLambdaQueryWrapper);

        //存入redis
        if(mallUser == null){
            return null;
        }
        redisUtil.set(key, JSON.toJSONString(mallUser),ttl);
        return mallUser;
    }

}
