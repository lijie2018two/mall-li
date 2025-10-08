package com.ruoyi.system.api.domain.ccairbag;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * 绑定的银行卡对象 ccairbag_shop_bank
 * 
 * @author lidabai
 * @date 2025-04-27
 */
@Data
public class CcairbagShopBank extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 银行卡类型 */
    @ApiParam(value = "银行卡类型 1 paypal 2 银行卡")
    private Integer payType;

    /** 开户地址 */
    @ApiParam(value = "账户")
    private String accountNumber;

    @ApiParam(value = "账户Ext")
    @TableField(exist = false)
    private String accountNumberExt;

    /** 店铺id */
    @Excel(name = "店铺id")
    private Long shopId;

    /** 真实身份id */
    @Excel(name = "真实身份id")
    private String idCard;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 店铺名 */
    @Excel(name = "店铺名")
    private String shopName;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer deleted;





}
