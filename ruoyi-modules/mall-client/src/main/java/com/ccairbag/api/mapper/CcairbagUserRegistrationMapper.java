package com.ccairbag.api.mapper;

import com.ruoyi.system.api.domain.ccairbag.CcairbagUserRegistration;

import java.util.List;

/**
 * 用户信息登记Mapper接口
 * 
 * @author lidabai
 * @date 2025-03-20
 */
public interface CcairbagUserRegistrationMapper 
{
    /**
     * 查询用户信息登记
     * 
     * @param id 用户信息登记主键
     * @return 用户信息登记
     */
    public CcairbagUserRegistration selectCcairbagUserRegistrationById(Long id);

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
     * 删除用户信息登记
     * 
     * @param id 用户信息登记主键
     * @return 结果
     */
    public int deleteCcairbagUserRegistrationById(Long id);

    /**
     * 批量删除用户信息登记
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCcairbagUserRegistrationByIds(Long[] ids);

    CcairbagUserRegistration getInfoByUserId(Long userId);
}
