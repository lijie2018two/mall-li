package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagSetscale;
import com.ruoyi.system.api.domain.ccairbag.CcairbagShops;
import com.ruoyi.system.mapper.CcairbagSetscaleMapper;
import com.ruoyi.system.mapper.CcairbagShopsMapper;
import com.ruoyi.system.service.ICcairbagSetscaleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 税收比例

Service业务层处理
 * 
 * @author lidabai
 * @date 2025-04-21
 */
@Service
public class CcairbagSetscaleServiceImpl implements ICcairbagSetscaleService 
{
    @Resource
    private CcairbagSetscaleMapper ccairbagSetscaleMapper;
    @Resource
    private CcairbagShopsMapper ccairbagShopsMapper;


    /**
     * 查询税收比例


     * 
     * @param id 税收比例

主键
     * @return 税收比例


     */
    @Override
    public CcairbagSetscale selectCcairbagSetscaleById(Long id)
    {
        return ccairbagSetscaleMapper.selectCcairbagSetscaleById(id);
    }

    /**
     * 查询税收比例

列表
     * 
     * @param ccairbagSetscale 税收比例


     * @return 税收比例


     */
    @Override
    public List<CcairbagSetscale> selectCcairbagSetscaleList(CcairbagSetscale ccairbagSetscale)
    {
        return ccairbagSetscaleMapper.selectCcairbagSetscaleList(ccairbagSetscale);
    }

    /**
     * 新增税收比例


     * 
     * @param ccairbagSetscale 税收比例


     * @return 结果
     */
    @Override
    public int insertCcairbagSetscale(CcairbagSetscale ccairbagSetscale)
    {
        CcairbagSetscale c = new CcairbagSetscale();
        List<CcairbagSetscale> ccairbagSetscales = ccairbagSetscaleMapper.selectCcairbagSetscaleList(c);
        if (ccairbagSetscales.size() > 0) {
            return 0;
        }else{
            ccairbagSetscale.setCreateTime(DateUtils.getNowDate());
            ccairbagSetscale.setDeleted(0);
            int i  = ccairbagSetscaleMapper.insertCcairbagSetscale(ccairbagSetscale);
            //给所有商店 添加这两个比例值
            CcairbagShops ccairbagShops = new CcairbagShops();
            ccairbagShops.setDealScale(ccairbagSetscale.getDealScale());
            ccairbagShops.setScale(ccairbagSetscale.getScale());
            ccairbagShopsMapper.updateShopsScale(ccairbagShops);
            return i;
        }

    }

    /**
     * 修改税收比例


     * 
     * @param ccairbagSetscale 税收比例


     * @return 结果
     */
    @Override
    public int updateCcairbagSetscale(CcairbagSetscale ccairbagSetscale)
    {

        CcairbagSetscale setscale = ccairbagSetscaleMapper.selectCcairbagSetscaleById(ccairbagSetscale.getId());
        setscale.setUpdateTime(DateUtils.getNowDate());
        setscale.setScale(ccairbagSetscale.getScale());
        setscale.setDealScale(ccairbagSetscale.getDealScale());
        int i  = ccairbagSetscaleMapper.updateCcairbagSetscale(ccairbagSetscale);
        //给所有商店 添加这两个比例值
        CcairbagShops ccairbagShops = new CcairbagShops();
        ccairbagShops.setDealScale(ccairbagSetscale.getDealScale());
        ccairbagShops.setScale(ccairbagSetscale.getScale());
        ccairbagShopsMapper.updateShopsScale(ccairbagShops);
        return i;
    }

    /**
     * 批量删除税收比例


     * 
     * @param ids 需要删除的税收比例

主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagSetscaleByIds(Long[] ids)
    {
        return ccairbagSetscaleMapper.deleteCcairbagSetscaleByIds(ids);
    }

    /**
     * 删除税收比例

信息
     * 
     * @param id 税收比例

主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagSetscaleById(Long id)
    {
        return ccairbagSetscaleMapper.deleteCcairbagSetscaleById(id);
    }
}
