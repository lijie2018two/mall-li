package com.ruoyi.system.api.domain.ccairbag;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 用户地址管理对象 ccairbag_user_addr
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Data
public class CcairbagUserAddr extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private String orderId;

    @ApiParam(value = "随机数")
    private String randomNum;
    /** ID */
    private Long addrId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    @ApiParam(name = "地址类型", value = "1：发货地址、2：退货地址、3：线下取货地址 4:账单地址 ")
    private Integer addrType;


    /** 收货人 */
    @Excel(name = "收货人")
    private String receiver;

    /** 国家名 */
    @Excel(name = "国家名")
    private String country;

    /** 州 / 省 / 大区名称;州 / 省 / 大区名称，不同国家可能有不同称呼 */
    @Excel(name = "州 / 省 / 大区名称;州 / 省 / 大区名称，不同国家可能有不同称呼")
    private String stateProvince;

    /** 城市 */
    @Excel(name = "城市")
    private String city;

    /** 区 */
    @Excel(name = "区")
    private String phone;

    /** 街道 */
    @Excel(name = "街道")
    private String street;

    /** 门牌号 */
    @Excel(name = "门牌号")
    private String houseNumber;

    /** 邮编 */
    @Excel(name = "邮编")
    private String postCode;

    /** 状态,1正常，0无效 */
    @Excel(name = "状态,1正常，0无效")
    private Integer status;

    /** 是否默认地址 1是 */
    @Excel(name = "是否默认地址 1是")
    private Integer commonAddr;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer deleted;

    public void setAddrId(Long addrId) 
    {
        this.addrId = addrId;
    }

    public Long getAddrId() 
    {
        return addrId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setReceiver(String receiver) 
    {
        this.receiver = receiver;
    }

    public String getReceiver() 
    {
        return receiver;
    }
    public void setCountry(String country) 
    {
        this.country = country;
    }

    public String getCountry() 
    {
        return country;
    }

    public void setCity(String city) 
    {
        this.city = city;
    }

    public String getCity() 
    {
        return city;
    }

    public void setStreet(String street) 
    {
        this.street = street;
    }

    public String getStreet() 
    {
        return street;
    }
    public void setHouseNumber(String houseNumber) 
    {
        this.houseNumber = houseNumber;
    }

    public String getHouseNumber() 
    {
        return houseNumber;
    }
    public void setPostCode(String postCode) 
    {
        this.postCode = postCode;
    }

    public String getPostCode() 
    {
        return postCode;
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
            .append("addrId", getAddrId())
            .append("userId", getUserId())
            .append("receiver", getReceiver())
            .append("country", getCountry())
            .append("stateProvince", getStateProvince())
            .append("city", getCity())
            .append("street", getStreet())
            .append("houseNumber", getHouseNumber())
            .append("postCode", getPostCode())

            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("deleted", getDeleted())
            .toString();
    }
}
