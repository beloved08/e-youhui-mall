package com.eyh.mall.service;

/**
 * 阿里云OSS业务接口类
 *
 * @author 李平
 * @Date 2023-3-11
 */
public interface OSSClientService {

    /**
     * 表单上传图片
     * @return String
     */
    String uploadImage(String localFilePath, String category, String fileName,String objectName);

    /**
     * 获取上传的图片的URL
     * @return String
     */
    String getUploadImageURL(String category, String fileName,String objectName);

    /**
     * 是存在
     * 判断文件是否存在
     *
     * @param name       名字
     * @param objectName 对象名称
     * @return Boolean
     */
    Boolean isExistence(String name,String objectName);

    /**
     * 删除文件
     *
     * @param name       名字
     * @param objectName 对象名称
     */
    void deleteFile(String name,String objectName);

    /**
     * 删除目录文件
     *
     * @param prefixName 前缀名字
     */
    String deleteDirectoryFile(String prefixName);

}
