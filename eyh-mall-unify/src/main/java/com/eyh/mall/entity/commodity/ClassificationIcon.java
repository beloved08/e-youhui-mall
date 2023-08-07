package com.eyh.mall.entity.commodity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME ClassificationIcon
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期六
 * @Date 2023-03-18 10:46:44
 * @Description 商品分类图片RabbitMQ信息封装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassificationIcon {

    /**
     * 分类id
     */
    private String classificationId;
    /**
     * 日期dir路径
     */
    private String dateDirPath;
    /**
     * 拼音名字
     */
    private String pinyinName;
    /**
     * 新文件名字
     */
    private String newFileName;
    /**
     * 本地文件路径
     */
    private String localFilePath;

}
