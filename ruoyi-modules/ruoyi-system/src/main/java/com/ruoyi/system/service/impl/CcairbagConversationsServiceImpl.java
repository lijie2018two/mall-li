package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagConversations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CcairbagConversationsMapper;
import com.ruoyi.system.service.ICcairbagConversationsService;

/**
 * 消息会话Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
public class CcairbagConversationsServiceImpl implements ICcairbagConversationsService 
{
    @Autowired
    private CcairbagConversationsMapper ccairbagConversationsMapper;

    /**
     * 查询消息会话
     * 
     * @param conversationId 消息会话主键
     * @return 消息会话
     */
    @Override
    public CcairbagConversations selectCcairbagConversationsByConversationId(Long conversationId)
    {
        return ccairbagConversationsMapper.selectCcairbagConversationsByConversationId(conversationId);
    }

    /**
     * 查询消息会话列表
     * 
     * @param ccairbagConversations 消息会话
     * @return 消息会话
     */
    @Override
    public List<CcairbagConversations> selectCcairbagConversationsList(CcairbagConversations ccairbagConversations)
    {
        return ccairbagConversationsMapper.selectCcairbagConversationsList(ccairbagConversations);
    }

    /**
     * 新增消息会话
     * 
     * @param ccairbagConversations 消息会话
     * @return 结果
     */
    @Override
    public int insertCcairbagConversations(CcairbagConversations ccairbagConversations)
    {
        ccairbagConversations.setCreateTime(DateUtils.getNowDate());
        return ccairbagConversationsMapper.insertCcairbagConversations(ccairbagConversations);
    }

    /**
     * 修改消息会话
     * 
     * @param ccairbagConversations 消息会话
     * @return 结果
     */
    @Override
    public int updateCcairbagConversations(CcairbagConversations ccairbagConversations)
    {
        ccairbagConversations.setUpdateTime(DateUtils.getNowDate());
        return ccairbagConversationsMapper.updateCcairbagConversations(ccairbagConversations);
    }

    /**
     * 批量删除消息会话
     * 
     * @param conversationIds 需要删除的消息会话主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagConversationsByConversationIds(Long[] conversationIds)
    {
        return ccairbagConversationsMapper.deleteCcairbagConversationsByConversationIds(conversationIds);
    }

    /**
     * 删除消息会话信息
     * 
     * @param conversationId 消息会话主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagConversationsByConversationId(Long conversationId)
    {
        return ccairbagConversationsMapper.deleteCcairbagConversationsByConversationId(conversationId);
    }
}
