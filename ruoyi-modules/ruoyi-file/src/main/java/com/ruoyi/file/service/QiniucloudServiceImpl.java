package com.ruoyi.file.service;

import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.file.FileTypeUtils;
import com.ruoyi.common.core.utils.file.MimeTypeUtils;
import com.ruoyi.file.utils.QiniuCloudUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Minio 文件存储
 *
 * @author ruoyi
 */
@Primary
@Service
public class QiniucloudServiceImpl implements ISysFileService
{
    @Autowired
    private QiniuCloudUploadUtil qiniuCloudUploadUtil;

    public String uploadFile(MultipartFile file) throws Exception {
        String extension = FileTypeUtils.getExtension(file);
        if (!StringUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.IMAGE_EXTENSION)) {
            return null;
        }
        return qiniuCloudUploadUtil.uploadFile(file);
    }

    public String uploadVideoFile(MultipartFile file) throws Exception {
        String extension = FileTypeUtils.getExtension(file);
        if (!StringUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.MP4_VIDEO_EXTENSION)) {
            return null;
        }
        return qiniuCloudUploadUtil.uploadVideoFile(file,"video",file.getOriginalFilename());
    }

    @Override
    public String uploadFiles(MultipartFile file) throws Exception {
//        String extension = FileTypeUtils.getExtension(file);
//        if (!StringUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.MP4_VIDEO_EXTENSION)) {
//            return null;
//        }
        return qiniuCloudUploadUtil.uploadVideoFile(file,"files",file.getOriginalFilename());
    }


}
