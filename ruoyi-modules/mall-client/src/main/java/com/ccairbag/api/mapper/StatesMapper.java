package com.ccairbag.api.mapper;

import java.util.List;
import com.ruoyi.system.domain.States;

/**
 * 省Mapper接口
 * 
 * @author lidabai
 * @date 2025-03-20
 */
public interface StatesMapper 
{
    /**
     * 查询省
     * 
     * @param id 省主键
     * @return 省
     */
    public States selectStatesById(String id);

    /**
     * 查询省列表
     * 
     * @param states 省
     * @return 省集合
     */
    public List<States> selectStatesList(States states);

    /**
     * 新增省
     * 
     * @param states 省
     * @return 结果
     */
    public int insertStates(States states);

    /**
     * 修改省
     * 
     * @param states 省
     * @return 结果
     */
    public int updateStates(States states);

    /**
     * 删除省
     * 
     * @param id 省主键
     * @return 结果
     */
    public int deleteStatesById(String id);

    /**
     * 批量删除省
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStatesByIds(String[] ids);
}
