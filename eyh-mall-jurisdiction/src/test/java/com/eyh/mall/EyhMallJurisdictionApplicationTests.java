package com.eyh.mall;

import com.eyh.mall.service.MenuService;
import com.eyh.mall.util.TimeDateFormat;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.UUID;

@SpringBootTest
class EyhMallJurisdictionApplicationTests {

    @Autowired
    private MenuService menuService;
    @Test
    void contextLoads() {
        Md5Hash md5Hash = new Md5Hash("merchant-1@2023","slat",3);
        Md5Hash md5Hash2 = new Md5Hash("E-YouHui@2023","slat",3);
        System.out.println(md5Hash);
        System.out.println(md5Hash2);
        for (int i = 0 ; i < 46; i++){
            System.out.println(UUID.randomUUID());
        }

        // menuService.selectUserMenu("eyJleWgiOiJNQUxMIiwiYWxnIjoiSFM1MTIifQ.eyJzdWIiOiJ7XCJsYXN0QWNjZXNzVGltZVwiOlwiMjAyMy0wMi0xNyAxMTowODozMVwiLFwic2Vzc2lvbklkXCI6XCIzRDk3MkI0QUQ0NjRERTA1NEMwQzVFODg5NjAwRDAzN1wiLFwidXNlck5hbWVcIjpcImFkbWluXCIsXCJ1c2VyUm9sZVwiOlwiYWRtaW5cIn0iLCJpYXQiOjE2NzY2MDMzMTcsImV4cCI6MTY3NjYwNjkxN30.I-9J__sFHarLMFkXAK2diGcRvj4zEBlsnf6PlrDswl-708kosXVgZdOHcL6NNdQu3_EsdVQsWf6zKhPOIdTMbQ");
    }

}
