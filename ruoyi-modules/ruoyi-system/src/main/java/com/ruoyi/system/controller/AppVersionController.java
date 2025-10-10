package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.api.domain.ccairbag.AppVersion;
import com.ruoyi.system.service.IAppVersionService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * App版本信息Controller
 * 
 * @author lidabai
 * @date 2025-10-09
 */
@RestController
@RequestMapping("/version")
public class AppVersionController extends BaseController
{
    @Autowired
    private IAppVersionService appVersionService;

    /**
     * 查询App版本信息列表
     */
    @RequiresPermissions("system:version:list")
    @GetMapping("/list")
    public TableDataInfo list(AppVersion appVersion)
    {
        startPage();
        List<AppVersion> list = appVersionService.selectAppVersionList(appVersion);
        return getDataTable(list);
    }

    /**
     * 导出App版本信息列表
     */
    @RequiresPermissions("system:version:export")
    @Log(title = "App版本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AppVersion appVersion)
    {
        List<AppVersion> list = appVersionService.selectAppVersionList(appVersion);
        ExcelUtil<AppVersion> util = new ExcelUtil<AppVersion>(AppVersion.class);
        util.exportExcel(response, list, "App版本信息数据");
    }

    /**
     * 获取App版本信息详细信息
     */
    @RequiresPermissions("system:version:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(appVersionService.selectAppVersionById(id));
    }

    /**
     * 新增App版本信息
     */
    @RequiresPermissions("system:version:add")
    @Log(title = "App版本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AppVersion appVersion)
    {
        return toAjax(appVersionService.insertAppVersion(appVersion));
    }

    /**
     * 修改App版本信息
     */
    @RequiresPermissions("system:version:edit")
    @Log(title = "App版本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AppVersion appVersion)
    {
        return toAjax(appVersionService.updateAppVersion(appVersion));
    }

    /**
     * 删除App版本信息
     */
    @RequiresPermissions("system:version:remove")
    @Log(title = "App版本信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(appVersionService.deleteAppVersionByIds(ids));
    }

    /**
   * 导出模板
   */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {

        ExcelUtil<AppVersion> util = new ExcelUtil<AppVersion>(AppVersion.class);
        util.importTemplateExcel(response, "App版本信息数据");

    }


    /**
 * 导入数据
 */
    @Log(title = "App版本信息", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<AppVersion> util = new ExcelUtil<AppVersion>(AppVersion.class);
        InputStream inputStream = file.getInputStream();
        List<AppVersion> list = util.importExcel(inputStream );
        inputStream.close();
        int count = appVersionService.batchInsertAppVersion(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }



}
