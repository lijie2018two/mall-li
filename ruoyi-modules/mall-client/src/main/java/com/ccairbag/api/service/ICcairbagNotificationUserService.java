package com.ccairbag.api.service;

import com.ruoyi.system.api.domain.ccairbag.CcairbagNotificationUser;

import java.util.List;

/**
 * 用户通知关联Service接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface ICcairbagNotificationUserService 
{
    /**
     * 查询用户通知关联
     * 
     * @param id 用户通知关联主键
     * @return 用户通知关联
     */
    public CcairbagNotificationUser selectCcairbagNotificationUserById(Long id);

    /**
     * 查询用户通知关联列表
     * 
     * @param ccairbagNotificationUser 用户通知关联
     * @return 用户通知关联集合
     */
    public List<CcairbagNotificationUser> selectCcairbagNotificationUserList(CcairbagNotificationUser ccairbagNotificationUser);

    /**
     * 新增用户通知关联
     * 
     * @param ccairbagNotificationUser 用户通知关联
     * @return 结果
     */
    public int insertCcairbagNotificationUser(CcairbagNotificationUser ccairbagNotificationUser);

    /**
     * 修改用户通知关联
     * 
     * @param ccairbagNotificationUser 用户通知关联
     * @return 结果
     */
    public int updateCcairbagNotificationUser(CcairbagNotificationUser ccairbagNotificationUser);

    /**
     * 批量删除用户通知关联
     * 
     * @param ids 需要删除的用户通知关联主键集合
     * @return 结果
     */
    public int deleteCcairbagNotificationUserByIds(Long[] ids);

    /**
     * 删除用户通知关联信息
     * 
     * @param id 用户通知关联主键
     * @return 结果
     */
    public int deleteCcairbagNotificationUserById(Long id);
}
