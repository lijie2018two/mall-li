package com.ccairbag.api.service.impl;

import com.ccairbag.api.mapper.CcairbagProductLogisticsMapper;
import com.ccairbag.api.service.ICcairbagProductLogisticsService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.domain.CcairbagProductLogistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 物流商品关联Service业务层处理
 * 
 * @author lidabai
 * @date 2025-03-06
 */
@Service
public class CcairbagProductLogisticsServiceImpl implements ICcairbagProductLogisticsService
{
    @Resource
    private CcairbagProductLogisticsMapper ccairbagProductLogisticsMapper;

    /**
     * 查询物流商品关联
     * 
     * @param id 物流商品关联主键
     * @return 物流商品关联
     */
    @Override
    public CcairbagProductLogistics selectCcairbagProductLogisticsById(Long id)
    {
        return ccairbagProductLogisticsMapper.selectCcairbagProductLogisticsById(id);
    }

    /**
     * 查询物流商品关联列表
     * 
     * @param ccairbagProductLogistics 物流商品关联
     * @return 物流商品关联
     */
    @Override
    public List<CcairbagProductLogistics> selectCcairbagProductLogisticsList(CcairbagProductLogistics ccairbagProductLogistics)
    {
        return ccairbagProductLogisticsMapper.selectCcairbagProductLogisticsList(ccairbagProductLogistics);
    }

    /**
     * 新增物流商品关联
     * 
     * @param ccairbagProductLogistics 物流商品关联
     * @return 结果
     */
    @Override
    public int insertCcairbagProductLogistics(CcairbagProductLogistics ccairbagProductLogistics)
    {
        ccairbagProductLogistics.setCreateTime(DateUtils.getNowDate());
        return ccairbagProductLogisticsMapper.insertCcairbagProductLogistics(ccairbagProductLogistics);
    }

    /**
     * 修改物流商品关联
     * 
     * @param ccairbagProductLogistics 物流商品关联
     * @return 结果
     */
    @Override
    public int updateCcairbagProductLogistics(CcairbagProductLogistics ccairbagProductLogistics)
    {
        ccairbagProductLogistics.setUpdateTime(DateUtils.getNowDate());
        return ccairbagProductLogisticsMapper.updateCcairbagProductLogistics(ccairbagProductLogistics);
    }

    /**
     * 批量删除物流商品关联
     * 
     * @param ids 需要删除的物流商品关联主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagProductLogisticsByIds(Long[] ids)
    {
        return ccairbagProductLogisticsMapper.deleteCcairbagProductLogisticsByIds(ids);
    }

    /**
     * 删除物流商品关联信息
     * 
     * @param id 物流商品关联主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagProductLogisticsById(Long id)
    {
        return ccairbagProductLogisticsMapper.deleteCcairbagProductLogisticsById(id);
    }
}
