package com.ruoyi.system.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.ccairbag.CcairbagParameters;
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
import com.ruoyi.system.service.ICcairbagParametersService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 参数表Controller
 * 
 * @author lidabai
 * @date 2025-02-24
 */
@RestController
@RequestMapping("ccairbag/parameters")
public class CcairbagParametersController extends BaseController
{
    @Autowired
    private ICcairbagParametersService ccairbagParametersService;

    /**
     * 查询参数表列表
     */
    @RequiresPermissions("system/ccairbag:parameters:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagParameters ccairbagParameters)
    {
        startPage();
        List<CcairbagParameters> list = ccairbagParametersService.selectCcairbagParametersList(ccairbagParameters);
        return getDataTable(list);
    }

    /**
     * 导出参数表列表
     */
    @RequiresPermissions("system/ccairbag:parameters:export")
    @Log(title = "参数表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagParameters ccairbagParameters)
    {
        List<CcairbagParameters> list = ccairbagParametersService.selectCcairbagParametersList(ccairbagParameters);
        ExcelUtil<CcairbagParameters> util = new ExcelUtil<CcairbagParameters>(CcairbagParameters.class);
        util.exportExcel(response, list, "参数表数据");
    }

    /**
     * 获取参数表详细信息
     */
    @RequiresPermissions("system/ccairbag:parameters:query")
    @GetMapping(value = "/{parameterId}")
    public AjaxResult getInfo(@PathVariable("parameterId") Long parameterId)
    {
        return success(ccairbagParametersService.selectCcairbagParametersByParameterId(parameterId));
    }

    /**
     * 新增参数表
     */
    @RequiresPermissions("system/ccairbag:parameters:add")
    @Log(title = "参数表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagParameters ccairbagParameters)
    {
        return toAjax(ccairbagParametersService.insertCcairbagParameters(ccairbagParameters));
    }

    /**
     * 修改参数表
     */
    @RequiresPermissions("system/ccairbag:parameters:edit")
    @Log(title = "参数表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagParameters ccairbagParameters)
    {
        return toAjax(ccairbagParametersService.updateCcairbagParameters(ccairbagParameters));
    }

    /**
     * 删除参数表
     */
    @RequiresPermissions("system/ccairbag:parameters:remove")
    @Log(title = "参数表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{parameterIds}")
    public AjaxResult remove(@PathVariable Long[] parameterIds)
    {
        return toAjax(ccairbagParametersService.deleteCcairbagParametersByParameterIds(parameterIds));
    }
}
