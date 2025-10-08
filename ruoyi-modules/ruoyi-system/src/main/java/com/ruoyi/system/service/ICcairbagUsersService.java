package com.ruoyi.system.service;

import com.ruoyi.system.api.domain.ccairbag.CcairbagUsers;

import java.util.List;
import java.util.Map;

/**
 * 用户管理Service接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface ICcairbagUsersService 
{
    /**
     * 查询用户管理
     * 
     * @param userId 用户管理主键
     * @return 用户管理
     */
    public CcairbagUsers selectCcairbagUsersByUserId(Long userId);

    public Map<String, Object> getUsersExt(Long userId);


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
     * 批量删除用户管理
     * 
     * @param userIds 需要删除的用户管理主键集合
     * @return 结果
     */
    public int deleteCcairbagUsersByUserIds(Long[] userIds);

    /**
     * 删除用户管理信息
     * 
     * @param userId 用户管理主键
     * @return 结果
     */
    public int deleteCcairbagUsersByUserId(Long userId);
}
