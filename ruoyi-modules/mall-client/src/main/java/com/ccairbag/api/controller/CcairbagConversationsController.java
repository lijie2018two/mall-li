package com.ccairbag.api.controller;

import com.ccairbag.api.service.ICcairbagConversationsService;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.dto.CcairbagConversationsDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 消息会话Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/conversations")
public class CcairbagConversationsController extends BaseController
{
    @Autowired
    private ICcairbagConversationsService ccairbagConversationsService;


    @ApiOperation(value = "客户消息会话列表")
    @GetMapping("/list")
    public AppResult list()
    {
        return ccairbagConversationsService.list();
    }

    @ApiOperation(value = "商家消息会话列表")
    @GetMapping("/shopList")
    public AppResult ShopList()
    {
        return ccairbagConversationsService.ShopList();
    }

    @ApiOperation(value = "客户从 进入店铺 发送消息给商家")
    @PostMapping("/sendMessagesByShop")
    public AppResult sendMessagesByShop(@RequestBody CcairbagConversationsDTO ccairbagConversationsDTO)
    {
        if (oConvertUtils.isEmpty(ccairbagConversationsDTO.getShopId())){
            return AppResult.error("商家id不能为空");
        }
        if (oConvertUtils.isNotEmpty(ccairbagConversationsDTO.getConversationId())){
            return AppResult.error("已有会话id ，业务错乱");
        }

        return ccairbagConversationsService.sendMessagesByShop(ccairbagConversationsDTO);
    }

    @ApiOperation(value = "商家通过订单信息 联系买家")
    @PostMapping("/sendMessagesByOrderId")
    public AppResult sendMessagesByOrderId(@RequestBody CcairbagConversationsDTO ccairbagConversationsDTO)
    {
        if (oConvertUtils.isEmpty(ccairbagConversationsDTO.getOrderId())){
            return AppResult.error("订单id不能为空");
        }
        if (oConvertUtils.isNotEmpty(ccairbagConversationsDTO.getConversationId())){
            return AppResult.error("已有会话id ，业务错乱");
        }

        return ccairbagConversationsService.sendMessagesByOrderId(ccairbagConversationsDTO);
    }

    @ApiOperation(value = "已有会话id 后 发送消息")
    @PostMapping("/sendMessages")
    public AppResult sendMessages(@RequestBody CcairbagConversationsDTO ccairbagConversationsDTO)
    {
        if (oConvertUtils.isEmpty(ccairbagConversationsDTO.getConversationId())){
            return AppResult.error("会话id不能为空");
        }
        if (oConvertUtils.isEmpty(ccairbagConversationsDTO.getMessageType())){
            return AppResult.error("消息类型不能为空");
        }
        if (oConvertUtils.isEmpty(ccairbagConversationsDTO.getSenderType())){
            return AppResult.error("发送人类型不能为空");
        }
        if (oConvertUtils.isEmpty(ccairbagConversationsDTO.getMessageContent())){
            return AppResult.error("消息内容不能为空");
        }
        return ccairbagConversationsService.sendMessages(ccairbagConversationsDTO);
    }

    @ApiOperation(value = "查看消息，设置已读")
    @PostMapping("/readMessages")
    public AppResult readMessages(@RequestBody CcairbagConversationsDTO ccairbagConversationsDTO)
    {
        if (oConvertUtils.isEmpty(ccairbagConversationsDTO.getSenderType())){
            return AppResult.error("发送人类型不能为空");
        }
        if (oConvertUtils.isEmpty(ccairbagConversationsDTO.getConversationId())){
            return AppResult.error("消息会话id不能为空");
        }
        return ccairbagConversationsService.readMessages(ccairbagConversationsDTO);
    }

    @ApiOperation("消息通知详情")
    @GetMapping("/get")
    public AppResult numberOfUnreadMessageTypesOnMobile()
    {
        return  ccairbagConversationsService.numberOfUnreadMessageTypesOnMobile();
    }

}
