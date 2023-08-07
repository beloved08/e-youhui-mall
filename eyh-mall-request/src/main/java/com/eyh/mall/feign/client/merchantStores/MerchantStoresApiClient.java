package com.eyh.mall.feign.client.merchantStores;

import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.merchantStores.ShopLogo;
import com.eyh.mall.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author 李平
 * @Date 2023-3-10
 */
@FeignClient(value = "eyh-mall-aliyun", configuration = FeignConfig.class)
public interface MerchantStoresApiClient {

    /**
     * 上传标志
     *
     * @param dateDirPath 日期dir路径
     * @param pinyinName  拼音名字
     * @param newFileName 新文件名字
     * @return {@link Result}
     */
    @GetMapping("/aliyun/uploadLogo")
    Result uploadLogo(@RequestParam(value = "dateDirPath") String dateDirPath,
                      @RequestParam(value = "pinyinName") String pinyinName,
                      @RequestParam(value = "newFileName") String newFileName,
                      @RequestParam(value = "objectName") String objectName);

    /**
     * 得到ossurl
     * 获取阿里云oss文件URL
     *
     * @param category 类别
     * @param fileName 文件名称
     * @return String
     */
    @GetMapping("/aliyun/getUrl")
    String getOSSUrl(@RequestParam(value = "category",required = true) String category,
                     @RequestParam(value = "fileName", required = true) String fileName,
                     @RequestParam(value = "objectName", required = true)String objectName);

    /**
     * 是存在
     * 判断文件是否存在
     *
     * @param objectName 对象名称
     * @param name       名字
     * @return {@link Boolean}
     */
    @GetMapping("/aliyun/isExistence")
    Boolean isExistence(@RequestParam(value = "name", required = true) String name,
                        @RequestParam(value = "objectName", required = true) String objectName);

    /**
     * 删除
     *
     * @param prefixName 前缀名字
     */
    @GetMapping("/aliyun/deleteFile")
    void delete(@RequestParam(value = "prefixName", required = true) String prefixName);

}
