package com.ruoyi.system.service;

import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.domain.ccairbag.CcairbagWithdrawal;

import java.util.List;

/**
 * 提现记录Service接口
 * 
 * @author lidabai
 * @date 2025-04-27
 */
public interface ICcairbagWithdrawalService 
{
    /**
     * 查询提现记录
     * 
     * @param id 提现记录主键
     * @return 提现记录
     */
    public CcairbagWithdrawal selectCcairbagWithdrawalById(Long id);

    /**
     * 查询提现记录列表
     * 
     * @param ccairbagWithdrawal 提现记录
     * @return 提现记录集合
     */
    public List<CcairbagWithdrawal> selectCcairbagWithdrawalList(CcairbagWithdrawal ccairbagWithdrawal);

    /**
     * 新增提现记录
     * 
     * @param ccairbagWithdrawal 提现记录
     * @return 结果
     */
    public int insertCcairbagWithdrawal(CcairbagWithdrawal ccairbagWithdrawal);

    /**
     * 修改提现记录
     * 
     * @param ccairbagWithdrawal 提现记录
     * @return 结果
     */
    public AjaxResult updateCcairbagWithdrawal(CcairbagWithdrawal ccairbagWithdrawal);

    /**
     * 批量删除提现记录
     * 
     * @param ids 需要删除的提现记录主键集合
     * @return 结果
     */
    public int deleteCcairbagWithdrawalByIds(Long[] ids);

    /**
     * 删除提现记录信息
     * 
     * @param id 提现记录主键
     * @return 结果
     */
    public int deleteCcairbagWithdrawalById(Long id);
}
