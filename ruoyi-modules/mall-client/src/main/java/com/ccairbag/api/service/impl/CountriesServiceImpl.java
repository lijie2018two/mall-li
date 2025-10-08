package com.ccairbag.api.service.impl;

import java.util.List;

import com.ccairbag.api.mapper.CountriesMapper;
import com.ccairbag.api.service.ICountriesService;
import com.ruoyi.system.api.domain.ccairbag.Countries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 国家Service业务层处理
 * 
 * @author lidabai
 * @date 2025-03-20
 */
@Service
public class CountriesServiceImpl implements ICountriesService
{
    @Autowired
    private CountriesMapper countriesMapper;

    /**
     * 查询国家
     * 
     * @param id 国家主键
     * @return 国家
     */
    @Override
    public Countries selectCountriesById(Integer id)
    {
        return countriesMapper.selectCountriesById(id);
    }

    /**
     * 查询国家列表
     * 
     * @param countries 国家
     * @return 国家
     */
    @Override
    public List<Countries> selectCountriesList(Countries countries)
    {
        return countriesMapper.selectCountriesList(countries);
    }

    /**
     * 新增国家
     * 
     * @param countries 国家
     * @return 结果
     */
    @Override
    public int insertCountries(Countries countries)
    {
        return countriesMapper.insertCountries(countries);
    }

    /**
     * 修改国家
     * 
     * @param countries 国家
     * @return 结果
     */
    @Override
    public int updateCountries(Countries countries)
    {
        return countriesMapper.updateCountries(countries);
    }

    /**
     * 批量删除国家
     * 
     * @param ids 需要删除的国家主键
     * @return 结果
     */
    @Override
    public int deleteCountriesByIds(Integer[] ids)
    {
        return countriesMapper.deleteCountriesByIds(ids);
    }

    /**
     * 删除国家信息
     * 
     * @param id 国家主键
     * @return 结果
     */
    @Override
    public int deleteCountriesById(Integer id)
    {
        return countriesMapper.deleteCountriesById(id);
    }
}
