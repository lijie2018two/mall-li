package com.ccairbag.api.controller;



import com.ccairbag.api.service.IAppVersionService;
import com.ruoyi.system.api.domain.ccairbag.AppVersion;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.web.controller.BaseController;


/**
 * App版本信息Controller
 * 
 * @author lidabai
 * @date 2025-10-09
 */
@RestController
@RequestMapping("/ccairbag/version")
public class AppVersionController extends BaseController
{
    @Autowired
    private IAppVersionService appVersionService;


    @ApiOperation(value = "查询apk最新版本")
    @GetMapping("/getApkVersion")
    public AppVersion getApkVersion()
    {
        AppVersion appVersion = appVersionService.getApkVersion();
        return appVersion;
    }


}
