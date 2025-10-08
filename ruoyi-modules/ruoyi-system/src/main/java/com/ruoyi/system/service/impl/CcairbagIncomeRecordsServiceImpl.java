package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CcairbagIncomeRecordsMapper;
import com.ruoyi.system.domain.CcairbagIncomeRecords;
import com.ruoyi.system.service.ICcairbagIncomeRecordsService;

/**
 * 收入支出记录Service业务层处理
 * 
 * @author lidabai
 * @date 2025-03-20
 */
@Service
public class CcairbagIncomeRecordsServiceImpl implements ICcairbagIncomeRecordsService 
{
    @Autowired
    private CcairbagIncomeRecordsMapper ccairbagIncomeRecordsMapper;

    /**
     * 查询收入支出记录
     * 
     * @param id 收入支出记录主键
     * @return 收入支出记录
     */
    @Override
    public CcairbagIncomeRecords selectCcairbagIncomeRecordsById(Long id)
    {
        return ccairbagIncomeRecordsMapper.selectCcairbagIncomeRecordsById(id);
    }

    /**
     * 查询收入支出记录列表
     * 
     * @param ccairbagIncomeRecords 收入支出记录
     * @return 收入支出记录
     */
    @Override
    public List<CcairbagIncomeRecords> selectCcairbagIncomeRecordsList(CcairbagIncomeRecords ccairbagIncomeRecords)
    {
        return ccairbagIncomeRecordsMapper.selectCcairbagIncomeRecordsList(ccairbagIncomeRecords);
    }

    /**
     * 新增收入支出记录
     * 
     * @param ccairbagIncomeRecords 收入支出记录
     * @return 结果
     */
    @Override
    public int insertCcairbagIncomeRecords(CcairbagIncomeRecords ccairbagIncomeRecords)
    {
        ccairbagIncomeRecords.setCreateTime(DateUtils.getNowDate());
        return ccairbagIncomeRecordsMapper.insertCcairbagIncomeRecords(ccairbagIncomeRecords);
    }

    /**
     * 修改收入支出记录
     * 
     * @param ccairbagIncomeRecords 收入支出记录
     * @return 结果
     */
    @Override
    public int updateCcairbagIncomeRecords(CcairbagIncomeRecords ccairbagIncomeRecords)
    {
        ccairbagIncomeRecords.setUpdateTime(DateUtils.getNowDate());
        return ccairbagIncomeRecordsMapper.updateCcairbagIncomeRecords(ccairbagIncomeRecords);
    }

    /**
     * 批量删除收入支出记录
     * 
     * @param ids 需要删除的收入支出记录主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagIncomeRecordsByIds(Long[] ids)
    {
        return ccairbagIncomeRecordsMapper.deleteCcairbagIncomeRecordsByIds(ids);
    }

    /**
     * 删除收入支出记录信息
     * 
     * @param id 收入支出记录主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagIncomeRecordsById(Long id)
    {
        return ccairbagIncomeRecordsMapper.deleteCcairbagIncomeRecordsById(id);
    }
}
