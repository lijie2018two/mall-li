package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 城市对象 cities
 * 
 * @author lidabai
 * @date 2025-03-20
 */
public class Cities extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** id */
    private String id;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 省id */
    @Excel(name = "省id")
    private String stateId;

    /** 省code */
    @Excel(name = "省code")
    private String stateCode;

    /** 省名成 */
    @Excel(name = "省名成")
    private String stateName;

    /** 国家id */
    @Excel(name = "国家id")
    private String countryId;

    /** 国家code */
    @Excel(name = "国家code")
    private String countryCode;

    /** 国家名 */
    @Excel(name = "国家名")
    private String countryName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String latitude;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String longitude;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String wikiDataId;

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
    public void setStateId(String stateId) 
    {
        this.stateId = stateId;
    }

    public String getStateId() 
    {
        return stateId;
    }
    public void setStateCode(String stateCode) 
    {
        this.stateCode = stateCode;
    }

    public String getStateCode() 
    {
        return stateCode;
    }
    public void setStateName(String stateName) 
    {
        this.stateName = stateName;
    }

    public String getStateName() 
    {
        return stateName;
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
    public void setWikiDataId(String wikiDataId) 
    {
        this.wikiDataId = wikiDataId;
    }

    public String getWikiDataId() 
    {
        return wikiDataId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("stateId", getStateId())
            .append("stateCode", getStateCode())
            .append("stateName", getStateName())
            .append("countryId", getCountryId())
            .append("countryCode", getCountryCode())
            .append("countryName", getCountryName())
            .append("latitude", getLatitude())
            .append("longitude", getLongitude())
            .append("wikiDataId", getWikiDataId())
            .toString();
    }
}
