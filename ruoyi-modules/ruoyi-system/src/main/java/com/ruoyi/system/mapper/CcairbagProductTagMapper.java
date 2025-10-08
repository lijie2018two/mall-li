package com.ruoyi.system.mapper;

import com.ruoyi.system.api.domain.ccairbag.CcairbagProductTag;

import java.util.List;

/**
 * 商品标签Mapper接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface CcairbagProductTagMapper 
{
    /**
     * 查询商品标签
     * 
     * @param id 商品标签主键
     * @return 商品标签
     */
    public CcairbagProductTag selectCcairbagProductTagById(Long id);

    /**
     * 查询商品标签列表
     * 
     * @param ccairbagProductTag 商品标签
     * @return 商品标签集合
     */
    public List<CcairbagProductTag> selectCcairbagProductTagList(CcairbagProductTag ccairbagProductTag);

    /**
     * 新增商品标签
     * 
     * @param ccairbagProductTag 商品标签
     * @return 结果
     */
    public int insertCcairbagProductTag(CcairbagProductTag ccairbagProductTag);

    /**
     * 修改商品标签
     * 
     * @param ccairbagProductTag 商品标签
     * @return 结果
     */
    public int updateCcairbagProductTag(CcairbagProductTag ccairbagProductTag);

    /**
     * 删除商品标签
     * 
     * @param id 商品标签主键
     * @return 结果
     */
    public int deleteCcairbagProductTagById(Long id);

    /**
     * 批量删除商品标签
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCcairbagProductTagByIds(Long[] ids);
}
