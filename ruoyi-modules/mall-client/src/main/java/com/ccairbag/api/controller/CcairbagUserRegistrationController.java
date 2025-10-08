package com.ccairbag.api.controller;

import com.ccairbag.api.service.ICcairbagUserRegistrationService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUserRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息登记Controller
 * 
 * @author lidabai
 * @date 2025-03-20
 */
@RestController
@RequestMapping("/registration")
public class CcairbagUserRegistrationController extends BaseController
{
    @Autowired
    private ICcairbagUserRegistrationService ccairbagUserRegistrationService;

    @GetMapping(value = "/getInfoByUserId/{userId}")
    public CcairbagUserRegistration getInfoByUserId(@PathVariable("userId") Long userId)
    {
        return ccairbagUserRegistrationService.getInfoByUserId(userId);
    }
}
