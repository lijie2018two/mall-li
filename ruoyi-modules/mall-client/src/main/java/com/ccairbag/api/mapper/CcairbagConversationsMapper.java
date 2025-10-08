package com.ccairbag.api.mapper;

import com.ruoyi.system.api.domain.ccairbag.CcairbagConversations;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 消息会话Mapper接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface CcairbagConversationsMapper 
{
    /**
     * 查询消息会话
     * 
     * @param conversationId 消息会话主键
     * @return 消息会话
     */
    public CcairbagConversations selectCcairbagConversationsByConversationId(Long conversationId);

    public CcairbagConversations selectConversation(@Param("buyerId")Long buyerId,@Param("sellerId") Long sellerId);

    public List<CcairbagConversations> selectConversationsList(CcairbagConversations ccairbagConversations);

    /**
     * 查询消息会话列表
     * 
     * @param ccairbagConversations 消息会话
     * @return 消息会话集合
     */

    public List<CcairbagConversations> selectBuyerConversationsList(CcairbagConversations ccairbagConversations);

    public List<CcairbagConversations> selectBuyerConversationsListExt(CcairbagConversations ccairbagConversations);

    public List<CcairbagConversations> selectSellerConversationsList(CcairbagConversations ccairbagConversations);
    public List<CcairbagConversations> selectSellerConversationsListExt(CcairbagConversations ccairbagConversations);

    List<Map<String, Object>> getUserUnreadMessageCount(Long userId);

    List<Map<String, Object>> getUserUnreadMessageCountExt(Long userId);

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
     * 删除消息会话
     * 
     * @param conversationId 消息会话主键
     * @return 结果
     */
    public int deleteCcairbagConversationsByConversationId(Long conversationId);

    /**
     * 批量删除消息会话
     * 
     * @param conversationIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCcairbagConversationsByConversationIds(Long[] conversationIds);
}
