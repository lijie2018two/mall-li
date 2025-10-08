package com.ccairbag.api.controller;

import com.ccairbag.api.service.ICcairbagProductPromotionRelationsService;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProductPromotionRelations;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品 - 活动关联Controller
 * 
 * @author lidabai
 * @date 2025-05-08
 */
@RestController
@RequestMapping("ccairbag/relations")
public class CcairbagProductPromotionRelationsController extends BaseController
{
    @Autowired
    private ICcairbagProductPromotionRelationsService ccairbagProductPromotionRelationsService;


    @ApiOperation(value = "给商品添加进入活动")
    @PostMapping("/setProductActivity")
    public AppResult setProductActivity(@RequestBody CcairbagProductPromotionRelations ccairbagProductPromotionRelations)
    {
        if (oConvertUtils.isEmpty(ccairbagProductPromotionRelations.getProductId())) {
            return AppResult.error("商品id不能为空");
        }
        if (oConvertUtils.isEmpty(ccairbagProductPromotionRelations.getActivityId())) {
            return AppResult.error("活动id不能为空");
        }
        return ccairbagProductPromotionRelationsService.setProductActivity(ccairbagProductPromotionRelations);
    }

    @ApiOperation(value = "给商品移除活动")
    @PostMapping("/delProductActivity")
    public AppResult delProductActivity(@RequestBody CcairbagProductPromotionRelations ccairbagProductPromotionRelations)
    {
        if (oConvertUtils.isEmpty(ccairbagProductPromotionRelations.getProductId())) {
            return AppResult.error("商品id不能为空");
        }
        if (oConvertUtils.isEmpty(ccairbagProductPromotionRelations.getActivityId())) {
            return AppResult.error("活动id不能为空");
        }
        return ccairbagProductPromotionRelationsService.delProductActivity(ccairbagProductPromotionRelations);
    }


}
