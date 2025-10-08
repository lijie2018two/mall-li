package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.CcairbagIncomeRecords;

/**
 * 收入支出记录Mapper接口
 * 
 * @author lidabai
 * @date 2025-03-20
 */
public interface CcairbagIncomeRecordsMapper 
{
    /**
     * 查询收入支出记录
     * 
     * @param id 收入支出记录主键
     * @return 收入支出记录
     */
    public CcairbagIncomeRecords selectCcairbagIncomeRecordsById(Long id);

    /**
     * 查询收入支出记录列表
     * 
     * @param ccairbagIncomeRecords 收入支出记录
     * @return 收入支出记录集合
     */
    public List<CcairbagIncomeRecords> selectCcairbagIncomeRecordsList(CcairbagIncomeRecords ccairbagIncomeRecords);

    /**
     * 新增收入支出记录
     * 
     * @param ccairbagIncomeRecords 收入支出记录
     * @return 结果
     */
    public int insertCcairbagIncomeRecords(CcairbagIncomeRecords ccairbagIncomeRecords);

    /**
     * 修改收入支出记录
     * 
     * @param ccairbagIncomeRecords 收入支出记录
     * @return 结果
     */
    public int updateCcairbagIncomeRecords(CcairbagIncomeRecords ccairbagIncomeRecords);

    /**
     * 删除收入支出记录
     * 
     * @param id 收入支出记录主键
     * @return 结果
     */
    public int deleteCcairbagIncomeRecordsById(Long id);

    /**
     * 批量删除收入支出记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCcairbagIncomeRecordsByIds(Long[] ids);
}
