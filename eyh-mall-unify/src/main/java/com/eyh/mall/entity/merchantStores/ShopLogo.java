package com.eyh.mall.entity.merchantStores;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李平
 * @Date 2023-3-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopLogo {

    private String dateDirPath;
    private String pinyinName;
    private String newFileName;
    private String localFilePath;

}
