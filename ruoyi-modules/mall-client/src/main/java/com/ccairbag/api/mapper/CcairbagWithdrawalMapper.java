package com.ccairbag.api.mapper;


import com.ruoyi.system.api.domain.ccairbag.CcairbagWithdrawal;

import java.math.BigDecimal;
import java.util.List;

/**
 * 提现记录Mapper接口
 * 
 * @author lidabai
 * @date 2025-04-27
 */
public interface CcairbagWithdrawalMapper 
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

    public List<CcairbagWithdrawal> selectListByShopId(Long shopId);

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
    public int updateCcairbagWithdrawal(CcairbagWithdrawal ccairbagWithdrawal);

    /**
     * 删除提现记录
     * 
     * @param id 提现记录主键
     * @return 结果
     */
    public int deleteCcairbagWithdrawalById(Long id);

    /**
     * 批量删除提现记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCcairbagWithdrawalByIds(Long[] ids);

    BigDecimal getWithdrawalMoneyByShopId(Long shopId);
}
