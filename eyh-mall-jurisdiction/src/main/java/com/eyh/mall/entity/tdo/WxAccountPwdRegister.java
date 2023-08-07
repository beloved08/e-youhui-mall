package com.eyh.mall.entity.tdo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李平
 * @Date 2023-3-6
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxAccountPwdRegister {

    private String phone;
    private String password;
    private String openid;

}
