package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.system.api.domain.ccairbag.CcairbagProductTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CcairbagProductTagMapper;
import com.ruoyi.system.service.ICcairbagProductTagService;

/**
 * 商品标签Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
public class CcairbagProductTagServiceImpl implements ICcairbagProductTagService 
{
    @Autowired
    private CcairbagProductTagMapper ccairbagProductTagMapper;

    /**
     * 查询商品标签
     * 
     * @param id 商品标签主键
     * @return 商品标签
     */
    @Override
    public CcairbagProductTag selectCcairbagProductTagById(Long id)
    {
        return ccairbagProductTagMapper.selectCcairbagProductTagById(id);
    }

    /**
     * 查询商品标签列表
     * 
     * @param ccairbagProductTag 商品标签
     * @return 商品标签
     */
    @Override
    public List<CcairbagProductTag> selectCcairbagProductTagList(CcairbagProductTag ccairbagProductTag)
    {
        return ccairbagProductTagMapper.selectCcairbagProductTagList(ccairbagProductTag);
    }

    /**
     * 新增商品标签
     * 
     * @param ccairbagProductTag 商品标签
     * @return 结果
     */
    @Override
    public int insertCcairbagProductTag(CcairbagProductTag ccairbagProductTag)
    {
        return ccairbagProductTagMapper.insertCcairbagProductTag(ccairbagProductTag);
    }

    /**
     * 修改商品标签
     * 
     * @param ccairbagProductTag 商品标签
     * @return 结果
     */
    @Override
    public int updateCcairbagProductTag(CcairbagProductTag ccairbagProductTag)
    {
        return ccairbagProductTagMapper.updateCcairbagProductTag(ccairbagProductTag);
    }

    /**
     * 批量删除商品标签
     * 
     * @param ids 需要删除的商品标签主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagProductTagByIds(Long[] ids)
    {
        return ccairbagProductTagMapper.deleteCcairbagProductTagByIds(ids);
    }

    /**
     * 删除商品标签信息
     * 
     * @param id 商品标签主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagProductTagById(Long id)
    {
        return ccairbagProductTagMapper.deleteCcairbagProductTagById(id);
    }
}
