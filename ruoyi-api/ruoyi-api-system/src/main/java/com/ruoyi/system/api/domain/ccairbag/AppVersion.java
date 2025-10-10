package com.ruoyi.system.api.domain.ccairbag;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * App版本信息对象 app_version
 * 
 * @author lidabai
 * @date 2025-10-09
 */
@Data
public class AppVersion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 版本号(数字形式，用于比较) */
    @Excel(name = "版本号(数字形式，用于比较)")
    private String versionCode;

    /** 版本名称(如1.0.0) */
    @Excel(name = "版本名称(如1.0.0)")
    private String versionName;

    /** APK文件存储路径 */
    @Excel(name = "APK文件存储路径")
    private String apkFilePath;

    /** 文件大小(字节) */
    @Excel(name = "文件大小(字节)")
    private Long fileSize;

    /** 是否强制升级(0-否，1-是) */
    @Excel(name = "是否强制升级(0-否，1-是)")
    private Integer isForceUpdate;

    /** 更新内容描述 */
    @Excel(name = "更新内容描述")
    private String updateContent;

    /** 下载次数 */
    @Excel(name = "下载次数")
    private Long downloadCount;

    /** 状态(0-禁用，1-启用) */
    @Excel(name = "状态(0-禁用，1-启用)")
    private Integer status;

    @Excel(name = "是否删除")
    private Integer deleted;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setVersionName(String versionName) 
    {
        this.versionName = versionName;
    }

    public String getVersionName() 
    {
        return versionName;
    }
    public void setApkFilePath(String apkFilePath) 
    {
        this.apkFilePath = apkFilePath;
    }

    public String getApkFilePath() 
    {
        return apkFilePath;
    }
    public void setFileSize(Long fileSize) 
    {
        this.fileSize = fileSize;
    }

    public Long getFileSize() 
    {
        return fileSize;
    }
    public void setIsForceUpdate(Integer isForceUpdate) 
    {
        this.isForceUpdate = isForceUpdate;
    }

    public Integer getIsForceUpdate() 
    {
        return isForceUpdate;
    }
    public void setUpdateContent(String updateContent) 
    {
        this.updateContent = updateContent;
    }

    public String getUpdateContent() 
    {
        return updateContent;
    }
    public void setDownloadCount(Long downloadCount) 
    {
        this.downloadCount = downloadCount;
    }

    public Long getDownloadCount() 
    {
        return downloadCount;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("versionCode", getVersionCode())
            .append("versionName", getVersionName())
            .append("apkFilePath", getApkFilePath())
            .append("fileSize", getFileSize())
            .append("isForceUpdate", getIsForceUpdate())
            .append("updateContent", getUpdateContent())
            .append("downloadCount", getDownloadCount())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
