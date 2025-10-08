package com.ccairbag.api.service;

import com.ruoyi.system.api.domain.ccairbag.CcairbagProductTagReference;

import java.util.List;

/**
 * 商品标签关系表Service接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface ICcairbagProductTagReferenceService 
{
    /**
     * 查询商品标签关系表
     * 
     * @param referenceId 商品标签关系表主键
     * @return 商品标签关系表
     */
    public CcairbagProductTagReference selectCcairbagProductTagReferenceByReferenceId(String referenceId);

    /**
     * 查询商品标签关系表列表
     * 
     * @param ccairbagProductTagReference 商品标签关系表
     * @return 商品标签关系表集合
     */
    public List<CcairbagProductTagReference> selectCcairbagProductTagReferenceList(CcairbagProductTagReference ccairbagProductTagReference);

    /**
     * 新增商品标签关系表
     * 
     * @param ccairbagProductTagReference 商品标签关系表
     * @return 结果
     */
    public int insertCcairbagProductTagReference(CcairbagProductTagReference ccairbagProductTagReference);

    /**
     * 修改商品标签关系表
     * 
     * @param ccairbagProductTagReference 商品标签关系表
     * @return 结果
     */
    public int updateCcairbagProductTagReference(CcairbagProductTagReference ccairbagProductTagReference);

    /**
     * 批量删除商品标签关系表
     * 
     * @param referenceIds 需要删除的商品标签关系表主键集合
     * @return 结果
     */
    public int deleteCcairbagProductTagReferenceByReferenceIds(String[] referenceIds);

    /**
     * 删除商品标签关系表信息
     * 
     * @param referenceId 商品标签关系表主键
     * @return 结果
     */
    public int deleteCcairbagProductTagReferenceByReferenceId(String referenceId);
}
