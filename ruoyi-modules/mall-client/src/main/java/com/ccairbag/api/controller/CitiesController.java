package com.ccairbag.api.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.ccairbag.api.service.ICitiesService;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.system.api.domain.ccairbag.Countries;
import com.ruoyi.system.domain.States;
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
import com.ruoyi.system.domain.Cities;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 城市Controller
 * 
 * @author lidabai
 * @date 2025-03-20
 */
@RestController
@RequestMapping("/cities")
public class CitiesController extends BaseController
{
    @Autowired
    private ICitiesService citiesService;




    @ApiOperation(value = "城市列表")
    @GetMapping("/list")
    public List<Cities> list(@ApiParam("城市名称") String name, @ApiParam("省id") String stateId)
    {
        if (oConvertUtils.isEmpty(stateId)){
            throw new RuntimeException("省id不能为空");
        }
        Cities cities = new Cities();
        cities.setName(name);
        cities.setStateId(stateId);
        List<Cities> list = citiesService.selectCitiesList(cities);
        return list;
    }



}
