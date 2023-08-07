package com.eyh.mall.entity.tdo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME UserAddressDetailDto
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期一
 * @Date 2023-03-27 17:21:41
 * @Description 用户收收货地址
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressDetailDto {

    /**
     * id
     */
    private String id;
    /**
     * 地址标识
     */
    private String addressId;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 收货人
     */
    private String consignee;
    /**
     * 电话
     */
    private String phone;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 是默认0:默认，1：不默认
     */
    private Integer isDefault;
    /**
     * 位置
     */
    private String location;
    /**
     * 详细地址
     */
    private String detailedAddress;

}
