package com.ruoyi.system.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.api.domain.ccairbag.CcairbagPromotionActivities;
import com.ruoyi.system.service.ICcairbagPromotionActivitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 活动Controller
 * 
 * @author lidabai
 * @date 2025-07-15
 */
@RestController
@RequestMapping("ccairbag/activities")
public class CcairbagPromotionActivitiesController extends BaseController
{
    @Autowired
    private ICcairbagPromotionActivitiesService ccairbagPromotionActivitiesService;

    /**
     * 查询活动列表
     */
    @RequiresPermissions("system:activities:list")
    @GetMapping("/list")
    public TableDataInfo list(CcairbagPromotionActivities ccairbagPromotionActivities)
    {
        startPage();
        List<CcairbagPromotionActivities> list = ccairbagPromotionActivitiesService.selectCcairbagPromotionActivitiesList(ccairbagPromotionActivities);
        return getDataTable(list);
    }

    /**
     * 导出活动列表
     */
    @RequiresPermissions("system:activities:export")
    @Log(title = "活动", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CcairbagPromotionActivities ccairbagPromotionActivities)
    {
        List<CcairbagPromotionActivities> list = ccairbagPromotionActivitiesService.selectCcairbagPromotionActivitiesList(ccairbagPromotionActivities);
        ExcelUtil<CcairbagPromotionActivities> util = new ExcelUtil<CcairbagPromotionActivities>(CcairbagPromotionActivities.class);
        util.exportExcel(response, list, "活动数据");
    }

    /**
     * 获取活动详细信息
     */
    @RequiresPermissions("system:activities:query")
    @GetMapping(value = "/{activityId}")
    public AjaxResult getInfo(@PathVariable("activityId") Long activityId)
    {
        return success(ccairbagPromotionActivitiesService.selectCcairbagPromotionActivitiesByActivityId(activityId));
    }

    /**
     * 新增活动
     */
    @RequiresPermissions("system:activities:add")
    @Log(title = "活动", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CcairbagPromotionActivities ccairbagPromotionActivities)
    {
        return toAjax(ccairbagPromotionActivitiesService.insertCcairbagPromotionActivities(ccairbagPromotionActivities));
    }

    /**
     * 修改活动
     */
    @RequiresPermissions("system:activities:edit")
    @Log(title = "活动", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CcairbagPromotionActivities ccairbagPromotionActivities)
    {
        return toAjax(ccairbagPromotionActivitiesService.updateCcairbagPromotionActivities(ccairbagPromotionActivities));
    }

    /**
     * 删除活动
     */
    @RequiresPermissions("system:activities:remove")
    @Log(title = "活动", businessType = BusinessType.DELETE)
	@DeleteMapping("/{activityIds}")
    public AjaxResult remove(@PathVariable Long[] activityIds)
    {
        return toAjax(ccairbagPromotionActivitiesService.deleteCcairbagPromotionActivitiesByActivityIds(activityIds));
    }
}
