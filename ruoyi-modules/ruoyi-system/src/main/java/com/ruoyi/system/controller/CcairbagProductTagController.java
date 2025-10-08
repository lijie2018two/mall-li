package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.api.domain.ccairbag.CcairbagProductTag;
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
import com.ruoyi.system.service.ICcairbagProductTagService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 商品标签Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/tag")
public class CcairbagProductTagController extends BaseController
{
    @Autowired
    private ICcairbagProductTagService ccairbagProductTagService;

    /**
     * 查询商品标签列表
     */
    @RequiresPermissions("ccairbag:tag:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagProductTag ccairbagProductTag)
    {
        startPage();
        List<CcairbagProductTag> list = ccairbagProductTagService.selectCcairbagProductTagList(ccairbagProductTag);
        return getDataTable(list);
    }

    /**
     * 导出商品标签列表
     */
    @RequiresPermissions("ccairbag:tag:export")
    @Log(title = "商品标签", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagProductTag ccairbagProductTag)
    {
        List<CcairbagProductTag> list = ccairbagProductTagService.selectCcairbagProductTagList(ccairbagProductTag);
        ExcelUtil<CcairbagProductTag> util = new ExcelUtil<CcairbagProductTag>(CcairbagProductTag.class);
        util.exportExcel(response, list, "商品标签数据");
    }

    /**
     * 获取商品标签详细信息
     */
    @RequiresPermissions("ccairbag:tag:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(ccairbagProductTagService.selectCcairbagProductTagById(id));
    }

    /**
     * 新增商品标签
     */
    @RequiresPermissions("ccairbag:tag:add")
    @Log(title = "商品标签", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagProductTag ccairbagProductTag)
    {
        return toAjax(ccairbagProductTagService.insertCcairbagProductTag(ccairbagProductTag));
    }

    /**
     * 修改商品标签
     */
    @RequiresPermissions("ccairbag:tag:edit")
    @Log(title = "商品标签", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagProductTag ccairbagProductTag)
    {
        return toAjax(ccairbagProductTagService.updateCcairbagProductTag(ccairbagProductTag));
    }

    /**
     * 删除商品标签
     */
    @RequiresPermissions("ccairbag:tag:remove")
    @Log(title = "商品标签", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(ccairbagProductTagService.deleteCcairbagProductTagByIds(ids));
    }
}
