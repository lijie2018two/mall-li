package com.ruoyi.system.mapper;

import com.ruoyi.system.api.domain.ccairbag.CcairbagSetscale;

import java.util.List;

/**
 * 税收比例

Mapper接口
 * 
 * @author lidabai
 * @date 2025-04-21
 */
public interface CcairbagSetscaleMapper 
{
    /**
     * 查询税收比例


     * 
     * @param id 税收比例

主键
     * @return 税收比例


     */
    public CcairbagSetscale selectCcairbagSetscaleById(Long id);

    /**
     * 查询税收比例

列表
     * 
     * @param ccairbagSetscale 税收比例


     * @return 税收比例

集合
     */
    public List<CcairbagSetscale> selectCcairbagSetscaleList(CcairbagSetscale ccairbagSetscale);

    /**
     * 新增税收比例


     * 
     * @param ccairbagSetscale 税收比例


     * @return 结果
     */
    public int insertCcairbagSetscale(CcairbagSetscale ccairbagSetscale);

    /**
     * 修改税收比例


     * 
     * @param ccairbagSetscale 税收比例


     * @return 结果
     */
    public int updateCcairbagSetscale(CcairbagSetscale ccairbagSetscale);

    /**
     * 删除税收比例


     * 
     * @param id 税收比例

主键
     * @return 结果
     */
    public int deleteCcairbagSetscaleById(Long id);

    /**
     * 批量删除税收比例


     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCcairbagSetscaleByIds(Long[] ids);
}
