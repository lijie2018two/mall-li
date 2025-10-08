package com.ccairbag.api.controller;

import com.ccairbag.api.service.ICcairbagParametersService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.api.domain.ccairbag.CcairbagParameters;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    @ApiOperation(value = "根据选择的品类id 获取 商品参数信息")
    @GetMapping("/getProductParametersList")
    public List<CcairbagParameters> getProductParametersList(@ApiParam(name = "categoryId", value = "品类id", required = true)  Long categoryId)
    {
        CcairbagParameters ccairbagParameters = new CcairbagParameters();
        ccairbagParameters.setCategoryId(categoryId);
        List<CcairbagParameters> list = ccairbagParametersService.selectCcairbagParametersList(ccairbagParameters);
        return list;
    }


}
