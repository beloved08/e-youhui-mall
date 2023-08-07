package com.eyh.mall;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class EyhMallLogisticsApplicationTests {

    @Test
    void contextLoads() {
        for (int i = 0; i < 14; i++) {
            System.out.println(UUID.randomUUID().toString());
        }
    }

}
