package com.ccairbag.api.service.impl;



import com.ccairbag.api.mapper.AppVersionMapper;
import com.ccairbag.api.mapper.CcairbagCarBrandsMapper;
import com.ccairbag.api.service.IAppVersionService;
import com.ruoyi.system.api.domain.ccairbag.AppVersion;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * App版本信息Service业务层处理
 * 
 * @author lidabai
 * @date 2025-10-09
 */
@Service
public class AppVersionServiceImpl implements IAppVersionService
{
    @Resource
    private AppVersionMapper appVersionMapper;

    @Override
    public AppVersion getApkVersion() {
        return appVersionMapper.getApkVersion();
    }
}
