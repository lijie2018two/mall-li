package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagShops;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CcairbagShopsMapper;
import com.ruoyi.system.service.ICcairbagShopsService;

/**
 * 店铺Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
public class CcairbagShopsServiceImpl implements ICcairbagShopsService 
{
    @Autowired
    private CcairbagShopsMapper ccairbagShopsMapper;

    /**
     * 查询店铺
     * 
     * @param shopId 店铺主键
     * @return 店铺
     */
    @Override
    public CcairbagShops selectCcairbagShopsByShopId(Long shopId)
    {
        return ccairbagShopsMapper.selectCcairbagShopsByShopId(shopId);
    }

    /**
     * 查询店铺列表
     * 
     * @param ccairbagShops 店铺
     * @return 店铺
     */
    @Override
    public List<CcairbagShops> selectCcairbagShopsList(CcairbagShops ccairbagShops)
    {
        return ccairbagShopsMapper.selectCcairbagShopsList(ccairbagShops);
    }

    /**
     * 新增店铺
     * 
     * @param ccairbagShops 店铺
     * @return 结果
     */
    @Override
    public int insertCcairbagShops(CcairbagShops ccairbagShops)
    {
        ccairbagShops.setCreateTime(DateUtils.getNowDate());
        return ccairbagShopsMapper.insertCcairbagShops(ccairbagShops);
    }

    /**
     * 修改店铺
     * 
     * @param ccairbagShops 店铺
     * @return 结果
     */
    @Override
    public int updateCcairbagShops(CcairbagShops ccairbagShops)
    {
        ccairbagShops.setUpdateTime(DateUtils.getNowDate());
        return ccairbagShopsMapper.updateCcairbagShops(ccairbagShops);
    }

    /**
     * 批量删除店铺
     * 
     * @param shopIds 需要删除的店铺主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagShopsByShopIds(Long[] shopIds)
    {
        return ccairbagShopsMapper.deleteCcairbagShopsByShopIds(shopIds);
    }

    /**
     * 删除店铺信息
     * 
     * @param shopId 店铺主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagShopsByShopId(Long shopId)
    {
        return ccairbagShopsMapper.deleteCcairbagShopsByShopId(shopId);
    }
}
