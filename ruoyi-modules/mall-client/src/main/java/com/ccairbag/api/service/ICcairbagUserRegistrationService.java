package com.ccairbag.api.service;

import com.ruoyi.system.api.domain.ccairbag.CcairbagUserRegistration;

import java.util.List;

/**
 * 用户信息登记Service接口
 * 
 * @author lidabai
 * @date 2025-03-20
 */
public interface ICcairbagUserRegistrationService 
{
    /**
     * 查询用户信息登记
     * 
     * @param id 用户信息登记主键
     * @return 用户信息登记
     */
    public CcairbagUserRegistration selectCcairbagUserRegistrationById(Long id);
    CcairbagUserRegistration getInfoByUserId(Long userId);
    /**
     * 查询用户信息登记列表
     * 
     * @param ccairbagUserRegistration 用户信息登记
     * @return 用户信息登记集合
     */
    public List<CcairbagUserRegistration> selectCcairbagUserRegistrationList(CcairbagUserRegistration ccairbagUserRegistration);

    /**
     * 新增用户信息登记
     * 
     * @param ccairbagUserRegistration 用户信息登记
     * @return 结果
     */
    public int insertCcairbagUserRegistration(CcairbagUserRegistration ccairbagUserRegistration);

    /**
     * 修改用户信息登记
     * 
     * @param ccairbagUserRegistration 用户信息登记
     * @return 结果
     */
    public int updateCcairbagUserRegistration(CcairbagUserRegistration ccairbagUserRegistration);

    /**
     * 批量删除用户信息登记
     * 
     * @param ids 需要删除的用户信息登记主键集合
     * @return 结果
     */
    public int deleteCcairbagUserRegistrationByIds(Long[] ids);

    /**
     * 删除用户信息登记信息
     * 
     * @param id 用户信息登记主键
     * @return 结果
     */
    public int deleteCcairbagUserRegistrationById(Long id);
}
