package com.ccairbag.api.controller;

import com.ccairbag.api.service.ICcairbagUserBrowseHistoryService;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.model.LoginAppUser;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户最近浏览记录Controller
 * 
 * @author ruoyi
 * @date 2025-07-01
 */
@RestController
@RequestMapping("ccairbag/history")
public class CcairbagUserBrowseHistoryController extends BaseController
{
    @Autowired
    private ICcairbagUserBrowseHistoryService ccairbagUserBrowseHistoryService;

    @ApiOperation(value = "查看该用户的商品浏览记录")
    @GetMapping("/recentlyViewed")
    public AppResult getRecentlyViewedProducts( @RequestParam(defaultValue = "1") Integer pageNum,
                                                @RequestParam(defaultValue = "10") Integer pageSize) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        if (oConvertUtils.isEmpty(userId)){
            return AppResult.error("用户id不能为空");
        }
        // 获取最近浏览商品列表
        return ccairbagUserBrowseHistoryService.getRecentlyViewedProducts(userId,pageNum, pageSize);
    }


}
