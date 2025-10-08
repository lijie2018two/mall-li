package com.ccairbag.api.mapper;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUsers;
import com.ruoyi.system.api.model.LoginUser;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

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

    public CcairbagUsers getUserInfo(@PathVariable("email") String email);

    public CcairbagUsers addExt(@RequestBody CcairbagUsers ccairbagUsers);



}
