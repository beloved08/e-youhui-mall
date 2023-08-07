package com.eyh.mall.util;

import java.io.File;

/**
 * 文件工具类
 * @author 李平
 */
public class FileUtil {

    /**
     * 删除文件及文件夹
     * @param file
     * @return
     */
    public static Boolean deleteFile(File file) {
        //判断文件不为null或文件目录存在
        if (file == null || !file.exists()) {
            return false;
        }
        //获取目录下子文件
        File[] files = file.listFiles();
        //遍历该目录下的文件对象
        for (File f : files) {
            //判断子目录是否存在子目录,如果是文件则删除
            if (f.isDirectory()) {
                //递归删除目录下的文件
                deleteFile(f);
            } else {
                //文件删除
                f.delete();
            }
        }
        //文件夹删除
        file.delete();
        return true;
    }

}
