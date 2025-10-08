package com.ccairbag.api.service.impl;

import com.ccairbag.api.mapper.CcairbagNegotiationRecordMapper;
import com.ccairbag.api.service.ICcairbagNegotiationRecordService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.CcairbagNegotiationRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * 议价记录Service业务层处理
 * 
 * @author lidabai
 * @date 2025-05-12
 */
@Service
public class CcairbagNegotiationRecordServiceImpl implements ICcairbagNegotiationRecordService
{
    @Resource
    private CcairbagNegotiationRecordMapper ccairbagNegotiationRecordMapper;

    /**
     * 查询议价记录
     * 
     * @param id 议价记录主键
     * @return 议价记录
     */
    @Override
    public CcairbagNegotiationRecord selectCcairbagNegotiationRecordById(Long id)
    {
        return ccairbagNegotiationRecordMapper.selectCcairbagNegotiationRecordById(id);
    }

    /**
     * 查询议价记录列表
     * 
     * @param ccairbagNegotiationRecord 议价记录
     * @return 议价记录
     */
    @Override
    public List<CcairbagNegotiationRecord> selectCcairbagNegotiationRecordList(CcairbagNegotiationRecord ccairbagNegotiationRecord)
    {
        return ccairbagNegotiationRecordMapper.selectCcairbagNegotiationRecordList(ccairbagNegotiationRecord);
    }

    /**
     * 新增议价记录
     * 
     * @param ccairbagNegotiationRecord 议价记录
     * @return 结果
     */
    @Override
    public int insertCcairbagNegotiationRecord(CcairbagNegotiationRecord ccairbagNegotiationRecord)
    {
        ccairbagNegotiationRecord.setCreateTime(DateUtils.getNowDate());
        return ccairbagNegotiationRecordMapper.insertCcairbagNegotiationRecord(ccairbagNegotiationRecord);
    }

    /**
     * 修改议价记录
     * 
     * @param ccairbagNegotiationRecord 议价记录
     * @return 结果
     */
    @Override
    public int updateCcairbagNegotiationRecord(CcairbagNegotiationRecord ccairbagNegotiationRecord)
    {
        ccairbagNegotiationRecord.setUpdateTime(DateUtils.getNowDate());
        return ccairbagNegotiationRecordMapper.updateCcairbagNegotiationRecord(ccairbagNegotiationRecord);
    }

    /**
     * 批量删除议价记录
     * 
     * @param ids 需要删除的议价记录主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagNegotiationRecordByIds(Long[] ids)
    {
        return ccairbagNegotiationRecordMapper.deleteCcairbagNegotiationRecordByIds(ids);
    }

    /**
     * 删除议价记录信息
     * 
     * @param id 议价记录主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagNegotiationRecordById(Long id)
    {
        return ccairbagNegotiationRecordMapper.deleteCcairbagNegotiationRecordById(id);
    }

    @Override
    public AppResult cancelNegotiable(Long id) {
        //三天后自动取消议价
        CcairbagNegotiationRecord record = ccairbagNegotiationRecordMapper.selectCcairbagNegotiationRecordById(id);
        if (record.getStatus()!=0){
            return AppResult.error("该议价已处理");
        }
        record.setStatus(4);
        record.setUpdateTime(DateUtils.getNowDate());
        ccairbagNegotiationRecordMapper.updateCcairbagNegotiationRecord(record);
        return AppResult.success("议价记录已过期处理");
    }
}
