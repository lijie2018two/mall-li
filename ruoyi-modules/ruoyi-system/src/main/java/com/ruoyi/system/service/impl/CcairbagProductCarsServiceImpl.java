package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.system.api.domain.ccairbag.CcairbagProductCars;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CcairbagProductCarsMapper;
import com.ruoyi.system.service.ICcairbagProductCarsService;

/**
 * 商品关联的适用车辆Service业务层处理
 * 
 * @author lidabai
 * @date 2025-03-12
 */
@Service
public class CcairbagProductCarsServiceImpl implements ICcairbagProductCarsService 
{
    @Autowired
    private CcairbagProductCarsMapper ccairbagProductCarsMapper;

    /**
     * 查询商品关联的适用车辆
     * 
     * @param id 商品关联的适用车辆主键
     * @return 商品关联的适用车辆
     */
    @Override
    public CcairbagProductCars selectCcairbagProductCarsById(Long id)
    {
        return ccairbagProductCarsMapper.selectCcairbagProductCarsById(id);
    }

    /**
     * 查询商品关联的适用车辆列表
     * 
     * @param ccairbagProductCars 商品关联的适用车辆
     * @return 商品关联的适用车辆
     */
    @Override
    public List<CcairbagProductCars> selectCcairbagProductCarsList(CcairbagProductCars ccairbagProductCars)
    {
        return ccairbagProductCarsMapper.selectCcairbagProductCarsList(ccairbagProductCars);
    }

    /**
     * 新增商品关联的适用车辆
     * 
     * @param ccairbagProductCars 商品关联的适用车辆
     * @return 结果
     */
    @Override
    public int insertCcairbagProductCars(CcairbagProductCars ccairbagProductCars)
    {
        return ccairbagProductCarsMapper.insertCcairbagProductCars(ccairbagProductCars);
    }

    /**
     * 修改商品关联的适用车辆
     * 
     * @param ccairbagProductCars 商品关联的适用车辆
     * @return 结果
     */
    @Override
    public int updateCcairbagProductCars(CcairbagProductCars ccairbagProductCars)
    {
        return ccairbagProductCarsMapper.updateCcairbagProductCars(ccairbagProductCars);
    }

    /**
     * 批量删除商品关联的适用车辆
     * 
     * @param ids 需要删除的商品关联的适用车辆主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagProductCarsByIds(Long[] ids)
    {
        return ccairbagProductCarsMapper.deleteCcairbagProductCarsByIds(ids);
    }

    /**
     * 删除商品关联的适用车辆信息
     * 
     * @param id 商品关联的适用车辆主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagProductCarsById(Long id)
    {
        return ccairbagProductCarsMapper.deleteCcairbagProductCarsById(id);
    }
}
