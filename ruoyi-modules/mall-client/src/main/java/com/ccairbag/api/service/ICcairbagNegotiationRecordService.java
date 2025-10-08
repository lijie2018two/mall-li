package com.ccairbag.api.service;

import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.CcairbagNegotiationRecord;

import java.util.List;

/**
 * 议价记录Service接口
 * 
 * @author lidabai
 * @date 2025-05-12
 */
public interface ICcairbagNegotiationRecordService 
{
    /**
     * 查询议价记录
     * 
     * @param id 议价记录主键
     * @return 议价记录
     */
    public CcairbagNegotiationRecord selectCcairbagNegotiationRecordById(Long id);

    /**
     * 查询议价记录列表
     * 
     * @param ccairbagNegotiationRecord 议价记录
     * @return 议价记录集合
     */
    public List<CcairbagNegotiationRecord> selectCcairbagNegotiationRecordList(CcairbagNegotiationRecord ccairbagNegotiationRecord);

    /**
     * 新增议价记录
     * 
     * @param ccairbagNegotiationRecord 议价记录
     * @return 结果
     */
    public int insertCcairbagNegotiationRecord(CcairbagNegotiationRecord ccairbagNegotiationRecord);

    /**
     * 修改议价记录
     * 
     * @param ccairbagNegotiationRecord 议价记录
     * @return 结果
     */
    public int updateCcairbagNegotiationRecord(CcairbagNegotiationRecord ccairbagNegotiationRecord);

    /**
     * 批量删除议价记录
     * 
     * @param ids 需要删除的议价记录主键集合
     * @return 结果
     */
    public int deleteCcairbagNegotiationRecordByIds(Long[] ids);

    /**
     * 删除议价记录信息
     * 
     * @param id 议价记录主键
     * @return 结果
     */
    public int deleteCcairbagNegotiationRecordById(Long id);


    AppResult cancelNegotiable(Long id);
}
