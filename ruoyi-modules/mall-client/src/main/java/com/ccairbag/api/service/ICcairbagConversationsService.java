package com.ccairbag.api.service;

import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.dto.CcairbagConversationsDTO;

/**
 * 消息会话Service接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface ICcairbagConversationsService 
{

    AppResult list();

    AppResult ShopList();

    AppResult sendMessagesByShop(CcairbagConversationsDTO ccairbagConversationsDTO);

    AppResult sendMessagesByOrderId(CcairbagConversationsDTO ccairbagConversationsDTO);


    AppResult sendMessages(CcairbagConversationsDTO ccairbagConversationsDTO);

    AppResult readMessages(CcairbagConversationsDTO ccairbagConversationsDTO);

    AppResult numberOfUnreadMessageTypesOnMobile();


}
