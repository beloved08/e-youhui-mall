package com.eyh.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.entity.UserBalance;
import com.eyh.mall.entity.UserBalanceChange;
import com.eyh.mall.entity.UserIntegral;
import com.eyh.mall.entity.UserIntegralChange;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.dto.UserBalanceIntegralDto;
import com.eyh.mall.entity.dto.UserBalanceIntegralTdo;
import com.eyh.mall.entity.vo.UserBalanceIntegralPage;
import com.eyh.mall.feign.client.user.UserApiClient;
import com.eyh.mall.feign.entity.MallUser;
import com.eyh.mall.mapper.UserBalanceChangeMapper;
import com.eyh.mall.mapper.UserBalanceMapper;
import com.eyh.mall.mapper.UserIntegralChangeMapper;
import com.eyh.mall.mapper.UserIntegralMapper;
import com.eyh.mall.service.UserBalanceChangeService;
import com.eyh.mall.service.UserBalanceService;
import com.eyh.mall.service.UserIntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author 李平
 * @NAME UserBalanceServiceImpl
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期五
 * @Date 2023-03-31 11:23:36
 * @Description 用户余额业务接口实现类
 */
@Service
public class UserBalanceServiceImpl extends ServiceImpl<UserBalanceMapper, UserBalance> implements UserBalanceService {

    @Autowired
    private UserBalanceMapper userBalanceMapper;

    @Autowired
    private UserIntegralMapper userIntegralMapper;

    @Autowired
    private UserIntegralChangeMapper userIntegralChangeMapper;

    @Autowired
    private UserBalanceChangeMapper userBalanceChangeMapper;

    @Autowired
    private UserBalanceChangeService userBalanceChangeService;

    @Autowired
    private UserIntegralService userIntegralService;

    @Autowired
    private UserApiClient userApiClient;

    /**
     * 用户余额充值
     *
     * @param userBalance 用户平衡
     * @return {@link Result}
     */
    @Override
    public Result userBalanceRecharge(UserBalance userBalance) {
        //判断当前用户有无充值记录
        LambdaQueryWrapper<UserBalance> userBalanceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userBalanceLambdaQueryWrapper.eq(!"".equals(userBalance.getUserId()),UserBalance::getUserId,userBalance.getUserId());
        UserBalance selectOne = userBalanceMapper.selectOne(userBalanceLambdaQueryWrapper);

        int i = 0;
        if (selectOne == null){
            //第一次充值，插入一条记录
            userBalance.setId(UUID.randomUUID().toString());
            userBalance.setBalanceId(UUID.randomUUID().toString());
            i += userBalanceMapper.insert(userBalance);
        } else {
            //不是第一次充值，更新该用户余额
            LambdaUpdateWrapper<UserBalance> userBalanceLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            userBalanceLambdaUpdateWrapper.eq(!"".equals(userBalance.getUserId()),UserBalance::getUserId,userBalance.getUserId())
                    .set(!"".equals(userBalance.getUserId()),UserBalance::getAvailableBalance,userBalance.getAvailableBalance() + selectOne.getAvailableBalance());

            i += userBalanceMapper.update(null, userBalanceLambdaUpdateWrapper);
        }
        if (i > 0){
            //在用户余额变动明细表新增记录
            userBalanceChangeService.updateUserBalanceChangeDetail(0,userBalance.getAvailableBalance(),userBalance.getUserId());

            //增加充值金额对应的积分数
            UserIntegral userIntegral = new UserIntegral();
            userIntegral.setUserId(userBalance.getUserId());
            userIntegral.setAvailableIntegral(userBalance.getAvailableBalance() * 10);
            userIntegralService.userIntegralRecharge(userIntegral);

            return Result.ok("余额充值成功");
        } else {
            //充值失败
            return Result.err("余额充值失败");
        }

    }

    /**
     * 获取用户平衡数量
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @Override
    public Result getUserBalanceNumber(String userId) {
        LambdaQueryWrapper<UserBalance> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(userId),UserBalance::getUserId,userId);
        UserBalance userBalance = userBalanceMapper.selectOne(queryWrapper);
        return userBalance == null ? Result.ok(0) : Result.ok(userBalance.getAvailableBalance());
    }

    /**
     * 用户余额扣除
     *
     * @param userId  用户id
     * @param balance 平衡
     */
    @Override
    public void userBalanceDeduction(String userId, double balance) {
        LambdaQueryWrapper<UserBalance> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(userId),UserBalance::getUserId,userId);
        UserBalance userBalance = userBalanceMapper.selectOne(queryWrapper);

        LambdaUpdateWrapper<UserBalance> updateWrapper = new LambdaUpdateWrapper<>();

        updateWrapper.eq(!"".equals(userId),UserBalance::getUserId,userId)
                .set(true,UserBalance::getAvailableBalance, userBalance.getAvailableBalance() - balance);

        int update = userBalanceMapper.update(null, updateWrapper);
        if (update > 0){
            userBalanceChangeService.updateUserBalanceChangeDetail(1,balance,userBalance.getUserId());
        }

    }

    /**
     * 得到用户平衡页面
     *
     * @param userBalancePage 用户平衡页面
     * @return {@link Result}
     */
    @Override
    public Result getUserBalancePage(UserBalanceIntegralPage userBalancePage) {
        IPage<UserBalance> page = new Page<UserBalance>(userBalancePage.getCurrentPage(), userBalancePage.getPageSize());

        LambdaQueryWrapper<UserBalance> queryWrapper = new LambdaQueryWrapper<>();

        if (!"".equals(userBalancePage.getUserName())){
            MallUser mallUserByName = userApiClient.getMallUserByName(userBalancePage.getUserName());
            queryWrapper.eq(!"".equals(mallUserByName.getUserId()),UserBalance::getUserId,mallUserByName.getUserId());
        }
        IPage<UserBalance> selectPage = userBalanceMapper.selectPage(page, queryWrapper);

        UserBalanceIntegralTdo tdo = new UserBalanceIntegralTdo();
        tdo.setCurrentPage(selectPage.getCurrent());
        tdo.setPageSize(selectPage.getPages());
        tdo.setSize(selectPage.getSize());
        tdo.setTotal(selectPage.getTotal());

        List<UserBalanceIntegralDto> userBalanceDto = new ArrayList<>();
        selectPage.getRecords().forEach(b ->{
            UserBalanceIntegralDto dto = new UserBalanceIntegralDto();
            // 余额
            dto.setBalanceId(b.getBalanceId());
            dto.setAvailableBalance(b.getAvailableBalance());
            MallUser mallUserByUserId = userApiClient.getMallUserByUserId(b.getUserId());
            dto.setUserName(mallUserByUserId.getRealName());

            // 充值
            LambdaQueryWrapper<UserBalanceChange> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(true,UserBalanceChange::getChangeType,0);
            //按照创建时间降序
            wrapper1.orderByDesc(true,UserBalanceChange::getChangeTime);
            //使用last方法拼接sql语句
            wrapper1.last(true,"limit 20");
            List<UserBalanceChange> listRecharge = userBalanceChangeMapper.selectList(wrapper1);

            // 扣减
            LambdaQueryWrapper<UserBalanceChange> wrapper2 = new LambdaQueryWrapper<>();
            wrapper2.eq(true,UserBalanceChange::getChangeType,1);
            //按照创建时间降序
            wrapper2.orderByDesc(true,UserBalanceChange::getChangeTime);
            //使用last方法拼接sql语句
            wrapper2.last(true,"limit 20");
            List<UserBalanceChange> listDeduction = userBalanceChangeMapper.selectList(wrapper2);

            dto.setUserBalanceChangeRechargeList(listRecharge);
            dto.setUserBalanceChangeDeductionList(listDeduction);

            //积分
            LambdaQueryWrapper<UserIntegral> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(!"".equals(b.getUserId()),UserIntegral::getUserId,b.getUserId());
            UserIntegral userIntegral = userIntegralMapper.selectOne(wrapper);
            dto.setAvailableIntegral(userIntegral.getAvailableIntegral());
            dto.setIntegralId(userIntegral.getIntegralId());

            //积分充值
            LambdaQueryWrapper<UserIntegralChange> wrapper3 = new LambdaQueryWrapper<>();
            wrapper3.eq(true,UserIntegralChange::getChangeType,0);
            //按照创建时间降序
            wrapper3.orderByDesc(true,UserIntegralChange::getChangeTime);
            //使用last方法拼接sql语句
            wrapper3.last(true,"limit 20");
            List<UserIntegralChange> userIntegralChanges = userIntegralChangeMapper.selectList(wrapper3);

            //积分扣减
            LambdaQueryWrapper<UserIntegralChange> wrapper4 = new LambdaQueryWrapper<>();
            wrapper4.eq(true,UserIntegralChange::getChangeType,1);
            //按照创建时间降序
            wrapper4.orderByDesc(true,UserIntegralChange::getChangeTime);
            //使用last方法拼接sql语句
            wrapper4.last(true,"limit 20");
            List<UserIntegralChange> userIntegralChanges1 = userIntegralChangeMapper.selectList(wrapper4);

            dto.setUserIntegralChangeRechargeList(userIntegralChanges);
            dto.setUserIntegralChangeDeductionList(userIntegralChanges1);

            userBalanceDto.add(dto);
        });
        tdo.setUserBalanceDtoList(userBalanceDto);

        return Result.ok(tdo);
    }

}
