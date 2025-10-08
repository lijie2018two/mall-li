package com.ruoyi.system.api.domain.ccairbag;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 提现记录对象 ccairbag_withdrawal
 * 
 * @author lidabai
 * @date 2025-04-27
 */
@Data
public class CcairbagWithdrawal extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 店铺id */
    @Excel(name = "店铺id")
    private Long shopId;

    @ApiParam(value = "银行卡id")
    private Long bankId;

    /** 店铺名 */
    @Excel(name = "店铺名")
    private String shopName;

    /** 申请时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyTime;

    /** 抽佣金额 */
    @Excel(name = "抽佣金额")
    private BigDecimal drawMoney;

    /** 提现金额 */
    @Excel(name = "提现金额")
    private BigDecimal money;

    /** 银行卡ext */
    @Excel(name = "银行卡ext")
    private String bankCardExt;

    /** 银行卡 */
    @Excel(name = "银行卡")
    private String bankCard;

    /** 实际到账金额 */
    @Excel(name = "实际到账金额")
    private BigDecimal realMoney;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 真实身份id */
    @Excel(name = "真实身份id")
    private String idCard;

    /** 状态 1：发起提现；2：提现关单；3：到账成功 */
    @Excel(name = "状态 1：发起提现；2：提现关单；3：到账成功")
    private Integer status;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer deleted;

    @ApiParam(value = "银行卡类型 1 paypal 2 银行卡")
    @TableField(exist = false)
    private Integer payType;

    /** 开户地址 */
    @ApiParam(value = "账户")
    @TableField(exist = false)
    private String accountNumber;

    @ApiParam(value = "账户Ext")
    @TableField(exist = false)
    private String accountNumberExt;


}
