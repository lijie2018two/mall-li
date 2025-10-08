package com.ccairbag.api.controller;

import com.ccairbag.api.service.ICcairbagIndexService;
import com.ruoyi.common.core.utils.HttpUtils;
import com.ruoyi.common.core.web.domain.AppResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("ccairbag/index")
public class CcairbagIndexController {


    @Autowired
    private ICcairbagIndexService ccairbagIndexService;


    @GetMapping("/index")
    public AppResult index()
    {
        return ccairbagIndexService.index();
    }

    @GetMapping("/test")
    public void test()
    {
        ccairbagIndexService.test();


    }
    @ApiOperation(value = "判断传来的vin码返回是否信息")
    @GetMapping("/checkVinInfo")
    public AppResult checkVinInfo(@ApiParam(value = "vin码", required = true) String vin )
    {
        String url = "https://api.api-ninjas.com/v1/vinlookup?vin="+vin;
        Map<String, String> header = new HashMap<>(4);
        header.put("X-Api-Key", "Xt+LRU5RLb9GGl2KTt1SHg==D6S4szcYBPFWqell");
        String cc =  HttpUtils.get(url,header, null);
        //cc转成json  然后判断json 中的vin 是否有值  有的话就返回true 没有就false
        if (cc.contains("vin"))
        {
            return new AppResult(true);
        }else {
            return new AppResult(false);
        }

    }
}
