package com.ccairbag.api.mapper;


import com.ruoyi.system.api.domain.ccairbag.AppVersion;
import com.ruoyi.system.domain.CcairbagCarBrands;

import java.util.List;

/**
 * App版本信息Mapper接口
 * 
 * @author lidabai
 * @date 2025-10-09
 */


public interface AppVersionMapper
{
    AppVersion getApkVersion();

    public AppVersion selectAppVersionById(Long id);

}
