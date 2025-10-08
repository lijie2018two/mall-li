package com.ruoyi.system.api.domain.ccairbag.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class CcairbagConversationsDTO {
    @ApiParam(name = "消息内容")
    private String messageContent;
    @ApiParam(name = "消息类型 0：文字 1：图片 2：语音 3：视频 4：文件")
    private Integer messageType;

    @ApiParam(name = "商铺id")
    private Long shopId;

    private Long conversationId;

    @ApiParam(name = "发送人类型 0：买家 1 卖家")
    private Integer senderType;

    private Long orderId;
}
