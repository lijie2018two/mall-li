package com.ruoyi.system.api.domain.ccairbag;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 用户信息登记对象 ccairbag_user_registration
 * 
 * @author lidabai
 * @date 2025-03-20
 */
@Data
public class CcairbagUserRegistration extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 用户类型：1（企业卖家）、2（个人卖家） */
    @Excel(name = "用户类型：1", readConverterExp = "企=业卖家")
    private Integer userType;

    /** 真实姓名（个人卖家） */
    @Excel(name = "真实姓名", readConverterExp = "个=人卖家")
    private String realName;

    /** 身份证类型（个人卖家） */
    @Excel(name = "0 身份证 1护照 2 驾驶证", readConverterExp = "个=人卖家")
    private String idCardType;

    /** 身份证号（个人卖家） */
    @Excel(name = "身份证号", readConverterExp = "个=人卖家")
    private String idCardNumber;

    /** 家庭住址（个人卖家） */
    @Excel(name = "家庭住址", readConverterExp = "个=人卖家")
    private String homeAddress;

    /** 联系电话（个人卖家） */


    /** 企业名称（企业卖家） */
    @Excel(name = "企业名称", readConverterExp = "企=业卖家")
    private String companyName;

    /** 公司注册所在地（企业卖家） */
    @Excel(name = "公司注册所在地", readConverterExp = "企=业卖家")
    private String companyLocation;

    /** 联盟税号（EIN企业卖家） */
    @Excel(name = "联盟税号", readConverterExp = "E=IN企业卖家")
    private String companyCode;

    /** 公司地址（企业卖家） */
    @Excel(name = "公司地址", readConverterExp = "企=业卖家")
    private String companyAddress;

    /** 负责人联系电话（企业卖家） */
    @ApiParam(name = "联系电话 明文")
    private String phone;

    @ApiParam(name = "联系电话 混淆")
    @TableField(exist = false)
    private String phoneMix;

    public void setPhoneMix(String phone) {
        if (phone != null && phone.length() >= 7) {
            this.phone = phone.substring(0, 3) + "xxxx" + phone.substring(phone.length() - 4);
        } else {
            this.phone = phone;
        }
    }
    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer deleted;

}
