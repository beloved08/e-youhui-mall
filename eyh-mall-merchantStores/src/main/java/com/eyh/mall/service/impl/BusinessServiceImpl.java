package com.eyh.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.entity.MerchantSettlementChannelData;
import com.eyh.mall.entity.MerchantStorePage;
import com.eyh.mall.entity.MerchantStores;
import com.eyh.mall.entity.ResidentAudit;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.merchantStores.Business;
import com.eyh.mall.entity.merchantStores.BusinessUser;
import com.eyh.mall.entity.redis.MerchantStoreConstant;
import com.eyh.mall.entity.sms.SmsData;
import com.eyh.mall.feign.client.merchantStores.MerchantStoresApiClient;
import com.eyh.mall.goeasy.GoEasyUtil;
import com.eyh.mall.mapper.BusinessMapper;
import com.eyh.mall.mapper.BusinessUserMapper;
import com.eyh.mall.rabbitmq.ShopMallUserConstant;
import com.eyh.mall.redis.RedisUtil;
import com.eyh.mall.service.BusinessService;
import com.eyh.mall.util.FileUtil;
import com.eyh.mall.util.HanyuPinyinUtil;
import com.eyh.mall.util.TimeUtil;
import com.eyh.mall.util.json.JsonUtil;
import io.goeasy.GoEasy;
import io.goeasy.publish.GoEasyError;
import io.goeasy.publish.PublishListener;
import org.apache.commons.io.FilenameUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 业务服务impl
 *
 * @author 李平
 * @Date 2023-2-18
 */
@Service
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, Business> implements BusinessService {

    @Autowired
    private BusinessMapper businessMapper;

    @Autowired
    private BusinessUserMapper businessUserMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JsonUtil<BusinessUser> jsonUtil;

    @Autowired
    private JsonUtil<Business> businessJsonUtil;

    @Autowired
    private JsonUtil<MerchantStores> merchantStoresJsonUtil;

    @Autowired
    private MerchantStoresApiClient merchantStoresApiClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 添加商户店铺
     * 商家入驻
     *
     * @param merchantStores 商人商店
     * @return Result
     */
    @Override
    public Result addMerchantStores(MerchantStores merchantStores) {
        // 校验参数
        if (merchantStores == null) {
            return Result.err("参数为空");
        }

        String key = MerchantStoreConstant.BUSINESS_SHOP_NAME
                + merchantStores.getBusinessName()
                + merchantStores.getShopName();

        // 判断该商家店铺是否已入驻过
        Business business = selectBusiness(
                merchantStores.getBusinessName(),
                merchantStores.getShopName(),
                key,
                MerchantStoreConstant.BUSINESS_SHOP_TTL);

        if (business != null) {
            // 入驻过
            return Result.err("该商家店铺已入驻");
        }

        //入驻，封装数据
        int markerId = (int)((Math.random()*9+1)*100000);
        String businessId = UUID.randomUUID().toString();
        String iconPath = "https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/location/icon/location.png";
        //商家状态(0：正常，1：已注销，2：未审核，3：已审核，4：信息有误)
        Integer businessStatus = 2;
        //管理员审核状态(0：审核通过，1：审核不通过，2：审核信息错误，3：待审核)
        Integer verifyStatus = 3;

        // 将商家店铺信息存入数据库
        businessMapper.insert(new Business(
                UUID.randomUUID().toString(),
                businessId,
                merchantStores.getUserId(),
                merchantStores.getBusinessName(),
                merchantStores.getBusinessPhone(),
                merchantStores.getBusinessCity(),
                merchantStores.getBusinessLogo(),
                merchantStores.getBusinessDescribe(),
                iconPath,
                markerId,
                merchantStores.getBusinessName() + "-" + merchantStores.getShopName(),
                merchantStores.getBusinessDetailAddress(),
                merchantStores.getShopName(),
                merchantStores.getBusinessCityLatitude(),
                merchantStores.getBusinessCityLongitude(),
                merchantStores.getBusinessDetailAddressLatitude(),
                merchantStores.getBusinessDetailAddressLongitude(),
                businessStatus,
                verifyStatus,
                ""
                ));

        businessUserMapper.insert(new BusinessUser(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                merchantStores.getBusinessUserName(),
                merchantStores.getBusinessUserIdCard(),
                merchantStores.getBusinessPhone(),
                merchantStores.getBusinessUserEmail(),
                merchantStores.getBusinessUserSex(),
                merchantStores.getBusinessUserAge(),
                businessId
        ));

        merchantStores.setIconPath(iconPath);
        merchantStores.setBusinessStatus(businessStatus);
        merchantStores.setVerifyStatus(verifyStatus);

        //封装数据
        MerchantSettlementChannelData data = new MerchantSettlementChannelData();
        data.setTitle("商家店铺入驻申请");
        data.setBusinessName(merchantStores.getBusinessName());
        data.setShopName(merchantStores.getShopName());
        data.setShopAvatar(merchantStores.getBusinessLogo());
        data.setCurrentTime(TimeUtil.getCurrentTime());

        GoEasyUtil.messagePush("merchant_settlement_channel",data);
        GoEasyUtil.messagePush("business_channel",data);

        return Result.ok("商家店铺入驻申请成功",merchantStores);
    }

    /**
     * 上传标志
     * 上传图片至服务器和OSS
     *
     * @param file 文件
     * @param name 名字
     * @return Result
     */
    @Override
    public Result uploadLogo(MultipartFile file, String name) {
        try {
            //获取文件后缀
            String extension = "." + FilenameUtils.getExtension(file.getOriginalFilename());

            //生成新的文件名称
            String newFileName = new SimpleDateFormat("mmss").format(new Date()) +
                    UUID.randomUUID().toString().replace("-", "") + extension;

            //目录
            ApplicationHome home = new ApplicationHome(getClass());
            File source = home.getSource();
            String s1 = source.getParentFile().toString() + "/shop_logo/";

            String dirPath = "/" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "/" + HanyuPinyinUtil.toHanyuPinyin(name);
            String dateDirPath = s1 + dirPath;
            File dateDir = new File(dateDirPath);
            if (!dateDir.exists()) {
                dateDir.mkdirs();
            }

            // 文件上传到服务器
            file.transferTo(new File(dateDir, newFileName));
            String pinyinName = "shop_logo/" + HanyuPinyinUtil.toHanyuPinyin(name);

            // 将图片上传至OSS存储服务中
            // ShopLogo shopLogo = new ShopLogo(
            //         dateDirPath,
            //         pinyinName,
            //         newFileName,
            //         s1);

            merchantStoresApiClient.uploadLogo(dateDirPath,pinyinName,newFileName,"wx");
            // 发送消息
            // rabbitTemplate.convertAndSend(ShopLogoConstant.SHOP_LOGO_QUEUE,JSON.toJSONString(shopLogo));
            // https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/shop_logo/biaodianfuhaogeVSdongruoguanhuo/4341d0c007457ea04a629b44adcb2ad88ebf.png
            Boolean existence = merchantStoresApiClient.isExistence(pinyinName + "/" + newFileName,"wx");
            if (existence) {
                // 上传到oss成功
                // 删除服务器文件
                FileUtil.deleteFile(new File(s1));
                // 获取商家图片URL
                String ossUrl = merchantStoresApiClient.getOSSUrl(pinyinName, newFileName,"wx");
                return Result.ok(ossUrl);
            }else{
                return Result.err("店铺logo上传到oss错误");
            }
            // return Result.ok("店铺logo上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.err("店铺logo上传错误");
        }
    }

    /**
     * 选择业务用户id
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @Override
    public Result selectBusinessByUserId(String userId) {
        String key = MerchantStoreConstant.MERCHANT_STORES_LIST + userId;
        if (!redisUtil.hasKey(key)){
            return Result.ok(getMerchantStoresList(key,MerchantStoreConstant.MERCHANT_STORES_LIST_TTL,userId));
        }
        List<MerchantStores> merchantStores = merchantStoresJsonUtil.toListBean(redisUtil.lGet(key, 0, -1),MerchantStores.class);
        if (merchantStores.size() == 0) {
            return Result.ok(getMerchantStoresList(key,MerchantStoreConstant.MERCHANT_STORES_LIST_TTL,userId));
        }
        return Result.ok(merchantStores);
    }

    /**
     * 选择页面
     *
     * @param merchantStorePage 商人商店页面
     * @return {@link Result}
     */
    @Override
    public Result selectByPage(MerchantStorePage merchantStorePage) {
        IPage<Business> page = new Page<Business>(merchantStorePage.getCurrentPage(), merchantStorePage.getPageSize());
        LambdaQueryWrapper<Business> lqw = new LambdaQueryWrapper<>();

        // 条件查询
        lqw.like(!"".equals(merchantStorePage.getBusinessName()),Business::getBusinessName,merchantStorePage.getBusinessName());
        lqw.like(!"".equals(merchantStorePage.getShopName()),Business::getShopName,merchantStorePage.getShopName());
        lqw.eq(merchantStorePage.getBusinessStatus() != null,Business::getBusinessStatus,merchantStorePage.getBusinessStatus());
        lqw.eq(merchantStorePage.getVerifyStatus() != null,Business::getVerifyStatus,merchantStorePage.getVerifyStatus());

        businessMapper.selectPage(page,lqw);
        return Result.ok(page);
    }

    /**
     * 选择业务用户id
     *
     * @param businessId 业务标识
     * @return {@link Result}
     */
    @Override
    public Result selectBusinessUserById(String businessId) {
        String key = MerchantStoreConstant.BUSINESS_USER_ID + businessId;
        if (!redisUtil.hasKey(key)){
            BusinessUser byBusinessId = getByBusinessId(businessId, key, MerchantStoreConstant.BUSINESS_USER_ID_TTL);
            return byBusinessId != null ? Result.ok(byBusinessId) : Result.err("数据为空");
        }
        BusinessUser businessUser = jsonUtil.toJavaBean(redisUtil.get(key), BusinessUser.class);
        if (businessUser == null){
            BusinessUser byBusinessId = getByBusinessId(businessId, key, MerchantStoreConstant.BUSINESS_USER_ID_TTL);
            return byBusinessId != null ? Result.ok(byBusinessId) : Result.err("数据为空");
        }
        return Result.ok(businessUser);
    }

    /**
     * 居民业务审计
     *
     * @param residentAudit 居民审计
     * @return {@link Result}
     */
    @Override
    public Result businessResidentAudit(ResidentAudit residentAudit) {
        String key = MerchantStoreConstant.VERIFY_BUSINESS_ID + residentAudit.getBusinessId();
        Business business = businessJsonUtil.toJavaBean(redisUtil.get(key), Business.class);

        MerchantSettlementChannelData data = new MerchantSettlementChannelData();

        if (business != null){
            business.setBusinessStatus(residentAudit.getBusinessStatus());
            business.setVerifyStatus(residentAudit.getVerifyStatus());
            business.setVerifyDesc(residentAudit.getVerifyDesc());
            businessMapper.updateById(business);

            data.setBusinessName(business.getBusinessName());
            data.setShopName(business.getShopName());
            data.setShopAvatar(business.getBusinessLogo());

        }else{
            Business b = selectByBusinessId(residentAudit.getBusinessId(),key,MerchantStoreConstant.VERIFY_BUSINESS_ID_TTL);
            b.setBusinessStatus(residentAudit.getBusinessStatus());
            b.setVerifyStatus(residentAudit.getVerifyStatus());
            b.setVerifyDesc(residentAudit.getVerifyDesc());
            businessMapper.updateById(b);

            data.setBusinessName(b.getBusinessName());
            data.setShopName(b.getShopName());
            data.setShopAvatar(b.getBusinessLogo());
        }

        if(residentAudit.getVerifyStatus() == 0){
            SmsData smsData = new SmsData();
            smsData.setPhone(residentAudit.getBusinessUserPhone());
            smsData.setTemplate("SMS_273795810");
            // 审核通过
            rabbitTemplate.convertAndSend(ShopMallUserConstant.VERIFY_BUSINESS_QUEUE,JSON.toJSONString(smsData));
            data.setMsg("入驻申请审核已通过，查看具体情况");

        }else if(residentAudit.getVerifyStatus() == 1 || residentAudit.getVerifyStatus() == 2){
            // 审核不通过
            SmsData smsData = new SmsData();
            smsData.setPhone(residentAudit.getBusinessUserPhone());
            smsData.setTemplate("SMS_273775728");
            rabbitTemplate.convertAndSend(ShopMallUserConstant.VERIFY_BUSINESS_QUEUE,JSON.toJSONString(smsData));
            data.setMsg("入驻申请审核不通过，查看具体情况");
        }
        data.setCurrentTime(TimeUtil.getCurrentTime());
        data.setTitle("商家店铺入驻审核结果");
        GoEasyUtil.messagePush("business_channel",data);

        // 调用阿里云发送审核通过消息
        return Result.ok("审核操作成功");
    }

    /**
     * 通过身份证注销业务
     *
     * @param businessId 业务标识
     * @return {@link Result}
     */
    @Override
    public Result logOffBusinessById(String businessId) {
        LambdaQueryWrapper<Business> businessLambdaQueryWrapper = new LambdaQueryWrapper<>();

        businessLambdaQueryWrapper.eq(!"".equals(businessId),Business::getBusinessId,businessId);
        Business business = businessMapper.selectOne(businessLambdaQueryWrapper);

        if(business.getBusinessStatus() == 1){
            return Result.err("该商家已注销");
        }
        business.setBusinessStatus(1);
        int i = businessMapper.updateById(business);
        return i > 0 ? Result.ok("注销成功") : Result.err("注销失败");
    }

    /**
     * 得到企业名字
     *
     * @param businessShopName 企业字号
     * @return {@link Business}
     */
    @Override
    public Business getBusinessByName(String businessShopName) {
        String key = MerchantStoreConstant.BUSINESS_SHOP_NAME + businessShopName;
        Business business = businessJsonUtil.toJavaBean(redisUtil.get(key), Business.class);
        if (business != null) {
            return business;
        }
        if (!"".equals(businessShopName)){
            return selectByBusinessShopName(
                    businessShopName,
                    key,
                    MerchantStoreConstant.BUSINESS_SHOP_TTL);
        }
        return null;
    }

    /**
     * 获得商业名称列表
     *
     * @param businessName 业务名称
     * @return {@link Result}
     */
    @Override
    public Result getBusinessNameList(String businessName) {
        //获取所有`管理员审核通过`、`商家状态正常`的商家名称集合
        LambdaQueryWrapper<Business> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(!"".equals(businessName),Business::getCalloutContent,businessName);
        wrapper.eq(true,Business::getBusinessStatus,0);
        wrapper.eq(true,Business::getVerifyStatus,0);

        List<Business> b = businessMapper.selectList(wrapper);
        if (b.size() > 0){
            List<String> contentList = new ArrayList<>();
            for (Business business : b){
                contentList.add(business.getCalloutContent());
            }
            return Result.ok(contentList);
        }else {
            return Result.err("商家名称获取失败");
        }


    }

    /**
     * 通过id获取业务
     *
     * @param businessId 业务标识
     * @return {@link Business}
     */
    @Override
    public Business getBusinessById(String businessId) {
        String key = MerchantStoreConstant.BUSINESS_INFO_ID +businessId;
        Object o = redisUtil.get(key);
        if (o != null){
            return businessJsonUtil.toJavaBean(o, Business.class);
        }
        //查询数据库
        Business business = selectByBusinessId(businessId, key, MerchantStoreConstant.BUSINESS_INFO_ID_TTL);
        if (business == null){
            // 数据库为空
            return null;
        }
        return business;
    }

    /**
     * 业务由业务id
     *
     * @param businessId 业务标识
     * @return {@link Result}
     */
    @Override
    public Result getBusinessByBusinessId(String businessId) {
        return Result.ok(this.getBusinessById(businessId));
    }

    /**
     * 得到总业务
     *
     * @return {@link Result}
     */
    @Override
    public Result getTotalBusiness() {
        LambdaQueryWrapper<Business> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(true,Business::getVerifyStatus,0);
        wrapper.eq(true,Business::getBusinessStatus,0);
        return Result.ok(businessMapper.selectCount(wrapper));
    }

    /**
     * 得到所有商业地图
     *
     * @return {@link Result}
     */
    @Override
    public Result getAllBusinessMap() {
        LambdaQueryWrapper<Business> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(true,Business::getBusinessStatus,0);
        wrapper.eq(true,Business::getVerifyStatus,0);
        return Result.ok(businessMapper.selectList(wrapper));
    }

    /**
     * 得到所有业务
     *
     * @return {@link List}<{@link Business}>
     */
    @Override
    public List<Business> getAllBusiness() {
        LambdaQueryWrapper<Business> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(true,Business::getBusinessStatus,0);
        wrapper.eq(true,Business::getVerifyStatus,0);
        return businessMapper.selectList(wrapper);
    }

    /**
     * 选择由业务标识
     * 通过业务标识
     *
     * @param businessId 业务标识
     * @param key        关键
     * @param ttl        ttl
     * @return {@link Business}
     */
    Business selectByBusinessId(String businessId,String key,long ttl){
        LambdaQueryWrapper<Business> businessLambdaQueryWrapper = new LambdaQueryWrapper<>();

        businessLambdaQueryWrapper.eq(!"".equals(businessId),Business::getBusinessId,businessId);
        Business business = businessMapper.selectOne(businessLambdaQueryWrapper);

        if (business == null){
            return null;
        }

        redisUtil.set(key,JSON.toJSONString(business),ttl);
        return business;
    }

    /**
     * 通过业务标识
     *
     * @param businessId 业务标识
     * @param key        关键
     * @param ttl        ttl
     * @return {@link BusinessUser}
     */
    BusinessUser getByBusinessId(String businessId,String key, long ttl){
        LambdaQueryWrapper<BusinessUser> businessUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        businessUserLambdaQueryWrapper.eq(!"".equals(businessId),BusinessUser::getBusinessId,businessId);
        BusinessUser businessUser = businessUserMapper.selectOne(businessUserLambdaQueryWrapper);

        if (businessUser == null){
            return null;
        }
        redisUtil.set(key,JSON.toJSONString(businessUser),ttl);
        return businessUser;
    }

    /**
     * 让商家店铺列表
     *
     * @param userId 用户id
     * @return {@link List}<{@link MerchantStores}>
     */
    List<MerchantStores> getMerchantStoresList(String key, long ttl,String userId){
        List<MerchantStores> merchantStoresList = new ArrayList<>();

        List<Business> businessList = getBusinessByUserId(userId);
        if (businessList.size() == 0){
            return null;
        }
        for (Business business : businessList){
            BusinessUser businessUser = getBusinessUserById(business.getBusinessId());
            if (businessUser == null){
                return null;
            }
            MerchantStores stores = new MerchantStores(
                    businessUser.getBusinessUserId(),
                    businessUser.getBusinessUserName(),
                    businessUser.getBusinessUserIdCard(),
                    businessUser.getBusinessUserPhone(),
                    businessUser.getBusinessUserEmail(),
                    businessUser.getBusinessUserSex(),
                    businessUser.getBusinessUserAge(),
                    business.getBusinessName(),
                    business.getShopName(),
                    business.getBusinessPhone(),
                    business.getBusinessCity(),
                    business.getBusinessDescribe(),
                    business.getBusinessDetailAddress(),
                    business.getBusinessCityLatitude(),
                    business.getBusinessCityLongitude(),
                    business.getBusinessLogo(),
                    business.getBusinessDetailAddressLatitude(),
                    business.getBusinessDetailAddressLongitude(),
                    business.getIconPath(),
                    business.getBusinessStatus(),
                    business.getVerifyStatus(),
                    business.getMarkerId(),
                    business.getCalloutContent()
            );
            merchantStoresList.add(stores);
        }
        redisUtil.lSet(key,merchantStoresList,ttl);
        return merchantStoresList;
    }

    /**
     * 让业务用户id
     *
     * @param userId 用户id
     * @return {@link List}<{@link Business}>
     */
    List<Business> getBusinessByUserId(String userId){
        LambdaQueryWrapper<Business> businessLambdaQueryWrapper = new LambdaQueryWrapper<>();
        businessLambdaQueryWrapper.eq(userId != null && !"".equals(userId),Business::getUserId,userId);
        return businessMapper.selectList(businessLambdaQueryWrapper);
    }

    /**
     * 让业务用户id
     *
     * @param businessId 业务标识
     * @return {@link BusinessUser}
     */
    BusinessUser getBusinessUserById(String businessId){
        LambdaQueryWrapper<BusinessUser> businessUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        businessUserLambdaQueryWrapper.eq(businessId != null && !"".equals(businessId),BusinessUser::getBusinessId,businessId);
        return businessUserMapper.selectOne(businessUserLambdaQueryWrapper);
    }

    /**
     * 选择业务
     * 根据商家名称和店铺名称查询
     *
     * @param businessName 业务名称
     * @param shopName     商店名字
     * @param key          关键
     * @param ttl          ttl
     * @return Business
     */
    Business selectBusiness(String businessName, String shopName, String key, Long ttl) {
        LambdaQueryWrapper<Business> businessLambdaQueryWrapper = new LambdaQueryWrapper<>();
        businessLambdaQueryWrapper.eq(businessName != null && !"".equals(businessName), Business::getBusinessName, businessName);
        businessLambdaQueryWrapper.eq(shopName != null && !"".equals(shopName), Business::getShopName, shopName);

        Business business = businessMapper.selectOne(businessLambdaQueryWrapper);
        if (business == null) {
            return null;
        }
        // 存入Redis
        redisUtil.set(key, JSON.toJSONString(business), ttl);
        return business;
    }

    /**
     * 由业务字号选择
     *
     * @param businessShopName 企业字号
     * @param key              关键
     * @param ttl              ttl
     * @return {@link Business}
     */
    Business selectByBusinessShopName(String businessShopName, String key, Long ttl) {
        LambdaQueryWrapper<Business> businessLambdaQueryWrapper = new LambdaQueryWrapper<>();
        businessLambdaQueryWrapper.eq(businessShopName != null && !"".equals(businessShopName),
                Business::getCalloutContent, businessShopName);

        Business business = businessMapper.selectOne(businessLambdaQueryWrapper);
        if (business == null) {
            return null;
        }
        // 存入Redis
        redisUtil.set(key, JSON.toJSONString(business), ttl);
        return business;
    }

}
