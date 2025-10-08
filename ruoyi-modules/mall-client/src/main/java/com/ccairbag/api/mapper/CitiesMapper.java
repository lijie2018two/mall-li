package com.ccairbag.api.mapper;

import java.util.List;
import com.ruoyi.system.domain.Cities;

/**
 * 城市Mapper接口
 * 
 * @author lidabai
 * @date 2025-03-20
 */
public interface CitiesMapper 
{
    /**
     * 查询城市
     * 
     * @param id 城市主键
     * @return 城市
     */
    public Cities selectCitiesById(String id);

    /**
     * 查询城市列表
     * 
     * @param cities 城市
     * @return 城市集合
     */
    public List<Cities> selectCitiesList(Cities cities);

    /**
     * 新增城市
     * 
     * @param cities 城市
     * @return 结果
     */
    public int insertCities(Cities cities);

    /**
     * 修改城市
     * 
     * @param cities 城市
     * @return 结果
     */
    public int updateCities(Cities cities);

    /**
     * 删除城市
     * 
     * @param id 城市主键
     * @return 结果
     */
    public int deleteCitiesById(String id);

    /**
     * 批量删除城市
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCitiesByIds(String[] ids);
}
