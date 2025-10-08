package com.ruoyi.system.api.util;


import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.CommonUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.file.FileTypeUtils;
import com.ruoyi.common.core.utils.file.FileUtils;
import com.ruoyi.common.core.utils.file.MimeTypeUtils;
import com.ruoyi.system.api.domain.SysFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Component
public class UploadImageUtils {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final int IMAGE_SIZE_MAX = 1024*1024;
	
	private static final int VEDIO_SIZE_MAX = 1024*1024*10;
	
//	@Value("${prop.upload-folder}")
//	private String uploadFolder;
//	@Value("${prop.resourceHandler}")
//	private String resourceHandler;
	


	//图片上传
	public R<SysFile> uploadOneCheck(MultipartFile file, String pixName) {
		if( file == null){
			return R.fail("文件不存在");
		}
		try {
			String contentType = file.getContentType();
			String extension = FileTypeUtils.getExtension(file);
			if (!StringUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.IMAGE_EXTENSION)) {
				// 图片类型不符合规定
				return R.fail("图片类型格式不正确.");
			}

			String url = CommonUtils.upload(file,"images:"+pixName);
			SysFile sysFile = new SysFile();
			sysFile.setName(FileUtils.getName(url));
			sysFile.setUrl(url);

			return R.ok(sysFile);
		}catch(RuntimeException e) {
			logger.error(e.getMessage());
			return R.fail("服务出现异常.");
		}
	}

	public R<SysFile> uploadOneCheckExt(MultipartFile file, String pixName, HttpServletRequest request) {
		if( file == null){
			return R.fail("文件不存在");
		}
		try {
			String contentType = file.getContentType();
			String extension = FileTypeUtils.getExtension(file);
			if (!StringUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.UPLOAD_APK_TYPES)) {
				// 图片类型不符合规定
				return R.fail("图片类型格式不正确.");
			}


			String url = CommonUtils.upload(file,pixName,null,request,file.getSize());
			SysFile sysFile = new SysFile();
			sysFile.setName(FileUtils.getName(url));
			sysFile.setUrl(url);
			return R.ok(sysFile);
		}catch(RuntimeException e) {
			logger.error(e.getMessage());
			return R.fail("服务出现异常.");
		}
	}
	
	// 上传视频
	public R<SysFile> uploadVedioOneCheck(MultipartFile file, String pixName)  {
		if( file == null){
			return R.fail("文件不存在");
		}
		try {
			String contentType = file.getContentType();
			String extension = FileTypeUtils.getExtension(file);
			if (!StringUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.MP4_VIDEO_EXTENSION)) {
				// 类型不符合规定
				return R.fail("视频类型格式不正确.");
			}
			
			if(file.getSize() > VEDIO_SIZE_MAX) {
				// 图片大小不符合规定
				return R.fail("errors.file.too.large.");
			}

			String url = CommonUtils.upload(file,"video:"+pixName);
			SysFile sysFile = new SysFile();
			sysFile.setName(FileUtils.getName(url));
			sysFile.setUrl(url);
			return R.ok(sysFile);
		}catch(RuntimeException e) {
			logger.error(e.getMessage());
			return R.fail("服务出现异常.");
		}
	}

}
