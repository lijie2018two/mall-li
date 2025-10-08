package com.ruoyi.system.mapper;

import com.ruoyi.system.api.domain.ccairbag.CcairbagMessages;

import java.util.List;

/**
 * 消息Mapper接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface CcairbagMessagesMapper 
{
    /**
     * 查询消息
     * 
     * @param messageId 消息主键
     * @return 消息
     */
    public CcairbagMessages selectCcairbagMessagesByMessageId(Long messageId);

    /**
     * 查询消息列表
     * 
     * @param ccairbagMessages 消息
     * @return 消息集合
     */
    public List<CcairbagMessages> selectCcairbagMessagesList(CcairbagMessages ccairbagMessages);

    /**
     * 新增消息
     * 
     * @param ccairbagMessages 消息
     * @return 结果
     */
    public int insertCcairbagMessages(CcairbagMessages ccairbagMessages);

    /**
     * 修改消息
     * 
     * @param ccairbagMessages 消息
     * @return 结果
     */
    public int updateCcairbagMessages(CcairbagMessages ccairbagMessages);

    /**
     * 删除消息
     * 
     * @param messageId 消息主键
     * @return 结果
     */
    public int deleteCcairbagMessagesByMessageId(Long messageId);

    /**
     * 批量删除消息
     * 
     * @param messageIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCcairbagMessagesByMessageIds(Long[] messageIds);
}
