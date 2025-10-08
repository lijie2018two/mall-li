package com.ccairbag.api.service.impl;

import java.util.List;

import com.ccairbag.api.mapper.StatesMapper;
import com.ccairbag.api.service.IStatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.States;

/**
 * 省Service业务层处理
 * 
 * @author lidabai
 * @date 2025-03-20
 */
@Service
public class StatesServiceImpl implements IStatesService
{
    @Autowired
    private StatesMapper statesMapper;

    /**
     * 查询省
     * 
     * @param id 省主键
     * @return 省
     */
    @Override
    public States selectStatesById(String id)
    {
        return statesMapper.selectStatesById(id);
    }

    /**
     * 查询省列表
     * 
     * @param states 省
     * @return 省
     */
    @Override
    public List<States> selectStatesList(States states)
    {
        return statesMapper.selectStatesList(states);
    }

    /**
     * 新增省
     * 
     * @param states 省
     * @return 结果
     */
    @Override
    public int insertStates(States states)
    {
        return statesMapper.insertStates(states);
    }

    /**
     * 修改省
     * 
     * @param states 省
     * @return 结果
     */
    @Override
    public int updateStates(States states)
    {
        return statesMapper.updateStates(states);
    }

    /**
     * 批量删除省
     * 
     * @param ids 需要删除的省主键
     * @return 结果
     */
    @Override
    public int deleteStatesByIds(String[] ids)
    {
        return statesMapper.deleteStatesByIds(ids);
    }

    /**
     * 删除省信息
     * 
     * @param id 省主键
     * @return 结果
     */
    @Override
    public int deleteStatesById(String id)
    {
        return statesMapper.deleteStatesById(id);
    }
}
