package com.ruoyi.system.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class IndexVo {
    @ApiModelProperty("平台总收入")
    private BigDecimal ptTotalMoney;

    @ApiModelProperty("平台总支出")
    private BigDecimal ptTotalOutlay;

    @ApiModelProperty("总订单量")
    private int totalOrderNum;

    @ApiModelProperty("今日收入")
    private BigDecimal ptTodayMoney;

    @ApiModelProperty("今日支出")
    private BigDecimal ptTodayOutlay;

    @ApiModelProperty("今日申请代理商数量")
    private int ptTodayAddAgent;

    @ApiModelProperty("今日申请达人量")
    private int ptTodayAddIntelligent;


    @ApiModelProperty("今日进行中订单")
    private int ptTodayOrderIng;

    @ApiModelProperty("今日已完成订单")
    private int ptTodayOrderFinish;

    @ApiModelProperty("今日未接订单")
    private int ptTodayOrderNoAccept;

    @ApiModelProperty("投诉订单")
    private int ptOrderComplaint;


    @ApiModelProperty("用户申请提现数量")
    private int userWithdrawalNum;

    @ApiModelProperty("达人申请提现数量")
    private int talentWithdrawalNum;

    @ApiModelProperty("代理商申请提现数量")
    private int agentWithdrawalNum;

    @ApiModelProperty("平台当日反馈数量")
    private int platformFeedbackNum;


}
