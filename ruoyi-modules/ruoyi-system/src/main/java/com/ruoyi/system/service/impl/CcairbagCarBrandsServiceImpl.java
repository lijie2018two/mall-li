package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CcairbagCarBrandsMapper;
import com.ruoyi.system.domain.CcairbagCarBrands;
import com.ruoyi.system.service.ICcairbagCarBrandsService;

/**
 * 汽车品牌型号Service业务层处理
 * 
 * @author lidabai
 * @date 2025-03-12
 */
@Service
public class CcairbagCarBrandsServiceImpl implements ICcairbagCarBrandsService 
{
    @Autowired
    private CcairbagCarBrandsMapper ccairbagCarBrandsMapper;

    /**
     * 查询汽车品牌型号
     * 
     * @param id 汽车品牌型号主键
     * @return 汽车品牌型号
     */
    @Override
    public CcairbagCarBrands selectCcairbagCarBrandsById(String id)
    {
        return ccairbagCarBrandsMapper.selectCcairbagCarBrandsById(id);
    }

    /**
     * 查询汽车品牌型号列表
     * 
     * @param ccairbagCarBrands 汽车品牌型号
     * @return 汽车品牌型号
     */
    @Override
    public List<CcairbagCarBrands> selectCcairbagCarBrandsList(CcairbagCarBrands ccairbagCarBrands)
    {
        return ccairbagCarBrandsMapper.selectCcairbagCarBrandsList(ccairbagCarBrands);
    }

    /**
     * 新增汽车品牌型号
     * 
     * @param ccairbagCarBrands 汽车品牌型号
     * @return 结果
     */
    @Override
    public int insertCcairbagCarBrands(CcairbagCarBrands ccairbagCarBrands)
    {
        return ccairbagCarBrandsMapper.insertCcairbagCarBrands(ccairbagCarBrands);
    }

    /**
     * 修改汽车品牌型号
     * 
     * @param ccairbagCarBrands 汽车品牌型号
     * @return 结果
     */
    @Override
    public int updateCcairbagCarBrands(CcairbagCarBrands ccairbagCarBrands)
    {
        return ccairbagCarBrandsMapper.updateCcairbagCarBrands(ccairbagCarBrands);
    }

    /**
     * 批量删除汽车品牌型号
     * 
     * @param ids 需要删除的汽车品牌型号主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagCarBrandsByIds(String[] ids)
    {
        return ccairbagCarBrandsMapper.deleteCcairbagCarBrandsByIds(ids);
    }

    /**
     * 删除汽车品牌型号信息
     * 
     * @param id 汽车品牌型号主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagCarBrandsById(String id)
    {
        return ccairbagCarBrandsMapper.deleteCcairbagCarBrandsById(id);
    }
}
