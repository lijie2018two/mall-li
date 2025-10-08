package com.ruoyi.system.mapper;

import com.ruoyi.system.api.domain.ccairbag.CcairbagShopBank;

import java.util.List;

/**
 * 绑定的银行卡Mapper接口
 * 
 * @author lidabai
 * @date 2025-04-27
 */
public interface CcairbagShopBankMapper 
{
    /**
     * 查询绑定的银行卡
     * 
     * @param id 绑定的银行卡主键
     * @return 绑定的银行卡
     */
    public CcairbagShopBank selectCcairbagShopBankById(Long id);

    /**
     * 查询绑定的银行卡列表
     * 
     * @param ccairbagShopBank 绑定的银行卡
     * @return 绑定的银行卡集合
     */
    public List<CcairbagShopBank> selectCcairbagShopBankList(CcairbagShopBank ccairbagShopBank);

    /**
     * 新增绑定的银行卡
     * 
     * @param ccairbagShopBank 绑定的银行卡
     * @return 结果
     */
    public int insertCcairbagShopBank(CcairbagShopBank ccairbagShopBank);

    /**
     * 修改绑定的银行卡
     * 
     * @param ccairbagShopBank 绑定的银行卡
     * @return 结果
     */
    public int updateCcairbagShopBank(CcairbagShopBank ccairbagShopBank);

    /**
     * 删除绑定的银行卡
     * 
     * @param id 绑定的银行卡主键
     * @return 结果
     */
    public int deleteCcairbagShopBankById(Long id);

    /**
     * 批量删除绑定的银行卡
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCcairbagShopBankByIds(Long[] ids);
}
