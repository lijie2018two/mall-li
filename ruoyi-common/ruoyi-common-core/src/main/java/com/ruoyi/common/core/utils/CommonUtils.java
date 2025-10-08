package com.ruoyi.common.core.utils;


import com.ruoyi.common.core.constant.CommonConstant;
import com.ruoyi.common.core.constant.SymbolConstant;
import com.ruoyi.common.core.utils.oss.OssBootUtil;
import com.ruoyi.common.core.utils.oss.filter.FileTypeFilter;
import lombok.extern.slf4j.Slf4j;
import org.jeecgframework.poi.util.PoiPublicUtil;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 通用工具
 * @author: jeecg-boot
 */
@Slf4j
public class CommonUtils {

    public static final long DEFAULT_MAX_SIZE = 10 * 1024 * 1024L;

    /**
     * 中文正则
     */
    private static Pattern ZHONGWEN_PATTERN = Pattern.compile("[\u4e00-\u9fa5]");

    /**
     * 文件名 正则字符串
     * 文件名支持的字符串：字母数字中文.-_()（） 除此之外的字符将被删除
     */
    private static String FILE_NAME_REGEX = "[^A-Za-z\\.\\(\\)\\-（）\\_0-9\\u4e00-\\u9fa5]";

    public static String uploadOnlineImage(byte[] data,String basePath,String bizPath,String uploadType){
        String dbPath = null;
        String fileName = "image" + Math.round(Math.random() * 100000000000L);
        fileName += "." + PoiPublicUtil.getFileExtendName(data);
        try {
            if(CommonConstant.UPLOAD_TYPE_LOCAL.equals(uploadType)){
                File file = new File(basePath + File.separator + bizPath + File.separator );
                if (!file.exists()) {
                    file.mkdirs();// 创建文件根目录
                }
                String savePath = file.getPath() + File.separator + fileName;
                File savefile = new File(savePath);
                FileCopyUtils.copy(data, savefile);
                dbPath = bizPath + File.separator + fileName;
            }else {
                InputStream in = new ByteArrayInputStream(data);
                String relativePath = bizPath+"/"+fileName;
                if(CommonConstant.UPLOAD_TYPE_MINIO.equals(uploadType)){
                    dbPath = OssBootUtil.upload(in,relativePath);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbPath;
    }

    /**
     * 判断文件名是否带盘符，重新处理
     * @param fileName
     * @return
     */
    public static String getFileName(String fileName){
        //判断是否带有盘符信息
        // Check for Unix-style path
        int unixSep = fileName.lastIndexOf('/');
        // Check for Windows-style path
        int winSep = fileName.lastIndexOf('\\');
        // Cut off at latest possible point
        int pos = (winSep > unixSep ? winSep : unixSep);
        if (pos != -1)  {
            // Any sort of path separator found...
            fileName = fileName.substring(pos + 1);
        }
        //替换上传文件名字的特殊字符
        fileName = fileName.replace("=","").replace(",","").replace("&","")
                .replace("#", "").replace("“", "").replace("”", "");
        //替换上传文件名字中的空格
        fileName=fileName.replaceAll("\\s","");
        //update-beign-author:taoyan date:20220302 for: /issues/3381 online 在线表单 使用文件组件时，上传文件名中含%，下载异常
        fileName = fileName.replaceAll(FILE_NAME_REGEX, "");
        //update-end-author:taoyan date:20220302 for: /issues/3381 online 在线表单 使用文件组件时，上传文件名中含%，下载异常
        return fileName;
    }

    /**
     * java 判断字符串里是否包含中文字符
     * @param str
     * @return
     */
    public static boolean ifContainChinese(String str) {
        if(str.getBytes().length == str.length()){
            return false;
        }else{
            Matcher m = ZHONGWEN_PATTERN.matcher(str);
            if (m.find()) {
                return true;
            }
            return false;
        }
    }

    /**
     * 统一全局上传
     * @Return: java.lang.String
     */
    public static String upload(MultipartFile file, String bizPath) {
        String url = "";
        url = OssBootUtil.upload(file, bizPath);
        return url;
    }
    /**
     * 本地文件上传
     * @param mf 文件
     * @param bizPath  自定义路径
     * @return
     */
    public static String uploadLocal(MultipartFile mf,String bizPath,String uploadpath){
        try {
            //update-begin-author:liusq date:20210809 for: 过滤上传文件类型
            FileTypeFilter.fileTypeFilter(mf);
            //update-end-author:liusq date:20210809 for: 过滤上传文件类型
            String fileName = null;
            File file = new File(uploadpath + File.separator + bizPath + File.separator );
            if (!file.exists()) {
                // 创建文件根目录
                file.mkdirs();
            }
            // 获取文件名
            String orgName = mf.getOriginalFilename();
            orgName = CommonUtils.getFileName(orgName);
            if(orgName.indexOf(SymbolConstant.SPOT)!=-1){
                fileName = orgName.substring(0, orgName.lastIndexOf(".")) + "_" + System.currentTimeMillis() + orgName.substring(orgName.lastIndexOf("."));
            }else{
                fileName = orgName+ "_" + System.currentTimeMillis();
            }
            String savePath = file.getPath() + File.separator + fileName;
            File savefile = new File(savePath);
            FileCopyUtils.copy(mf.getBytes(), savefile);
            String dbpath = null;
            if(oConvertUtils.isNotEmpty(bizPath)){
                dbpath = bizPath + File.separator + fileName;
            }else{
                dbpath = fileName;
            }
            if (dbpath.contains(SymbolConstant.DOUBLE_BACKSLASH)) {
                dbpath = dbpath.replace("\\", "/");
            }
            return dbpath;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }

    /**
     * 统一全局上传 带桶
     * @Return: java.lang.String
     */
    public static String upload(MultipartFile file, String bizPath, String uploadType, String customBucket) {
        String url = "";
        if(CommonConstant.UPLOAD_TYPE_OSS.equals(uploadType)){
            url = OssBootUtil.upload(file,bizPath,customBucket);
        }
        return url;
    }

    public static String upload(MultipartFile file, String bizPath,String customBucket, HttpServletRequest request,long size) {
        String url = "";
        url = OssBootUtil.uploadExt(file,bizPath,customBucket,request,size);
        return url;
    }












    /**
     * 获取服务器地址
     *
     * @param request
     * @return
     */
    public static String getBaseUrl(HttpServletRequest request) {
        //1.【兼容】兼容微服务下的 base path-------
        String xGatewayBasePath = request.getHeader("X_GATEWAY_BASE_PATH");
        if(oConvertUtils.isNotEmpty(xGatewayBasePath)){
            log.info("x_gateway_base_path = "+ xGatewayBasePath);
            return  xGatewayBasePath;
        }
        //2.【兼容】SSL认证之后，request.getScheme()获取不到https的问题
        // https://blog.csdn.net/weixin_34376986/article/details/89767950
        String scheme = request.getHeader("X-Forwarded-Scheme");
        if(oConvertUtils.isEmpty(scheme)){
            scheme = request.getScheme();
        }

        //3.常规操作
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        //返回 host domain
        String baseDomainPath = null;
        int length = 80;
        if(length == serverPort){
            baseDomainPath = scheme + "://" + serverName  + contextPath ;
        }else{
            baseDomainPath = scheme + "://" + serverName + ":" + serverPort + contextPath ;
        }
        log.info("-----Common getBaseUrl----- : " + baseDomainPath);
        return baseDomainPath;
    }
}