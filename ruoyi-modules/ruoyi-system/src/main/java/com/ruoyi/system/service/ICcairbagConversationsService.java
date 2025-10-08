package com.ruoyi.system.service;

import com.ruoyi.system.api.domain.ccairbag.CcairbagConversations;

import java.util.List;

/**
 * 消息会话Service接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface ICcairbagConversationsService 
{
    /**
     * 查询消息会话
     * 
     * @param conversationId 消息会话主键
     * @return 消息会话
     */
    public CcairbagConversations selectCcairbagConversationsByConversationId(Long conversationId);

    /**
     * 查询消息会话列表
     * 
     * @param ccairbagConversations 消息会话
     * @return 消息会话集合
     */
    public List<CcairbagConversations> selectCcairbagConversationsList(CcairbagConversations ccairbagConversations);

    /**
     * 新增消息会话
     * 
     * @param ccairbagConversations 消息会话
     * @return 结果
     */
    public int insertCcairbagConversations(CcairbagConversations ccairbagConversations);

    /**
     * 修改消息会话
     * 
     * @param ccairbagConversations 消息会话
     * @return 结果
     */
    public int updateCcairbagConversations(CcairbagConversations ccairbagConversations);

    /**
     * 批量删除消息会话
     * 
     * @param conversationIds 需要删除的消息会话主键集合
     * @return 结果
     */
    public int deleteCcairbagConversationsByConversationIds(Long[] conversationIds);

    /**
     * 删除消息会话信息
     * 
     * @param conversationId 消息会话主键
     * @return 结果
     */
    public int deleteCcairbagConversationsByConversationId(Long conversationId);
}
