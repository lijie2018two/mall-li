package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CcairbagMessagesMapper;
import com.ruoyi.system.service.ICcairbagMessagesService;

/**
 * 消息Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
public class CcairbagMessagesServiceImpl implements ICcairbagMessagesService 
{
    @Autowired
    private CcairbagMessagesMapper ccairbagMessagesMapper;

    /**
     * 查询消息
     * 
     * @param messageId 消息主键
     * @return 消息
     */
    @Override
    public CcairbagMessages selectCcairbagMessagesByMessageId(Long messageId)
    {
        return ccairbagMessagesMapper.selectCcairbagMessagesByMessageId(messageId);
    }

    /**
     * 查询消息列表
     * 
     * @param ccairbagMessages 消息
     * @return 消息
     */
    @Override
    public List<CcairbagMessages> selectCcairbagMessagesList(CcairbagMessages ccairbagMessages)
    {
        return ccairbagMessagesMapper.selectCcairbagMessagesList(ccairbagMessages);
    }

    /**
     * 新增消息
     * 
     * @param ccairbagMessages 消息
     * @return 结果
     */
    @Override
    public int insertCcairbagMessages(CcairbagMessages ccairbagMessages)
    {
        ccairbagMessages.setCreateTime(DateUtils.getNowDate());
        return ccairbagMessagesMapper.insertCcairbagMessages(ccairbagMessages);
    }

    /**
     * 修改消息
     * 
     * @param ccairbagMessages 消息
     * @return 结果
     */
    @Override
    public int updateCcairbagMessages(CcairbagMessages ccairbagMessages)
    {
        ccairbagMessages.setUpdateTime(DateUtils.getNowDate());
        return ccairbagMessagesMapper.updateCcairbagMessages(ccairbagMessages);
    }

    /**
     * 批量删除消息
     * 
     * @param messageIds 需要删除的消息主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagMessagesByMessageIds(Long[] messageIds)
    {
        return ccairbagMessagesMapper.deleteCcairbagMessagesByMessageIds(messageIds);
    }

    /**
     * 删除消息信息
     * 
     * @param messageId 消息主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagMessagesByMessageId(Long messageId)
    {
        return ccairbagMessagesMapper.deleteCcairbagMessagesByMessageId(messageId);
    }
}
