package com.ruoyi.system.api.domain.ccairbag;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 税收比例

对象 ccairbag_setscale
 * 
 * @author lidabai
 * @date 2025-04-21
 */
@Data
public class CcairbagSetscale extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /**  销售税比例 */
    @ApiParam(value = "销售税比例")
    private BigDecimal scale;

    /** 交易费用比例 */
    @ApiParam(value = "交易费用比例")
    private BigDecimal dealScale;

    /** 是否删除 */
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
    public void setScale(BigDecimal scale) 
    {
        this.scale = scale;
    }

    public BigDecimal getScale()
    {
        return scale;
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
            .append("id", getId())
            .append("scale", getScale())
            .append("dealScale", getDealScale())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("deleted", getDeleted())
            .toString();
    }
}
