package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.CcairbagCarBrands;

/**
 * 汽车品牌型号Service接口
 * 
 * @author lidabai
 * @date 2025-03-12
 */
public interface ICcairbagCarBrandsService 
{
    /**
     * 查询汽车品牌型号
     * 
     * @param id 汽车品牌型号主键
     * @return 汽车品牌型号
     */
    public CcairbagCarBrands selectCcairbagCarBrandsById(String id);

    /**
     * 查询汽车品牌型号列表
     * 
     * @param ccairbagCarBrands 汽车品牌型号
     * @return 汽车品牌型号集合
     */
    public List<CcairbagCarBrands> selectCcairbagCarBrandsList(CcairbagCarBrands ccairbagCarBrands);

    /**
     * 新增汽车品牌型号
     * 
     * @param ccairbagCarBrands 汽车品牌型号
     * @return 结果
     */
    public int insertCcairbagCarBrands(CcairbagCarBrands ccairbagCarBrands);

    /**
     * 修改汽车品牌型号
     * 
     * @param ccairbagCarBrands 汽车品牌型号
     * @return 结果
     */
    public int updateCcairbagCarBrands(CcairbagCarBrands ccairbagCarBrands);

    /**
     * 批量删除汽车品牌型号
     * 
     * @param ids 需要删除的汽车品牌型号主键集合
     * @return 结果
     */
    public int deleteCcairbagCarBrandsByIds(String[] ids);

    /**
     * 删除汽车品牌型号信息
     * 
     * @param id 汽车品牌型号主键
     * @return 结果
     */
    public int deleteCcairbagCarBrandsById(String id);
}
