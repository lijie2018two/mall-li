package com.ccairbag.api.controller;

import com.ccairbag.api.service.ICcairbagPromotionActivitiesService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AppResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 活动Controller
 * 
 * @author lidabai
 * @date 2025-05-08
 */
@RestController
@RequestMapping("ccairbag/activities")
public class CcairbagPromotionActivitiesController extends BaseController
{
    @Autowired
    private ICcairbagPromotionActivitiesService ccairbagPromotionActivitiesService;



    @ApiOperation(value = "查询所有活动，以及对应商品商品")
    @GetMapping("/getActivitiesAndProduct")
    public AppResult getActivitiesAndProduct()
    {
        return ccairbagPromotionActivitiesService.getActivitiesAndProduct();
    }

    @ApiOperation(value = "查询所有活动")
    @GetMapping("/getActivities")
    public AppResult getActivities()
    {
        return ccairbagPromotionActivitiesService.getActivities();
    }



}
