package com.ruoyi.system.mapper;

import com.ruoyi.system.api.domain.ccairbag.CcairbagUsers;

import java.util.List;

/**
 * 用户管理Mapper接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface CcairbagUsersMapper 
{
    /**
     * 查询用户管理
     * 
     * @param userId 用户管理主键
     * @return 用户管理
     */
    public CcairbagUsers selectCcairbagUsersByUserId(Long userId);

    /**
     * 查询用户管理列表
     * 
     * @param ccairbagUsers 用户管理
     * @return 用户管理集合
     */
    public List<CcairbagUsers> selectCcairbagUsersList(CcairbagUsers ccairbagUsers);

    /**
     * 新增用户管理
     * 
     * @param ccairbagUsers 用户管理
     * @return 结果
     */
    public int insertCcairbagUsers(CcairbagUsers ccairbagUsers);

    /**
     * 修改用户管理
     * 
     * @param ccairbagUsers 用户管理
     * @return 结果
     */
    public int updateCcairbagUsers(CcairbagUsers ccairbagUsers);

    /**
     * 删除用户管理
     * 
     * @param userId 用户管理主键
     * @return 结果
     */
    public int deleteCcairbagUsersByUserId(Long userId);

    /**
     * 批量删除用户管理
     * 
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCcairbagUsersByUserIds(Long[] userIds);
}
