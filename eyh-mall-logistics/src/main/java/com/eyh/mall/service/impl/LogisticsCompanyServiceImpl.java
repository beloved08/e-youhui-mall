package com.eyh.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.entity.ExpressOrder;
import com.eyh.mall.entity.LogisticsCompany;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.vo.ExpressOrderPage;
import com.eyh.mall.mapper.ExpressOrderMapper;
import com.eyh.mall.mapper.LogisticsCompanyMapper;
import com.eyh.mall.service.LogisticsCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 李平
 * @NAME LogisticsCompanyServiceImpl
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 四月
 * @@DAY_NAME_FULL 星期五
 * @Date 2023-04-07 18:21:29
 * @Description 物流公司业务接口实现类
 */
@Service
public class LogisticsCompanyServiceImpl extends ServiceImpl<LogisticsCompanyMapper, LogisticsCompany> implements LogisticsCompanyService {

    @Autowired
    private LogisticsCompanyMapper logisticsCompanyMapper;

    /**
     * 让物流公司
     *
     * @return {@link Result}
     */
    @Override
    public Result getLogisticsCompany() {
        return Result.ok(logisticsCompanyMapper.selectList(null));
    }

    /**
     * forbiddenis物流公司
     *
     * @param logisticsCompanyId 物流公司id
     * @param status             状态
     * @return {@link Result}
     */
    @Override
    public Result forbiddenisLogisticsCompany(String logisticsCompanyId,Integer status) {
        LambdaUpdateWrapper<LogisticsCompany> updateWrapper = new LambdaUpdateWrapper<>();

        updateWrapper.eq(!"".equals(logisticsCompanyId),LogisticsCompany::getLogisticsCompanyId,logisticsCompanyId)
                .set(!"".equals(logisticsCompanyId),LogisticsCompany::getIsForbidden,status);
        int update = logisticsCompanyMapper.update(null, updateWrapper);
        return update > 0 ? Result.ok("操作成功") :Result.err("操作失败");
    }

    /**
     * 物流公司名称
     *
     * @return {@link Result}
     */
    @Override
    public Result getLogisticsCompanyName() {
        LambdaQueryWrapper<LogisticsCompany> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(true,LogisticsCompany::getIsForbidden,0);

        List<String> list = new ArrayList<>();
        logisticsCompanyMapper.selectList(queryWrapper).forEach(c -> {
            list.add(c.getLogisticsCompanyName());
        });
        return Result.ok(list);
    }

    /**
     * 选择物流公司名字
     *
     * @param logisticsCompanyName 物流公司名称
     * @return {@link LogisticsCompany}
     */
    @Override
    public LogisticsCompany selectLogisticsCompanyByName(String logisticsCompanyName) {
        LambdaQueryWrapper<LogisticsCompany> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(logisticsCompanyName),LogisticsCompany::getLogisticsCompanyName,logisticsCompanyName);

        return logisticsCompanyMapper.selectOne(queryWrapper);
    }

}
