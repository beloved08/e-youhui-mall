package com.eyh.mall.entity.sms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李平
 * @Date 2023-3-8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsData {

    private String phone;
    private String template;
    private String key;
    private Long ttl;

}
