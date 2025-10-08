package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.system.api.domain.ccairbag.CcairbagNotificationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CcairbagNotificationUserMapper;
import com.ruoyi.system.service.ICcairbagNotificationUserService;

/**
 * 用户通知关联Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
public class CcairbagNotificationUserServiceImpl implements ICcairbagNotificationUserService 
{
    @Autowired
    private CcairbagNotificationUserMapper ccairbagNotificationUserMapper;

    /**
     * 查询用户通知关联
     * 
     * @param id 用户通知关联主键
     * @return 用户通知关联
     */
    @Override
    public CcairbagNotificationUser selectCcairbagNotificationUserById(Long id)
    {
        return ccairbagNotificationUserMapper.selectCcairbagNotificationUserById(id);
    }

    /**
     * 查询用户通知关联列表
     * 
     * @param ccairbagNotificationUser 用户通知关联
     * @return 用户通知关联
     */
    @Override
    public List<CcairbagNotificationUser> selectCcairbagNotificationUserList(CcairbagNotificationUser ccairbagNotificationUser)
    {
        return ccairbagNotificationUserMapper.selectCcairbagNotificationUserList(ccairbagNotificationUser);
    }

    /**
     * 新增用户通知关联
     * 
     * @param ccairbagNotificationUser 用户通知关联
     * @return 结果
     */
    @Override
    public int insertCcairbagNotificationUser(CcairbagNotificationUser ccairbagNotificationUser)
    {
        return ccairbagNotificationUserMapper.insertCcairbagNotificationUser(ccairbagNotificationUser);
    }

    /**
     * 修改用户通知关联
     * 
     * @param ccairbagNotificationUser 用户通知关联
     * @return 结果
     */
    @Override
    public int updateCcairbagNotificationUser(CcairbagNotificationUser ccairbagNotificationUser)
    {
        return ccairbagNotificationUserMapper.updateCcairbagNotificationUser(ccairbagNotificationUser);
    }

    /**
     * 批量删除用户通知关联
     * 
     * @param ids 需要删除的用户通知关联主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagNotificationUserByIds(Long[] ids)
    {
        return ccairbagNotificationUserMapper.deleteCcairbagNotificationUserByIds(ids);
    }

    /**
     * 删除用户通知关联信息
     * 
     * @param id 用户通知关联主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagNotificationUserById(Long id)
    {
        return ccairbagNotificationUserMapper.deleteCcairbagNotificationUserById(id);
    }
}
