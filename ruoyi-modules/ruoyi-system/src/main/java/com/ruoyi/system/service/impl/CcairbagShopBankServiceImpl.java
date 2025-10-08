package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagShopBank;
import com.ruoyi.system.mapper.CcairbagShopBankMapper;
import com.ruoyi.system.service.ICcairbagShopBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 绑定的银行卡Service业务层处理
 * 
 * @author lidabai
 * @date 2025-04-27
 */
@Service
public class CcairbagShopBankServiceImpl implements ICcairbagShopBankService 
{
    @Autowired
    private CcairbagShopBankMapper ccairbagShopBankMapper;

    /**
     * 查询绑定的银行卡
     * 
     * @param id 绑定的银行卡主键
     * @return 绑定的银行卡
     */
    @Override
    public CcairbagShopBank selectCcairbagShopBankById(Long id)
    {
        return ccairbagShopBankMapper.selectCcairbagShopBankById(id);
    }

    /**
     * 查询绑定的银行卡列表
     * 
     * @param ccairbagShopBank 绑定的银行卡
     * @return 绑定的银行卡
     */
    @Override
    public List<CcairbagShopBank> selectCcairbagShopBankList(CcairbagShopBank ccairbagShopBank)
    {
        return ccairbagShopBankMapper.selectCcairbagShopBankList(ccairbagShopBank);
    }

    /**
     * 新增绑定的银行卡
     * 
     * @param ccairbagShopBank 绑定的银行卡
     * @return 结果
     */
    @Override
    public int insertCcairbagShopBank(CcairbagShopBank ccairbagShopBank)
    {
        ccairbagShopBank.setCreateTime(DateUtils.getNowDate());
        return ccairbagShopBankMapper.insertCcairbagShopBank(ccairbagShopBank);
    }

    /**
     * 修改绑定的银行卡
     * 
     * @param ccairbagShopBank 绑定的银行卡
     * @return 结果
     */
    @Override
    public int updateCcairbagShopBank(CcairbagShopBank ccairbagShopBank)
    {
        ccairbagShopBank.setUpdateTime(DateUtils.getNowDate());
        return ccairbagShopBankMapper.updateCcairbagShopBank(ccairbagShopBank);
    }

    /**
     * 批量删除绑定的银行卡
     * 
     * @param ids 需要删除的绑定的银行卡主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagShopBankByIds(Long[] ids)
    {
        return ccairbagShopBankMapper.deleteCcairbagShopBankByIds(ids);
    }

    /**
     * 删除绑定的银行卡信息
     * 
     * @param id 绑定的银行卡主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagShopBankById(Long id)
    {
        return ccairbagShopBankMapper.deleteCcairbagShopBankById(id);
    }
}
