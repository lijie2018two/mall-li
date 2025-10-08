package com.ruoyi.system.api.domain.ccairbag;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 商品关联的适用车辆对象 ccairbag_product_cars
 * 
 * @author lidabai
 * @date 2025-03-12
 */
@Data
public class CcairbagProductCars extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 品牌 */
    @Excel(name = "品牌")
    private String make;

    /** 车型 */
    @Excel(name = "车型")
    private String model;

    /** 年份 */
    @Excel(name = "年份")
    private String year;

    /** 商品id */
    @Excel(name = "商品id")
    private Long productId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setModel(String model) 
    {
        this.model = model;
    }

    public String getModel() 
    {
        return model;
    }
    public void setYear(String year) 
    {
        this.year = year;
    }

    public String getYear() 
    {
        return year;
    }
    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("model", getModel())
            .append("year", getYear())
            .append("productId", getProductId())
            .toString();
    }
}
