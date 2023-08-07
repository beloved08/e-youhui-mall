package com.eyh.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME ResidentAudit
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期四
 * @Date 2023-03-16 11:44:53
 * @Description 商家店铺审核信息封装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResidentAudit {

    /**
     * 业务标识
     */
    private String businessId;
    /**
     * 业务状况
     */
    private Integer businessStatus;
    /**
     * 业务用户电话
     */
    private String businessUserPhone;
    /**
     * 验证状态
     */
    private Integer verifyStatus;
    /**
     * 验证desc
     */
    private String verifyDesc;
}
