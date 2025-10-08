package com.ccairbag.api.service;

import com.ruoyi.system.api.domain.ccairbag.CcairbagProductCars;

import java.util.List;

/**
 * 商品关联的适用车辆Service接口
 * 
 * @author lidabai
 * @date 2025-03-12
 */
public interface ICcairbagProductCarsService 
{
    /**
     * 查询商品关联的适用车辆
     * 
     * @param id 商品关联的适用车辆主键
     * @return 商品关联的适用车辆
     */
    public CcairbagProductCars selectCcairbagProductCarsById(Long id);

    /**
     * 查询商品关联的适用车辆列表
     * 
     * @param ccairbagProductCars 商品关联的适用车辆
     * @return 商品关联的适用车辆集合
     */
    public List<CcairbagProductCars> selectCcairbagProductCarsList(CcairbagProductCars ccairbagProductCars);

    /**
     * 新增商品关联的适用车辆
     * 
     * @param ccairbagProductCars 商品关联的适用车辆
     * @return 结果
     */
    public int insertCcairbagProductCars(CcairbagProductCars ccairbagProductCars);

    /**
     * 修改商品关联的适用车辆
     * 
     * @param ccairbagProductCars 商品关联的适用车辆
     * @return 结果
     */
    public int updateCcairbagProductCars(CcairbagProductCars ccairbagProductCars);

    /**
     * 批量删除商品关联的适用车辆
     * 
     * @param ids 需要删除的商品关联的适用车辆主键集合
     * @return 结果
     */
    public int deleteCcairbagProductCarsByIds(Long[] ids);

    /**
     * 删除商品关联的适用车辆信息
     * 
     * @param id 商品关联的适用车辆主键
     * @return 结果
     */
    public int deleteCcairbagProductCarsById(Long id);
}
