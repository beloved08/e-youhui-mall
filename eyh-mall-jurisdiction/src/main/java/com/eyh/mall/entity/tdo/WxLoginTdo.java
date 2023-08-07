package com.eyh.mall.entity.tdo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李平
 * @Date 2023-3-5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxLoginTdo {

    private String code;
    private String rawData;
    private String signature;

}
