package com.eyh.mall.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李平
 * @Date 2023-1-31
 */
@RestController
@RequestMapping("/aliyun")
public class TestController {

    @Value("${eyh.mall.aliyun}")
    private String aliyun;

    @GetMapping("/test")
    public String test(){
        return aliyun;
    }
}
