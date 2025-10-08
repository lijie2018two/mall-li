package com.ruoyi.file.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.ruoyi.common.core.utils.uuid.UUID;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@ConfigurationProperties(prefix = "qiniu")
@Data
public class QiniuCloudUploadUtil {

    private String accessKey;

    private String secretKey;

    private String bucketName;

    private String region;

    private String defaultDomain;

    /**
     * 上传 MultipartFile 类型的文件到七牛云
     * @param file 要上传的 MultipartFile 文件
     * @return 上传成功返回文件链接，失败返回 null
     */
    public String uploadFile(MultipartFile file) {
        // 生成随机文件名
        String randomName = generateRandomFileName(file.getOriginalFilename());

        // ...
        // 根据配置的区域创建配置类
        Configuration cfg = new Configuration(getRegion(region));
        // 创建上传管理器
        UploadManager uploadManager = new UploadManager(cfg);
        // 生成上传凭证
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucketName);

        try {
            byte[] fileBytes = file.getBytes();
            // 调用上传方法
            Response response = uploadManager.put(fileBytes, randomName, upToken);
            // 解析上传结果
            if (response.isOK()) {
                // 获取默认测试域名
                return  defaultDomain + "/" + randomName; // 拼接文件链接
            }
        } catch (IOException ex) {
            if (ex instanceof QiniuException) {
                Response r = ((QiniuException) ex).response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            } else {
                System.err.println("读取文件字节流时出错: " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     * 上传 MultipartFile 类型的文件到七牛云指定文件夹
     * @param file 要上传的 MultipartFile 文件
     * @param folder 上传到七牛云的文件夹路径，例如 "images/"
     * @param key 上传到七牛云后的文件名，如果为 null 则使用文件的哈希值作为文件名
     * @return 上传成功返回文件链接，失败返回 null
     */
    public String uploadVideoFile(MultipartFile file, String folder, String key) {
        // 如果key为空则生成随机文件名
        key = generateRandomFileName(file.getOriginalFilename());
        // 根据配置的区域创建配置类
        Configuration cfg = new Configuration(getRegion(region));
        // 创建上传管理器
        UploadManager uploadManager = new UploadManager(cfg);
        // 生成上传凭证
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucketName);

        try {
            byte[] fileBytes = file.getBytes();
            // 拼接包含文件夹路径的文件名
            String fullKey = folder + key;
            // 调用上传方法
            Response response = uploadManager.put(fileBytes, fullKey, upToken);
            // 解析上传结果
            if (response.isOK()) {
                // 获取默认测试域名
                return defaultDomain + "/" + fullKey; // 拼接文件链接
            }
        } catch (IOException ex) {
            if (ex instanceof QiniuException) {
                Response r = ((QiniuException) ex).response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            } else {
                System.err.println("读取文件字节流时出错: " + ex.getMessage());
            }
        }
        return null;
    }


    /**
     * 根据配置的区域字符串获取 Region 对象
     * @param regionStr 区域字符串，如 "region0"、"region1" 等
     * @return Region 对象
     */
    private Region getRegion(String regionStr) {
        switch (regionStr) {
            case "region0":
                return Region.region0();
            case "region1":
                return Region.region1();
            case "region2":
                return Region.region2();
            case "regionNa0":
                return Region.regionNa0();
            case "regionAs0":
                return Region.regionAs0();
            default:
                return Region.region0();
        }
    }


    // 在QiniuCloudUploadUtil类中添加生成随机文件名的方法
    private String generateRandomFileName(String originalFileName) {
        // 获取文件扩展名
        String extension = "";
        int dotIndex = originalFileName.lastIndexOf(".");
        if (dotIndex > 0) {
            extension = originalFileName.substring(dotIndex);
        }

        // 生成随机文件名：UUID + 时间戳
        return UUID.randomUUID().toString().replace("-", "")
                + "_" + System.currentTimeMillis()
                + extension;
    }

}