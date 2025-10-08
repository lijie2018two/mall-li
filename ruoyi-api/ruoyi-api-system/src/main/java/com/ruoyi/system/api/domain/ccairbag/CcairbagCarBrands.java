package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 汽车品牌型号对象 ccairbag_car_brands
 * 
 * @author lidabai
 * @date 2025-03-12
 */
public class CcairbagCarBrands extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;

    /** 品牌 */
    @Excel(name = "品牌")
    private String make;

    /** 型号 */
    @Excel(name = "型号")
    private String model;

    /** 年份 */
    @Excel(name = "年份")
    private String year;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setMake(String make) 
    {
        this.make = make;
    }

    public String getMake() 
    {
        return make;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("make", getMake())
            .append("model", getModel())
            .append("year", getYear())
            .toString();
    }
}
