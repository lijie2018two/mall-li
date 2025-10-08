package com.ccairbag.api.service.impl;

import java.util.List;

import com.ccairbag.api.mapper.CitiesMapper;
import com.ccairbag.api.service.ICitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.Cities;

/**
 * 城市Service业务层处理
 * 
 * @author lidabai
 * @date 2025-03-20
 */
@Service
public class CitiesServiceImpl implements ICitiesService
{
    @Autowired
    private CitiesMapper citiesMapper;

    /**
     * 查询城市
     * 
     * @param id 城市主键
     * @return 城市
     */
    @Override
    public Cities selectCitiesById(String id)
    {
        return citiesMapper.selectCitiesById(id);
    }

    /**
     * 查询城市列表
     * 
     * @param cities 城市
     * @return 城市
     */
    @Override
    public List<Cities> selectCitiesList(Cities cities)
    {
        return citiesMapper.selectCitiesList(cities);
    }

    /**
     * 新增城市
     * 
     * @param cities 城市
     * @return 结果
     */
    @Override
    public int insertCities(Cities cities)
    {
        return citiesMapper.insertCities(cities);
    }

    /**
     * 修改城市
     * 
     * @param cities 城市
     * @return 结果
     */
    @Override
    public int updateCities(Cities cities)
    {
        return citiesMapper.updateCities(cities);
    }

    /**
     * 批量删除城市
     * 
     * @param ids 需要删除的城市主键
     * @return 结果
     */
    @Override
    public int deleteCitiesByIds(String[] ids)
    {
        return citiesMapper.deleteCitiesByIds(ids);
    }

    /**
     * 删除城市信息
     * 
     * @param id 城市主键
     * @return 结果
     */
    @Override
    public int deleteCitiesById(String id)
    {
        return citiesMapper.deleteCitiesById(id);
    }
}
