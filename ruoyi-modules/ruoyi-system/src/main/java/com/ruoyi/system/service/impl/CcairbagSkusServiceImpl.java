package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagSkus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CcairbagSkusMapper;
import com.ruoyi.system.service.ICcairbagSkusService;

/**
 * skuService业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
public class CcairbagSkusServiceImpl implements ICcairbagSkusService 
{
    @Autowired
    private CcairbagSkusMapper ccairbagSkusMapper;

    /**
     * 查询sku
     * 
     * @param skuId sku主键
     * @return sku
     */
    @Override
    public CcairbagSkus selectCcairbagSkusBySkuId(Long skuId)
    {
        return ccairbagSkusMapper.selectCcairbagSkusBySkuId(skuId);
    }

    /**
     * 查询sku列表
     * 
     * @param ccairbagSkus sku
     * @return sku
     */
    @Override
    public List<CcairbagSkus> selectCcairbagSkusList(CcairbagSkus ccairbagSkus)
    {
        return ccairbagSkusMapper.selectCcairbagSkusList(ccairbagSkus);
    }

    /**
     * 新增sku
     * 
     * @param ccairbagSkus sku
     * @return 结果
     */
    @Override
    public int insertCcairbagSkus(CcairbagSkus ccairbagSkus)
    {
        ccairbagSkus.setCreateTime(DateUtils.getNowDate());
        return ccairbagSkusMapper.insertCcairbagSkus(ccairbagSkus);
    }

    /**
     * 修改sku
     * 
     * @param ccairbagSkus sku
     * @return 结果
     */
    @Override
    public int updateCcairbagSkus(CcairbagSkus ccairbagSkus)
    {
        ccairbagSkus.setUpdateTime(DateUtils.getNowDate());
        return ccairbagSkusMapper.updateCcairbagSkus(ccairbagSkus);
    }

    /**
     * 批量删除sku
     * 
     * @param skuIds 需要删除的sku主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagSkusBySkuIds(Long[] skuIds)
    {
        return ccairbagSkusMapper.deleteCcairbagSkusBySkuIds(skuIds);
    }

    /**
     * 删除sku信息
     * 
     * @param skuId sku主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagSkusBySkuId(Long skuId)
    {
        return ccairbagSkusMapper.deleteCcairbagSkusBySkuId(skuId);
    }
}
