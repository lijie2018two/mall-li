package com.ruoyi.system.controller;

import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.api.domain.ccairbag.CcairbagSetscale;
import com.ruoyi.system.service.ICcairbagSetscaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 税收比例

Controller
 * 
 * @author lidabai
 * @date 2025-04-21
 */
@RestController
@RequestMapping("ccairbag/setscale")
public class CcairbagSetscaleController extends BaseController
{
    @Autowired
    private ICcairbagSetscaleService ccairbagSetscaleService;

    /**
     * 查询税收比例

列表
     */
    @RequiresPermissions("system/ccairbag:setscale:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagSetscale ccairbagSetscale)
    {
        startPage();
        List<CcairbagSetscale> list = ccairbagSetscaleService.selectCcairbagSetscaleList(ccairbagSetscale);
        return getDataTable(list);
    }

    /**
     * 导出税收比例

列表
     */
    @RequiresPermissions("system/ccairbag:setscale:export")
    @Log(title = "税收比例", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagSetscale ccairbagSetscale)
    {
        List<CcairbagSetscale> list = ccairbagSetscaleService.selectCcairbagSetscaleList(ccairbagSetscale);
        ExcelUtil<CcairbagSetscale> util = new ExcelUtil<CcairbagSetscale>(CcairbagSetscale.class);
        util.exportExcel(response, list, "税收比例数据");
    }

    /**
     * 获取税收比例

详细信息
     */
    @RequiresPermissions("system/ccairbag:setscale:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(ccairbagSetscaleService.selectCcairbagSetscaleById(id));
    }

    /**
     * 新增税收比例


     */
    @RequiresPermissions("system/ccairbag:setscale:add")
    @Log(title = "税收比例", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagSetscale ccairbagSetscale)
    {
        if (oConvertUtils.isEmpty(ccairbagSetscale.getScale())){
            return AjaxResult.error("销售税比例不能为空");
        }
        if (oConvertUtils.isEmpty(ccairbagSetscale.getDealScale())){
            return AjaxResult.error("交易费用比例不能为空");
        }
        return toAjax(ccairbagSetscaleService.insertCcairbagSetscale(ccairbagSetscale));
    }

    /**
     * 修改税收比例


     */
    @RequiresPermissions("system/ccairbag:setscale:edit")
    @Log(title = "税收比例", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagSetscale ccairbagSetscale)
    {
        if (oConvertUtils.isEmpty(ccairbagSetscale.getScale())){
            return AjaxResult.error("销售税比例不能为空");
        }
        if (oConvertUtils.isEmpty(ccairbagSetscale.getDealScale())){
            return AjaxResult.error("交易费用比例不能为空");
        }
        return toAjax(ccairbagSetscaleService.updateCcairbagSetscale(ccairbagSetscale));
    }

    /**
     * 删除税收比例


     */
    @RequiresPermissions("system/ccairbag:setscale:remove")
    @Log(title = "税收比例", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(ccairbagSetscaleService.deleteCcairbagSetscaleByIds(ids));
    }
}
