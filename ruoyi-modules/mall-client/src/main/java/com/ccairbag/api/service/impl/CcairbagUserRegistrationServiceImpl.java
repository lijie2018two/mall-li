package com.ccairbag.api.service.impl;

import java.util.List;

import com.ccairbag.api.mapper.CcairbagUserRegistrationMapper;
import com.ccairbag.api.service.ICcairbagUserRegistrationService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUserRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户信息登记Service业务层处理
 * 
 * @author lidabai
 * @date 2025-03-20
 */
@Service
public class CcairbagUserRegistrationServiceImpl implements ICcairbagUserRegistrationService
{
    @Resource
    private CcairbagUserRegistrationMapper ccairbagUserRegistrationMapper;

    /**
     * 查询用户信息登记
     * 
     * @param id 用户信息登记主键
     * @return 用户信息登记
     */
    @Override
    public CcairbagUserRegistration selectCcairbagUserRegistrationById(Long id)
    {
        return ccairbagUserRegistrationMapper.selectCcairbagUserRegistrationById(id);
    }

    @Override
    public CcairbagUserRegistration getInfoByUserId(Long userId) {
        CcairbagUserRegistration ccairbagUserRegistration = ccairbagUserRegistrationMapper.getInfoByUserId(userId);
        return ccairbagUserRegistration;
    }

    /**
     * 查询用户信息登记列表
     * 
     * @param ccairbagUserRegistration 用户信息登记
     * @return 用户信息登记
     */
    @Override
    public List<CcairbagUserRegistration> selectCcairbagUserRegistrationList(CcairbagUserRegistration ccairbagUserRegistration)
    {
        return ccairbagUserRegistrationMapper.selectCcairbagUserRegistrationList(ccairbagUserRegistration);
    }

    /**
     * 新增用户信息登记
     * 
     * @param ccairbagUserRegistration 用户信息登记
     * @return 结果
     */
    @Override
    public int insertCcairbagUserRegistration(CcairbagUserRegistration ccairbagUserRegistration)
    {
        ccairbagUserRegistration.setCreateTime(DateUtils.getNowDate());
        return ccairbagUserRegistrationMapper.insertCcairbagUserRegistration(ccairbagUserRegistration);
    }

    /**
     * 修改用户信息登记
     * 
     * @param ccairbagUserRegistration 用户信息登记
     * @return 结果
     */
    @Override
    public int updateCcairbagUserRegistration(CcairbagUserRegistration ccairbagUserRegistration)
    {
        ccairbagUserRegistration.setUpdateTime(DateUtils.getNowDate());
        return ccairbagUserRegistrationMapper.updateCcairbagUserRegistration(ccairbagUserRegistration);
    }

    /**
     * 批量删除用户信息登记
     * 
     * @param ids 需要删除的用户信息登记主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagUserRegistrationByIds(Long[] ids)
    {
        return ccairbagUserRegistrationMapper.deleteCcairbagUserRegistrationByIds(ids);
    }

    /**
     * 删除用户信息登记信息
     * 
     * @param id 用户信息登记主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagUserRegistrationById(Long id)
    {
        return ccairbagUserRegistrationMapper.deleteCcairbagUserRegistrationById(id);
    }
}
