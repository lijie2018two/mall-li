package com.ccairbag.api.controller;

import com.ccairbag.api.service.ICcairbagUserCategoryService;
import com.ruoyi.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户喜好分类Controller
 * 
 * @author lidabai
 * @date 2025-05-10
 */
@RestController
@RequestMapping("/category")
public class CcairbagUserCategoryController extends BaseController
{
    @Autowired
    private ICcairbagUserCategoryService ccairbagUserCategoryService;


}
