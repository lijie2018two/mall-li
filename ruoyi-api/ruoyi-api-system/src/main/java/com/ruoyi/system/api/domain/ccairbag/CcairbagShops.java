package com.ruoyi.system.api.domain.ccairbag;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 店铺对象 ccairbag_shops
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Data
public class CcairbagShops extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 店铺唯一标识，自增主键 */
    private Long shopId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    @TableField(exist = false)
    private String userName;

    /** 店铺名称，不能为空 */
    @Excel(name = "店铺名称，不能为空")
    private String shopName;

    /** 店铺简介 */
    @Excel(name = "店铺简介")
    private String intro;

    @TableField(exist = false)
    private Integer userType;
    /** 店铺公告 */
    @Excel(name = "店铺公告")
    private String shopNotice;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer deleted;

    /** 店铺logo */
    @Excel(name = "店铺logo")
    private String shopLogo;

    /** 店铺联系电话 */
    @Excel(name = "店铺联系电话")
    private String shopPhone;

    /** 店铺入驻申请状态，初始为待审核;0：待审核，1：通过，2：拒绝 */
    @Excel(name = "店铺入驻申请状态，初始为待审核;0：待审核，1：通过，2：拒绝")
    private Integer applicationStatus;

    /** 若入驻申请被拒绝，记录拒绝原因 */
    @Excel(name = "若入驻申请被拒绝，记录拒绝原因")
    private String applicationReason;

    @ApiParam(value = "钱包余额待入账 冻结金额 暂停金额")
    private BigDecimal walletBalance;

    @ApiParam(value = "可提现")
    private BigDecimal walletBalanceExt;

    @ApiParam(value = "支付成功，收货前的钱")
    private BigDecimal walletOrderIng;

    @ApiParam(value = "销售资质 图片")
    private String businessQualificationImages;



    @ApiParam(value = "好评率")
    private BigDecimal goodRate;

    @ApiParam(value = "销量")
    private Integer salesVolume;


    @ApiParam(value = "销售税比例")
    private BigDecimal scale;

    /** 交易费用比例 */
    @ApiParam(value = "交易费用比例")
    private BigDecimal dealScale;

    @ApiParam(value = "描述准确平均分")
    private BigDecimal accuracyScore;

    @ApiParam(value = "物流速度平均分")
    private BigDecimal logisticsScore;

    @ApiParam(value = "沟通平均分")
    private BigDecimal communicationScore;

    @ApiParam(value = "热门分类列表")
    private List<CcairbagCategory> ccairbagCategoryList;

}
