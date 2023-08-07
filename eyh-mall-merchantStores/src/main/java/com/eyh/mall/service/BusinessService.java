package com.eyh.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyh.mall.entity.MerchantStorePage;
import com.eyh.mall.entity.MerchantStores;
import com.eyh.mall.entity.ResidentAudit;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.merchantStores.Business;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 业务服务
 *
 * @author 李平
 * @Date 2023-2-18
 */
public interface BusinessService extends IService<Business> {

    /**
     * 商家入驻
     * @param merchantStores
     * @return Result
     */
    Result addMerchantStores(MerchantStores merchantStores);

    /**
     * 上传图片至服务器和OSS
     * @param file
     * @param name
     * @return Result
     */
    Result uploadLogo(MultipartFile file, String name);

    /**
     * 选择业务用户id
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    Result selectBusinessByUserId(String userId);

    /**
     * 选择页面
     *
     * @param merchantStorePage 商人商店页面
     * @return {@link Result}
     */
    Result selectByPage(MerchantStorePage merchantStorePage);

    /**
     * 选择业务用户id
     *
     * @param businessId 业务标识
     * @return {@link Result}
     */
    Result selectBusinessUserById(String businessId);

    /**
     * 居民业务审计
     *
     * @param residentAudit 居民审计
     * @return {@link Result}
     */
    Result businessResidentAudit(ResidentAudit residentAudit);

    /**
     * 通过身份证注销业务
     *
     * @param businessId 业务标识
     * @return {@link Result}
     */
    Result logOffBusinessById(String businessId);

    /**
     * 得到企业名字
     *
     * @param businessShopName 企业字号
     * @return {@link Business}
     */
    Business getBusinessByName(String businessShopName);

    /**
     * 获得商业名称列表
     *
     * @param businessName 业务名称
     * @return {@link Result}
     */
    Result getBusinessNameList(String businessName);

    /**
     * 通过id获取业务
     *
     * @param businessId 业务标识
     * @return {@link Business}
     */
    Business getBusinessById(String businessId);

    /**
     * 业务由业务id
     *
     * @param businessId 业务标识
     * @return {@link Result}
     */
    Result getBusinessByBusinessId(String businessId);

    /**
     * 得到总业务
     *
     * @return {@link Result}
     */
    Result getTotalBusiness();

    /**
     * 得到所有商业地图
     *
     * @return {@link Result}
     */
    Result getAllBusinessMap();

    /**
     * 得到所有业务
     *
     * @return {@link List}<{@link Business}>
     */
    List<Business> getAllBusiness();

}
