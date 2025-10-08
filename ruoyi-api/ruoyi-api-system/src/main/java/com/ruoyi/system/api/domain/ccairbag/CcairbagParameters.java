package com.ruoyi.system.api.domain.ccairbag;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 参数表对象 ccairbag_parameters
 * 
 * @author lidabai
 * @date 2025-02-22
 */
@Data
public class CcairbagParameters extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long parameterId;

    /** 参数名 */
    @Excel(name = "参数名")
    private String parameterName;

    /** 分组id */
    @Excel(name = "是否必填")
    private int isMust;

    /** 品类id */
    @Excel(name = "品类id")
    private Long categoryId;

    @TableField(exist = false)
    private String value;
    @TableField(exist = false)
    private Long valueId;

    /** 逻辑删除标志，0 表示未删除，1 表示已删除 */
    @Excel(name = "逻辑删除标志，0 表示未删除，1 表示已删除")
    private Integer deleted;

    public void setParameterId(Long parameterId) 
    {
        this.parameterId = parameterId;
    }

    public Long getParameterId() 
    {
        return parameterId;
    }
    public void setParameterName(String parameterName) 
    {
        this.parameterName = parameterName;
    }

    public String getParameterName() 
    {
        return parameterName;
    }

    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }
    public void setDeleted(Integer deleted) 
    {
        this.deleted = deleted;
    }

    public Integer getDeleted() 
    {
        return deleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("parameterId", getParameterId())
            .append("parameterName", getParameterName())
            .append("categoryId", getCategoryId())
            .append("deleted", getDeleted())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .toString();
    }
}
