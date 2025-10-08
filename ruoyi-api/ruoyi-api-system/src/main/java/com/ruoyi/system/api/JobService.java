package com.ruoyi.system.api;


import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.domain.SHySmsLog;
import com.ruoyi.system.api.factory.RemoteLogFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 日志服务
 * 
 * @author ruoyi
 */
@FeignClient(contextId = "jobService", value = ServiceNameConstants.JOB_SERVICE, fallbackFactory = RemoteLogFallbackFactory.class)
public interface JobService
{
    /**
     * 保存系统日志
     *

     * @return 结果
     */
    @PostMapping("/delay/addSmsLog")
    public AjaxResult addSmsLog(@RequestBody SHySmsLog smsLog) ;

}
