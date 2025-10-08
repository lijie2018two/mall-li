package com.ccairbag.api.mapper;

import com.ruoyi.system.api.domain.ccairbag.CcairbagNegotiationRecord;
import com.ruoyi.system.api.domain.ccairbag.dto.CcairbagNegotiationRecordDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 议价记录Mapper接口
 * 
 * @author lidabai
 * @date 2025-05-12
 */
public interface CcairbagNegotiationRecordMapper 
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

    //不同同时发起两个议价
    public int checkNegotiableMoney(@Param("buyerId") Long buyerId,@Param("productId") Long productId);

    public List<CcairbagNegotiationRecordDTO> listNegotiableMoney(Long shopId);

    public List<CcairbagNegotiationRecordDTO> buyerListNegotiableMoney(Long buyerId);


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
     * 删除议价记录
     * 
     * @param id 议价记录主键
     * @return 结果
     */
    public int deleteCcairbagNegotiationRecordById(Long id);

    /**
     * 批量删除议价记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCcairbagNegotiationRecordByIds(Long[] ids);
}
