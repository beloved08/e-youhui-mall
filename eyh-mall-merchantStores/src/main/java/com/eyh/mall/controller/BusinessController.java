package com.eyh.mall.controller;

import com.eyh.mall.entity.MerchantSettlementChannelData;
import com.eyh.mall.entity.MerchantStorePage;
import com.eyh.mall.entity.MerchantStores;
import com.eyh.mall.entity.ResidentAudit;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.merchantStores.Business;
import com.eyh.mall.goeasy.GoEasyUtil;
import com.eyh.mall.service.BusinessService;
import com.eyh.mall.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 李平
 * @Date 2023-2-18
 */
@RestController
@RequestMapping("/merchantStores/wx")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @GetMapping("/getGoeasy")
    public Result testGetGoeasy(){
        MerchantSettlementChannelData data = new MerchantSettlementChannelData();
        data.setTitle("商家店铺入驻申请");
        data.setBusinessName("ljkfwej");
        data.setShopName("klsnretioeh");
        data.setShopAvatar("https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/avatar/avatar.png");
        data.setCurrentTime(TimeUtil.getCurrentTime());

        GoEasyUtil.messagePush("merchant_settlement_channel",data);
        return Result.ok(data);
    }

    /**
     * 得到所有商业地图
     *
     * @return {@link Result}
     */
    @GetMapping("/getAllBusinessMap")
    public Result getAllBusinessMap() {
        return businessService.getAllBusinessMap();
    }

    /**
     * 得到总业务
     *
     * @return {@link Result}
     */
    @GetMapping("/getTotalBusiness")
    public Result getTotalBusiness() {
        return businessService.getTotalBusiness();
    }

    /**
     * 商家入驻
     * @param merchantStores
     * @return Result
     */
    @PostMapping("/addBusiness")
    public Result addBusiness(@RequestBody MerchantStores merchantStores){
        return businessService.addMerchantStores(merchantStores);
    }

    /**
     * 让业务用户id
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @GetMapping("/getBusinessByUserId/{userId}")
    public Result getBusinessByUserId(@PathVariable String userId){
        return businessService.selectBusinessByUserId(userId);
    }

    /**
     * 分页获取
     *
     * @param merchantStorePage 商人商店页面
     * @return {@link Result}
     */
    @PostMapping("/getMerchantStorePage")
    public Result getPage(@RequestBody MerchantStorePage merchantStorePage){
        return businessService.selectByPage(merchantStorePage);
    }

    /**
     * 让业务用户id
     *
     * @param businessId 业务标识
     * @return {@link Result}
     */
    @GetMapping("/getBusinessUserById/{businessId}")
    public Result getBusinessUserById(@PathVariable String businessId){
        return businessService.selectBusinessUserById(businessId);
    }

    /**
     * 居民审计
     *
     * @param residentAudit 居民审计
     * @return {@link Result}
     */
    @PostMapping("/businessResidentAudit")
    public Result residentAudit(@RequestBody ResidentAudit residentAudit){
        return businessService.businessResidentAudit(residentAudit);
    }

    /**
     * 注销业务
     *
     * @param businessId 业务标识
     * @return {@link Result}
     */
    @GetMapping("/logOffBusiness/{businessId}")
    public Result logOffBusiness(@PathVariable String businessId){
        return businessService.logOffBusinessById(businessId);
    }

    /**
     * 获得业务
     *
     * @param businessShopName 企业字号
     * @return {@link Business}
     */
    @GetMapping("/getBusinessByName")
    public Business getBusiness(@RequestParam(value = "businessShopName",required = true) String businessShopName){
        return businessService.getBusinessByName(businessShopName);
    }

    /**
     * 通过id获取业务
     *
     * @param businessId 业务标识
     * @return {@link Business}
     */
    @GetMapping("/getBusinessById")
    public Business getBusinessById(@RequestParam(value = "businessId",required = true) String businessId){
        return businessService.getBusinessById(businessId);
    }

    /**
     * 获得业务名字列表
     *
     * @param businessName 业务名称
     * @return {@link Result}
     */
    @GetMapping("/getBusinessNameList")
    public Result getBusinessByNameList(@RequestParam(value = "businessName",required = false) String businessName){
        return businessService.getBusinessNameList(businessName);
    }

    /**
     * 业务由业务id
     *
     * @param businessId 业务标识
     * @return {@link Result}
     */
    @GetMapping("/getBusinessById/{businessId}")
    public Result getBusinessByBusinessId(@PathVariable String businessId){
        return businessService.getBusinessByBusinessId(businessId);
    }

    /**
     * 得到所有业务
     *
     * @return {@link List}<{@link Business}>
     */
    @GetMapping("/getAllBusiness")
    public List<Business> getAllBusiness() {
        return businessService.getAllBusiness();
    }

}
