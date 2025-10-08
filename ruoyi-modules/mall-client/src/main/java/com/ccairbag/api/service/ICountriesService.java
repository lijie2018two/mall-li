package com.ccairbag.api.service;

import com.ruoyi.system.api.domain.ccairbag.Countries;

import java.util.List;

/**
 * 国家Service接口
 * 
 * @author lidabai
 * @date 2025-03-20
 */
public interface ICountriesService 
{
    /**
     * 查询国家
     * 
     * @param id 国家主键
     * @return 国家
     */
    public Countries selectCountriesById(Integer id);

    /**
     * 查询国家列表
     * 
     * @param countries 国家
     * @return 国家集合
     */
    public List<Countries> selectCountriesList(Countries countries);

    /**
     * 新增国家
     * 
     * @param countries 国家
     * @return 结果
     */
    public int insertCountries(Countries countries);

    /**
     * 修改国家
     * 
     * @param countries 国家
     * @return 结果
     */
    public int updateCountries(Countries countries);

    /**
     * 批量删除国家
     * 
     * @param ids 需要删除的国家主键集合
     * @return 结果
     */
    public int deleteCountriesByIds(Integer[] ids);

    /**
     * 删除国家信息
     * 
     * @param id 国家主键
     * @return 结果
     */
    public int deleteCountriesById(Integer id);
}
