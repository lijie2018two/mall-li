package com.ccairbag.api.service;

import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUserAddr;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 用户地址管理Service接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface ICcairbagUserAddrService 
{
    AppResult setUserAddr(@RequestBody CcairbagUserAddr ccairbagUserAddr);

    AppResult getUserAddrType( Integer addrType);
    List<CcairbagUserAddr> selectCcairbagUserAddrByUserId(Long userId);

    AppResult delectAddr(@ApiParam("addrId") Long addrId);
}
