package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.CcairbagProductLogistics;

/**
 * 物流商品关联Service接口
 * 
 * @author lidabai
 * @date 2025-03-06
 */
public interface ICcairbagProductLogisticsService 
{
    /**
     * 查询物流商品关联
     * 
     * @param id 物流商品关联主键
     * @return 物流商品关联
     */
    public CcairbagProductLogistics selectCcairbagProductLogisticsById(Long id);

    /**
     * 查询物流商品关联列表
     * 
     * @param ccairbagProductLogistics 物流商品关联
     * @return 物流商品关联集合
     */
    public List<CcairbagProductLogistics> selectCcairbagProductLogisticsList(CcairbagProductLogistics ccairbagProductLogistics);

    /**
     * 新增物流商品关联
     * 
     * @param ccairbagProductLogistics 物流商品关联
     * @return 结果
     */
    public int insertCcairbagProductLogistics(CcairbagProductLogistics ccairbagProductLogistics);

    /**
     * 修改物流商品关联
     * 
     * @param ccairbagProductLogistics 物流商品关联
     * @return 结果
     */
    public int updateCcairbagProductLogistics(CcairbagProductLogistics ccairbagProductLogistics);

    /**
     * 批量删除物流商品关联
     * 
     * @param ids 需要删除的物流商品关联主键集合
     * @return 结果
     */
    public int deleteCcairbagProductLogisticsByIds(Long[] ids);

    /**
     * 删除物流商品关联信息
     * 
     * @param id 物流商品关联主键
     * @return 结果
     */
    public int deleteCcairbagProductLogisticsById(Long id);
}
