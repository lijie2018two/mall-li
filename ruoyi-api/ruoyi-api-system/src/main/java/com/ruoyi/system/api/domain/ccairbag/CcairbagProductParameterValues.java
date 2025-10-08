package com.ruoyi.system.api.domain.ccairbag;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 商品参数值表对象 ccairbag_product_parameter_values
 * 
 * @author lidabai
 * @date 2025-02-22
 */
public class CcairbagProductParameterValues extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long valueId;

    /** 商品id */
    @Excel(name = "商品id")
    private Long productId;

    /** 参数id */
    @Excel(name = "参数id")
    private Long parameterId;

    /** 参数值 */
    @Excel(name = "参数值")
    private String value;

    /** 逻辑删除标志，0 表示未删除，1 表示已删除 */
    @Excel(name = "逻辑删除标志，0 表示未删除，1 表示已删除")
    private Integer deleted;

    public void setValueId(Long valueId) 
    {
        this.valueId = valueId;
    }

    public Long getValueId() 
    {
        return valueId;
    }
    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }
    public void setParameterId(Long parameterId) 
    {
        this.parameterId = parameterId;
    }

    public Long getParameterId() 
    {
        return parameterId;
    }
    public void setValue(String value) 
    {
        this.value = value;
    }

    public String getValue() 
    {
        return value;
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
            .append("valueId", getValueId())
            .append("productId", getProductId())
            .append("parameterId", getParameterId())
            .append("value", getValue())
            .append("deleted", getDeleted())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .toString();
    }
}
