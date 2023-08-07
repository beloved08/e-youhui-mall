package com.eyh.mall.entity.vo;

import com.eyh.mall.feign.entity.MallUser;
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
public class WxLoginUserVo extends MallUser {

    private String accessToken;
    private String expiresIn;
    /**
     * 是否绑定手机
     * 0：未绑定，1：已绑定
     */
    private Integer isBindPhone;

}
