package com.ruoyi.file.service;

import com.ruoyi.common.core.utils.CommonUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.file.FileTypeUtils;
import com.ruoyi.common.core.utils.file.MimeTypeUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class OssSysFileServiceImpl implements ISysFileService{
    @Override
    public String uploadFile(MultipartFile file) throws Exception {

        try {
            String extension = FileTypeUtils.getExtension(file);
            if (!StringUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.IMAGE_EXTENSION)) {
                return null;
            }
            String url = CommonUtils.upload(file, "images:api:updata");
            return url;

        }catch (Exception e){
            return "";
        }

    }

    @Override
    public String uploadVideoFile(MultipartFile file) throws Exception {
        return "";
    }

    @Override
    public String uploadFiles(MultipartFile file) throws Exception {
        return null;
    }
}
