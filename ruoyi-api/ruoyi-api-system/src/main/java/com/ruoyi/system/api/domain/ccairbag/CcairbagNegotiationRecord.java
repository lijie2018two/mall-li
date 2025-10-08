package com.ruoyi.system.api.domain.ccairbag;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 议价记录对象 ccairbag_negotiation_record
 * 
 * @author lidabai
 * @date 2025-05-12
 */
@Data
public class CcairbagNegotiationRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 关联商品表的商品ID */
    @Excel(name = "关联商品表的商品ID")
    private Long productId;

    /** 买家ID，关联用户表 */
    @Excel(name = "买家ID，关联用户表")
    private Long buyerId;

    /** 卖家ID，关联用户表 */
    @Excel(name = "卖家ID，关联用户表")
    private Long shopId;

    /** 买家出价 */
    @Excel(name = "买家出价")
    private BigDecimal offerPrice;

    /** 议价状态，0-待处理，1-同意，2-拒绝，3-自动拒绝 */
    @Excel(name = "议价状态，0-待处理，1-同意，2-拒绝，3-自动拒绝 4 - 过期")
    private Integer status;

    private Integer negotiationCount;

    private String remarks;

    private CcairbagProducts products;
}
