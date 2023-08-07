package com.eyh.mall.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.eyh.mall.service.OSSClientService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

/**
 * impl ossclient服务
 * 阿里云OSS对象存储业务接口实现类
 *
 * @author 李平
 * @Date 2023-1-31
 */
@Service
public class OSSClientServiceImpl implements OSSClientService {
    /**
     * yourEndpoint填写Bucket所在地域对应的Endpoint
     */
    private static final String ENDPOINT = "ENDPOINT";
    /**
     * 阿里云账号AccessKey
     */
    private static final String ACCESS_KEY_ID = "ACCESS_KEY_ID";
    /**
     * 阿里云账号AccessKeySecret
     */
    private static final String ACCESS_KEY_SECRET = "ACCESS_KEY_SECRET";
    /**
     * 填写Bucket名称-zwgGoodsImages
     */
    private static final String BUCKET_NAME = "eyouhui";

    /**
     * 上传图片
     * 表单上传文件
     *
     * @param localFilePath 本地文件路径
     * @param category      类别
     * @param fileName      文件名称
     * @return {@link String}
     */
    @Override
    public String uploadImage(String localFilePath, String category, String fileName, String objectName) {

        try{
            // 在URL中添加Bucket名称，添加后URL格式为http://yourBucketName.oss-cn-hangzhou.aliyuncs.com。
            String urlStr = ENDPOINT.replace("http://", "http://" + BUCKET_NAME + ".");
            // 设置表单Map。
            Map<String, String> formFields = new LinkedHashMap<String, String>();
            // 设置文件名称。
            formFields.put("key", objectName + "/" + category  + "/" + fileName);
            // 设置Content-Disposition。
            formFields.put("Content-Disposition", "attachment;filename=" + localFilePath);
            formFields.put("OSSAccessKeyId", ACCESS_KEY_ID);
            String policy = "{\"expiration\": \"2120-01-01T12:00:00.000Z\",\"conditions\": [[\"content-length-range\", 0, 104857600]]}";
            String encodePolicy = new String(Base64.encodeBase64(policy.getBytes()));
            // 设置policy。
            formFields.put("policy", encodePolicy);
            // 生成签名。
            String signaturecom = com.aliyun.oss.common.auth.ServiceSignature.create().computeSignature(ACCESS_KEY_SECRET, encodePolicy);
            // 设置签名。
            formFields.put("Signature", signaturecom);
            String ret = formUpload(urlStr, formFields, localFilePath + "/" + fileName);
            return ret;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 得到上传图像url
     * 获取OSS存储对象下载请求URL
     *
     * @param category   类别
     * @param fileName   文件名称
     * @param objectName 对象名称
     * @return {@link String}
     */
    @Override
    public String getUploadImageURL(String category, String fileName, String objectName) {
        return "https://" + BUCKET_NAME + ".oss-cn-hangzhou.aliyuncs.com/" + objectName + "/" + category + "/" + fileName;
    }

    /**
     * 是存在
     * 判断文件是否存在
     *
     * @param objectName 对象名称
     * @param name       名字
     * @return Boolean
     */
    @Override
    public Boolean isExistence(String name,String objectName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        try {
            boolean found = ossClient.doesObjectExist(BUCKET_NAME, objectName + "/" + name);
            return found;
        } catch (OSSException oe) {
            oe.printStackTrace();
            return false;
        } catch (ClientException ce) {
            ce.printStackTrace();
            return false;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     * 删除文件
     *
     * @param objectName 对象名称
     * @param name       名字
     */
    @Override
    public void deleteFile(String name,String objectName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        try {
            // 删除文件或目录。如果要删除目录，目录必须为空。
            ossClient.deleteObject(BUCKET_NAME, objectName + "/" + name);
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     * 删除目录文件
     *
     * @param prefixName 前缀名字
     */
    @Override
    public String deleteDirectoryFile(String prefixName) {
        // 如果您需要删除所有前缀为src的文件，则prefix设置为src。设置为src后，所有前缀为src的非目录文件、src目录以及目录下的所有文件均会被删除。
        // String prefix = "src";
        // 如果您仅需要删除src目录及目录下的所有文件，则prefix设置为src/。
        String prefix = prefixName + "/";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        try {
            // 列举所有包含指定前缀的文件并删除。
            String nextMarker = null;
            ObjectListing objectListing = null;
            do {
                ListObjectsRequest listObjectsRequest = new ListObjectsRequest(BUCKET_NAME)
                        .withPrefix(prefix)
                        .withMarker(nextMarker);

                objectListing = ossClient.listObjects(listObjectsRequest);
                if (objectListing.getObjectSummaries().size() > 0) {
                    List<String> keys = new ArrayList<String>();
                    for (OSSObjectSummary s : objectListing.getObjectSummaries()) {
                        keys.add(s.getKey());
                    }
                    DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(BUCKET_NAME).withKeys(keys).withEncodingType("url");
                    DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(deleteObjectsRequest);
                    List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
                    try {
                        for(String obj : deletedObjects) {
                            String deleteObj =  URLDecoder.decode(obj, "UTF-8");
                            System.out.println(deleteObj);
                        }

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                nextMarker = objectListing.getNextMarker();
                return nextMarker;
            } while (objectListing.isTruncated());
        } catch (OSSException | ClientException oe) {
            oe.printStackTrace();
            return null;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     * 表单上传
     * 表单上传文件业务
     *
     * @param urlStr     url str
     * @param formFields 表单字段
     * @param localFile  本地文件
     * @return String
     * @throws Exception 异常
     */
    private static String formUpload(String urlStr, Map<String, String> formFields, String localFile) throws Exception {
        String res = "";
        HttpURLConnection conn = null;
        String boundary = "9431149156168";
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            // 设置MD5值。MD5值由整个Body计算得出。如果希望开启MD5校验，可参考MD5加密设置。
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // 遍历读取表单Map中的数据，将数据写入到输出流中。
            if (formFields != null) {
                StringBuffer strBuf = new StringBuffer();
                Iterator<Map.Entry<String, String>> iter = formFields.entrySet().iterator();
                int i = 0;
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    String inputName = entry.getKey();
                    String inputValue = entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    if (i == 0) {
                        strBuf.append("--").append(boundary).append("\r\n");
                        strBuf.append("Content-Disposition: form-data; name=\""
                                + inputName + "\"\r\n\r\n");
                        strBuf.append(inputValue);
                    } else {
                        strBuf.append("\r\n").append("--").append(boundary).append("\r\n");
                        strBuf.append("Content-Disposition: form-data; name=\""
                                + inputName + "\"\r\n\r\n");
                        strBuf.append(inputValue);
                    }
                    i++;
                }
                out.write(strBuf.toString().getBytes());
            }
            // 读取文件信息，将要上传的文件写入到输出流中。
            File file = new File(localFile);
            String filename = file.getName();
            String contentType = new MimetypesFileTypeMap().getContentType(file);
            if (contentType == null || contentType.equals("")) {
                contentType = "application/octet-stream";
            }
            StringBuffer strBuf = new StringBuffer();
            strBuf.append("\r\n").append("--").append(boundary)
                    .append("\r\n");
            strBuf.append("Content-Disposition: form-data; name=\"file\"; "
                    + "filename=\"" + filename + "\"\r\n");
            strBuf.append("Content-Type: " + contentType + "\r\n\r\n");
            out.write(strBuf.toString().getBytes());
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            in.close();
            byte[] endData = ("\r\n--" + boundary + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();
            // 读取返回数据。
            strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = strBuf.toString();
            reader.close();
            reader = null;
        } catch (ClientException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }

}
