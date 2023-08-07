package com.eyh.mall;

import com.eyh.mall.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class EyhMallUserApplicationTests {

    @Autowired
    private AdminService adminService;

    @Test
    void contextLoads() {
        // System.out.println(adminService.selectAdminOne("merchant_admin_1"));

        for (int i = 0; i < 14; i++) {
            System.out.println(UUID.randomUUID().toString());
        }
    }

}
