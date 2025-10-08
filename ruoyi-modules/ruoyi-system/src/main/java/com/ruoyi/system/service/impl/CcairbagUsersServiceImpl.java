package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagShops;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUsers;
import com.ruoyi.system.mapper.CcairbagShopsMapper;
import com.ruoyi.system.mapper.CcairbagUsersMapper;
import com.ruoyi.system.service.ICcairbagUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
public class CcairbagUsersServiceImpl implements ICcairbagUsersService 
{
    @Autowired
    private CcairbagUsersMapper ccairbagUsersMapper;
    @Resource
    private CcairbagShopsMapper ccairbagShopsMapper;
    /**
     * 查询用户管理
     * 
     * @param userId 用户管理主键
     * @return 用户管理
     */
    @Override
    public CcairbagUsers selectCcairbagUsersByUserId(Long userId)
    {
        return ccairbagUsersMapper.selectCcairbagUsersByUserId(userId);
    }

    @Override
    public Map<String, Object> getUsersExt(Long userId) {
        CcairbagShops shops = ccairbagShopsMapper.getShopByUserId(userId);
        if (oConvertUtils.isEmpty(shops)){
            return null;
        }
        CcairbagUsers users = ccairbagUsersMapper.selectCcairbagUsersByUserId(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("shop", shops);
        map.put("users", users);
        return map;
    }

    /**
     * 查询用户管理列表
     * 
     * @param ccairbagUsers 用户管理
     * @return 用户管理
     */
    @Override
    public List<CcairbagUsers> selectCcairbagUsersList(CcairbagUsers ccairbagUsers)
    {
        return ccairbagUsersMapper.selectCcairbagUsersList(ccairbagUsers);
    }

    /**
     * 新增用户管理
     * 
     * @param ccairbagUsers 用户管理
     * @return 结果
     */
    @Override
    public int insertCcairbagUsers(CcairbagUsers ccairbagUsers)
    {
        ccairbagUsers.setCreateTime(DateUtils.getNowDate());
        return ccairbagUsersMapper.insertCcairbagUsers(ccairbagUsers);
    }

    /**
     * 修改用户管理
     * 
     * @param ccairbagUsers 用户管理
     * @return 结果
     */
    @Override
    public int updateCcairbagUsers(CcairbagUsers ccairbagUsers)
    {
        ccairbagUsers.setUpdateTime(DateUtils.getNowDate());
        return ccairbagUsersMapper.updateCcairbagUsers(ccairbagUsers);
    }



    /**
     * 批量删除用户管理
     * 
     * @param userIds 需要删除的用户管理主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagUsersByUserIds(Long[] userIds)
    {
        return ccairbagUsersMapper.deleteCcairbagUsersByUserIds(userIds);
    }

    /**
     * 删除用户管理信息
     * 
     * @param userId 用户管理主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagUsersByUserId(Long userId)
    {
        return ccairbagUsersMapper.deleteCcairbagUsersByUserId(userId);
    }
}
