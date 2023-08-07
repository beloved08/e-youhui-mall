package com.eyh.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 李平
 * @NAME OSSImageData
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期六
 * @Date 2023-03-18 15:27:57
 * @Description 阿里云oss上传数据封装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OSSImageData {

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

    /**
     * 对象名称
     */
    private String objectName;

}
