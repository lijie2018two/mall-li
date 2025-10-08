package com.ruoyi.system.api.domain.ccairbag;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 用户管理对象 ccairbag_users
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Data
public class CcairbagUsers extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long userId;

    /** 用户类型;0:普通用户，1：商业用户。 2 个人卖家 */
    private Integer userType;

    /** 用户名字;用户名字，对于外国用户适用 */
    @Excel(name = "用户名字;用户名字，对于外国用户适用")
    private String firstName;

    /** 用户姓氏;用户姓氏，对于外国用户适用 */
    @Excel(name = "用户姓氏;用户姓氏，对于外国用户适用")
    private String lastName;



    /** 头像 */
    @Excel(name = "头像")
    private String headimgurl;

    /** 用户名;用户名 */
    @Excel(name = "用户名;用户名")
    private String userName;

    /** 用户邮箱;用户邮箱，唯一且不能为空 */
    @Excel(name = "用户邮箱;用户邮箱，唯一且不能为空")
    private String email;

    /** 加密后的用户密码 */
    @Excel(name = "加密后的用户密码")
    private String passwordHash;

    private String passwordOld;

    @ApiParam(name = "是否显示手机号 0不显示 1 显示")
    private Integer showPhone;

    @ApiParam(name = "是否认证 0 未认证 1 已认证")
    private Integer isVerified;

    @TableField(exist = false)
    private String confirmedPassword;

    @TableField(exist = false)
    private String verifyCode;

    @ApiParam(name = "联系电话 明文")
    private String phone;

    @ApiParam(name = "联系电话 混淆")
    @TableField(exist = false)
    private String phoneMix;
    @ApiParam(name = "0未开通，1审核中，2审核拒绝，3已开通  ")
    private Integer qualificationStatus;


    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer deleted;

    private CcairbagUserRegistration ccairbagUserRegistration;

    public void setPhoneMix(String phone) {
        if (phone != null && phone.length() >= 7) {
            this.phone = phone.substring(0, 3) + "xxxx" + phone.substring(phone.length() - 4);
            this.phoneMix = phone.substring(0, 3) + "xxxx" + phone.substring(phone.length() - 4);
        } else {
            this.phone = phone;
        }
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setUserType(Integer userType) 
    {
        this.userType = userType;
    }

    public Integer getUserType() 
    {
        return userType;
    }
    public void setFirstName(String firstName) 
    {
        this.firstName = firstName;
    }

    public String getFirstName() 
    {
        return firstName;
    }
    public void setLastName(String lastName) 
    {
        this.lastName = lastName;
    }

    public String getLastName() 
    {
        return lastName;
    }

    public void setHeadimgurl(String headimgurl) 
    {
        this.headimgurl = headimgurl;
    }

    public String getHeadimgurl() 
    {
        return headimgurl;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }
    public void setPasswordHash(String passwordHash) 
    {
        this.passwordHash = passwordHash;
    }

    public String getPasswordHash() 
    {
        return passwordHash;
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
            .append("userId", getUserId())
            .append("userType", getUserType())
            .append("firstName", getFirstName())
            .append("lastName", getLastName())

            .append("headimgurl", getHeadimgurl())
            .append("email", getEmail())
            .append("passwordHash", getPasswordHash())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("deleted", getDeleted())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
