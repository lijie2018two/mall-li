package com.ruoyi.system.api.domain.ccairbag.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShopMoneyDTO {
    /**
     * 账户总金额（可用+暂停）
     * 账户可用金额
     * 账户暂停金额
     * 累计提现金额（已完成状态）
     */
    @ApiParam(value = "账户总金额")
    private BigDecimal totalMoney;

    @ApiParam(value = "账户可用金额")
    private BigDecimal availableMoney;

    @ApiParam(value = "账户暂停金额")
    private BigDecimal pauseMoney;

    @ApiParam(value = "累计提现金额")
    private BigDecimal sumWithdrawMoney;


}
