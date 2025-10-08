package com.ruoyi.system.api.domain.ccairbag;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 国家对象 countries
 * 
 * @author lidabai
 * @date 2025-03-20
 */
public class Countries extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Integer id;

    /** 国家名 */
    @Excel(name = "国家名")
    private String name;

    /** $column.columnComment */
    private String iso3;

    /** $column.columnComment */
    private String numericCode;

    /** $column.columnComment */
    private String iso2;

    /** $column.columnComment */
    private String phonecode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String capital;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String currency;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String currencyName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String currencySymbol;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String tld;


    /** $column.columnComment */
    private String region;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Integer regionId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String subregion;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Integer subregionId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String nationality;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String timezones;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String translations;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private BigDecimal latitude;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private BigDecimal longitude;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String emoji;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String emojiU;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date createdAt;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date updatedAt;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Integer flag;

    /** Rapid API GeoDB Cities */
    @Excel(name = "Rapid API GeoDB Cities")
    private String wikiDataId;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
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
    public void setIso3(String iso3) 
    {
        this.iso3 = iso3;
    }

    public String getIso3() 
    {
        return iso3;
    }
    public void setNumericCode(String numericCode) 
    {
        this.numericCode = numericCode;
    }

    public String getNumericCode() 
    {
        return numericCode;
    }
    public void setIso2(String iso2) 
    {
        this.iso2 = iso2;
    }

    public String getIso2() 
    {
        return iso2;
    }
    public void setPhonecode(String phonecode) 
    {
        this.phonecode = phonecode;
    }

    public String getPhonecode() 
    {
        return phonecode;
    }
    public void setCapital(String capital) 
    {
        this.capital = capital;
    }

    public String getCapital() 
    {
        return capital;
    }
    public void setCurrency(String currency) 
    {
        this.currency = currency;
    }

    public String getCurrency() 
    {
        return currency;
    }
    public void setCurrencyName(String currencyName) 
    {
        this.currencyName = currencyName;
    }

    public String getCurrencyName() 
    {
        return currencyName;
    }
    public void setCurrencySymbol(String currencySymbol) 
    {
        this.currencySymbol = currencySymbol;
    }

    public String getCurrencySymbol() 
    {
        return currencySymbol;
    }
    public void setTld(String tld) 
    {
        this.tld = tld;
    }

    public String getTld() 
    {
        return tld;
    }

    public void setRegion(String region) 
    {
        this.region = region;
    }

    public String getRegion() 
    {
        return region;
    }
    public void setRegionId(Integer regionId) 
    {
        this.regionId = regionId;
    }

    public Integer getRegionId() 
    {
        return regionId;
    }
    public void setSubregion(String subregion) 
    {
        this.subregion = subregion;
    }

    public String getSubregion() 
    {
        return subregion;
    }
    public void setSubregionId(Integer subregionId) 
    {
        this.subregionId = subregionId;
    }

    public Integer getSubregionId() 
    {
        return subregionId;
    }
    public void setNationality(String nationality) 
    {
        this.nationality = nationality;
    }

    public String getNationality() 
    {
        return nationality;
    }
    public void setTimezones(String timezones) 
    {
        this.timezones = timezones;
    }

    public String getTimezones() 
    {
        return timezones;
    }
    public void setTranslations(String translations) 
    {
        this.translations = translations;
    }

    public String getTranslations() 
    {
        return translations;
    }
    public void setLatitude(BigDecimal latitude) 
    {
        this.latitude = latitude;
    }

    public BigDecimal getLatitude() 
    {
        return latitude;
    }
    public void setLongitude(BigDecimal longitude) 
    {
        this.longitude = longitude;
    }

    public BigDecimal getLongitude() 
    {
        return longitude;
    }
    public void setEmoji(String emoji) 
    {
        this.emoji = emoji;
    }

    public String getEmoji() 
    {
        return emoji;
    }
    public void setEmojiU(String emojiU) 
    {
        this.emojiU = emojiU;
    }

    public String getEmojiU() 
    {
        return emojiU;
    }
    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }
    public void setUpdatedAt(Date updatedAt) 
    {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() 
    {
        return updatedAt;
    }
    public void setFlag(Integer flag) 
    {
        this.flag = flag;
    }

    public Integer getFlag() 
    {
        return flag;
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
            .append("iso3", getIso3())
            .append("numericCode", getNumericCode())
            .append("iso2", getIso2())
            .append("phonecode", getPhonecode())
            .append("capital", getCapital())
            .append("currency", getCurrency())
            .append("currencyName", getCurrencyName())
            .append("currencySymbol", getCurrencySymbol())
            .append("tld", getTld())
            .append("region", getRegion())
            .append("regionId", getRegionId())
            .append("subregion", getSubregion())
            .append("subregionId", getSubregionId())
            .append("nationality", getNationality())
            .append("timezones", getTimezones())
            .append("translations", getTranslations())
            .append("latitude", getLatitude())
            .append("longitude", getLongitude())
            .append("emoji", getEmoji())
            .append("emojiU", getEmojiU())
            .append("createdAt", getCreatedAt())
            .append("updatedAt", getUpdatedAt())
            .append("flag", getFlag())
            .append("wikiDataId", getWikiDataId())
            .toString();
    }
}
