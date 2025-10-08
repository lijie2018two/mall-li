package com.ccairbag.api.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.ccairbag.api.service.IStatesService;
import com.ruoyi.common.core.utils.oConvertUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
import com.ruoyi.system.domain.States;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 省Controller
 * 
 * @author lidabai
 * @date 2025-03-20
 */
@RestController
@RequestMapping("/states")
public class StatesController extends BaseController
{
    @Autowired
    private IStatesService statesService;

    /**
     * 查询省列表
     */


    @ApiOperation("根据国家id获取省列表")
    @GetMapping("/list")
    public List<States> list(@ApiParam("name") String name,@ApiParam("countryId") String countryId)
    {
        if (oConvertUtils.isEmpty(countryId)){
            throw new RuntimeException("国家id不能为空");
        }
        States states = new States();
        states.setName(name);
        states.setCountryId(countryId);
        List<States> list = statesService.selectStatesList(states);
        return list;
    }

    /**
     * 导出省列表
     */
    @RequiresPermissions("ccairbag/system:states:export")
    @Log(title = "省", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, States states)
    {
        List<States> list = statesService.selectStatesList(states);
        ExcelUtil<States> util = new ExcelUtil<States>(States.class);
        util.exportExcel(response, list, "省数据");
    }

    /**
     * 获取省详细信息
     */
    @RequiresPermissions("ccairbag/system:states:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(statesService.selectStatesById(id));
    }

    /**
     * 新增省
     */
    @RequiresPermissions("ccairbag/system:states:add")
    @Log(title = "省", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody States states)
    {
        return toAjax(statesService.insertStates(states));
    }

    /**
     * 修改省
     */
    @RequiresPermissions("ccairbag/system:states:edit")
    @Log(title = "省", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody States states)
    {
        return toAjax(statesService.updateStates(states));
    }

    /**
     * 删除省
     */
    @RequiresPermissions("ccairbag/system:states:remove")
    @Log(title = "省", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(statesService.deleteStatesByIds(ids));
    }
}
