package com.ruoyi.system.api.domain.ccairbag.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class LogisticsInfoDTO {
    // 物流编号
    private String logisticsNumber;
    // 物流平台名称
    private String logisticsPlatformName;
    // 物流编码
    private String logisticsCode;

    // 已付款时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date paymentTime;
    // 物流单号生成时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date waybillGenerationTime;
    // 完成时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date completionTime;
}
