package com.ruoyi.system.api.domain.ccairbag;


import com.ruoyi.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单表;
 * @author : http://www.chiner.pro
 * @date : 2025-4-7
 */
@ApiModel(value = "总订单表",description = "")
@Data
public class CcairbagOrdersTotal  extends BaseEntity {
    /** 订单唯一标识，自增主键 */
    private static final long serialVersionUID = 1L;

    /** 订单唯一标识，自增主键 */
    private Long orderTotalId;
    /** 下单用户的 ID */
    @ApiModelProperty(name = "下单用户的 ID",notes = "")
    private Long userId ;
    /** 订单总金额 */
    @ApiModelProperty(name = "订单总金额",notes = "")
    private BigDecimal totalAmount ;
    /** 订单最终金额，扣除折扣后的金额 */
    @ApiModelProperty(name = "订单最终金额",notes = "")
    private BigDecimal finalAmount ;
    /** 支付类型手续费' */
    @ApiModelProperty(name = "支付类型手续费'",notes = "")
    private BigDecimal paymentFee ;
    /** 关税 */
    @ApiModelProperty(name = "关税",notes = "")
    private BigDecimal tariff ;
    /** 支付时间 */
    @ApiModelProperty(name = "支付时间",notes = "")
    private Date payTime;    /** 订单信息创建时间，默认当前时间 */
    @ApiModelProperty(name = "订单信息创建时间，默认当前时间",notes = "")
    private Date createTime ;
    /** 订单信息最后一次更新时间，自动更新为当前时间 */
    @ApiModelProperty(name = "订单信息最后一次更新时间，自动更新为当前时间",notes = "")
    private Date updateTime ;
    /** 是否删除 */
    @ApiModelProperty(name = "是否删除",notes = "")
    private Integer deleted ;

    @ApiModelProperty(name = "支付方式",notes = "")
    private Integer payType ;
    /** 备注 */
    @ApiModelProperty(name = "备注",notes = "")
    private String remarks ;
    /** 用户订单地址Id;用户订单地址Id */
    @ApiModelProperty(name = "用户订单地址Id",notes = "用户订单地址Id")
    private Long addrId ;

    private Integer orderStatus;

    @ApiParam(value = "商品数量")
    private Integer productNums;

    private String orderNumber;

    private List<CcairbagOrders> ordersList;

}
