package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 省对象 states
 * 
 * @author lidabai
 * @date 2025-03-20
 */
public class States extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;

    /** 省名称 */
    @Excel(name = "省名称")
    private String name;

    /** 国家id */
    @Excel(name = "国家id")
    private String countryId;

    /** 国家code */
    @Excel(name = "国家code")
    private String countryCode;

    /** 国家名 */
    @Excel(name = "国家名")
    private String countryName;

    /** 省code */
    @Excel(name = "省code")
    private String stateCode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String type;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String latitude;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String longitude;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setCountryId(String countryId) 
    {
        this.countryId = countryId;
    }

    public String getCountryId() 
    {
        return countryId;
    }
    public void setCountryCode(String countryCode) 
    {
        this.countryCode = countryCode;
    }

    public String getCountryCode() 
    {
        return countryCode;
    }
    public void setCountryName(String countryName) 
    {
        this.countryName = countryName;
    }

    public String getCountryName() 
    {
        return countryName;
    }
    public void setStateCode(String stateCode) 
    {
        this.stateCode = stateCode;
    }

    public String getStateCode() 
    {
        return stateCode;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setLatitude(String latitude) 
    {
        this.latitude = latitude;
    }

    public String getLatitude() 
    {
        return latitude;
    }
    public void setLongitude(String longitude) 
    {
        this.longitude = longitude;
    }

    public String getLongitude() 
    {
        return longitude;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("countryId", getCountryId())
            .append("countryCode", getCountryCode())
            .append("countryName", getCountryName())
            .append("stateCode", getStateCode())
            .append("type", getType())
            .append("latitude", getLatitude())
            .append("longitude", getLongitude())
            .toString();
    }
}
