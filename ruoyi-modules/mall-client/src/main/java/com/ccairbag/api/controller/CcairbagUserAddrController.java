package com.ccairbag.api.controller;

import com.ccairbag.api.service.ICcairbagUserAddrService;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUserAddr;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户地址管理Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/addr")
public class CcairbagUserAddrController extends BaseController
{
    @Autowired
    private ICcairbagUserAddrService ccairbagUserAddrService;




    @ApiOperation(value = "用户添加/编辑收货地址")
    @PostMapping("/setUserAddr")
    public AppResult setUserAddr(@RequestBody CcairbagUserAddr ccairbagUserAddr)
    {
        if (oConvertUtils.isEmpty(ccairbagUserAddr.getUserId())){
            return AppResult.error("用户id不能为空");
        }
        return ccairbagUserAddrService.setUserAddr(ccairbagUserAddr);
    }

    @ApiOperation(value = "根据用户id 查询用户地址")
    @GetMapping("/selectCcairbagUserAddrByUserId/{userId}")
    public List<CcairbagUserAddr> selectCcairbagUserAddrByUserId(@PathVariable("userId") Long userId)
    {
        return ccairbagUserAddrService.selectCcairbagUserAddrByUserId(userId);
    }

    @ApiOperation(value = "根据地址类型查询 用户地址")
    @GetMapping("/getUserAddrType")
    public AppResult getUserAddrType( Integer addrType)
    {
        return ccairbagUserAddrService.getUserAddrType(addrType);
    }

    @ApiOperation(value = "删除用户地址")
    @DeleteMapping("/delectAddr")
    public AppResult delectAddr(@ApiParam("addrId") Long addrId)
    {
        return ccairbagUserAddrService.delectAddr(addrId);
    }


}
