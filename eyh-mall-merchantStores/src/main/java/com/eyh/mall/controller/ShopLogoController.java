package com.eyh.mall.controller;

import com.eyh.mall.entity.common.Result;
import com.eyh.mall.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 李平
 * @Date 2023-3-11
 */
@RestController
@RequestMapping("/merchantStores/wx")
public class ShopLogoController {

    @Autowired
    private BusinessService businessService;

    /**
     * 上传图片至服务器和OSS
     * @param file
     * @param name
     * @return
     */
    @PostMapping(value = "/uploadLogo/{name}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result uploadShopLogo(@RequestParam("shopLogo") MultipartFile file, @PathVariable String name) {
        return businessService.uploadLogo(file,name);
    }

}
