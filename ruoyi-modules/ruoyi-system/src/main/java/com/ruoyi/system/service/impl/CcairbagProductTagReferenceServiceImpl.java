package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.system.api.domain.ccairbag.CcairbagProductTagReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CcairbagProductTagReferenceMapper;
import com.ruoyi.system.service.ICcairbagProductTagReferenceService;

/**
 * 商品标签关系表Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
public class CcairbagProductTagReferenceServiceImpl implements ICcairbagProductTagReferenceService 
{
    @Autowired
    private CcairbagProductTagReferenceMapper ccairbagProductTagReferenceMapper;

    /**
     * 查询商品标签关系表
     * 
     * @param referenceId 商品标签关系表主键
     * @return 商品标签关系表
     */
    @Override
    public CcairbagProductTagReference selectCcairbagProductTagReferenceByReferenceId(String referenceId)
    {
        return ccairbagProductTagReferenceMapper.selectCcairbagProductTagReferenceByReferenceId(referenceId);
    }

    /**
     * 查询商品标签关系表列表
     * 
     * @param ccairbagProductTagReference 商品标签关系表
     * @return 商品标签关系表
     */
    @Override
    public List<CcairbagProductTagReference> selectCcairbagProductTagReferenceList(CcairbagProductTagReference ccairbagProductTagReference)
    {
        return ccairbagProductTagReferenceMapper.selectCcairbagProductTagReferenceList(ccairbagProductTagReference);
    }

    /**
     * 新增商品标签关系表
     * 
     * @param ccairbagProductTagReference 商品标签关系表
     * @return 结果
     */
    @Override
    public int insertCcairbagProductTagReference(CcairbagProductTagReference ccairbagProductTagReference)
    {
        return ccairbagProductTagReferenceMapper.insertCcairbagProductTagReference(ccairbagProductTagReference);
    }

    /**
     * 修改商品标签关系表
     * 
     * @param ccairbagProductTagReference 商品标签关系表
     * @return 结果
     */
    @Override
    public int updateCcairbagProductTagReference(CcairbagProductTagReference ccairbagProductTagReference)
    {
        return ccairbagProductTagReferenceMapper.updateCcairbagProductTagReference(ccairbagProductTagReference);
    }

    /**
     * 批量删除商品标签关系表
     * 
     * @param referenceIds 需要删除的商品标签关系表主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagProductTagReferenceByReferenceIds(String[] referenceIds)
    {
        return ccairbagProductTagReferenceMapper.deleteCcairbagProductTagReferenceByReferenceIds(referenceIds);
    }

    /**
     * 删除商品标签关系表信息
     * 
     * @param referenceId 商品标签关系表主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagProductTagReferenceByReferenceId(String referenceId)
    {
        return ccairbagProductTagReferenceMapper.deleteCcairbagProductTagReferenceByReferenceId(referenceId);
    }
}
