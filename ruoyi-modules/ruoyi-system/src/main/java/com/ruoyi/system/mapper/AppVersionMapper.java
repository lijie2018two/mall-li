package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.api.domain.ccairbag.AppVersion;

/**
 * App版本信息Mapper接口
 * 
 * @author lidabai
 * @date 2025-10-09
 */
public interface AppVersionMapper 
{
    /**
     * 查询App版本信息
     * 
     * @param id App版本信息主键
     * @return App版本信息
     */
    public AppVersion selectAppVersionById(Long id);

    /**
     * 查询App版本信息列表
     * 
     * @param appVersion App版本信息
     * @return App版本信息集合
     */
    public List<AppVersion> selectAppVersionList(AppVersion appVersion);

    /**
     * 新增App版本信息
     * 
     * @param appVersion App版本信息
     * @return 结果
     */
    public int insertAppVersion(AppVersion appVersion);

    /**
     * 修改App版本信息
     * 
     * @param appVersion App版本信息
     * @return 结果
     */
    public int updateAppVersion(AppVersion appVersion);

    /**
     * 删除App版本信息
     * 
     * @param id App版本信息主键
     * @return 结果
     */
    public int deleteAppVersionById(Long id);

    /**
     * 批量删除App版本信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAppVersionByIds(Long[] ids);
}
