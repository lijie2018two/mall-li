package com.ccairbag.api.service.impl;

import com.ccairbag.api.mapper.CcairbagLogisticsServicesMapper;
import com.ccairbag.api.service.ICcairbagLogisticsServicesService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.domain.CcairbagLogisticsServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物流服务Service业务层处理
 * 
 * @author lidabai
 * @date 2025-03-06
 */
@Service
public class CcairbagLogisticsServicesServiceImpl implements ICcairbagLogisticsServicesService
{
    @Autowired
    private CcairbagLogisticsServicesMapper ccairbagLogisticsServicesMapper;

    /**
     * 查询物流服务
     * 
     * @param serviceId 物流服务主键
     * @return 物流服务
     */
    @Override
    public CcairbagLogisticsServices selectCcairbagLogisticsServicesByServiceId(Long serviceId)
    {
        return ccairbagLogisticsServicesMapper.selectCcairbagLogisticsServicesByServiceId(serviceId);
    }

    /**
     * 查询物流服务列表
     * 
     * @param ccairbagLogisticsServices 物流服务
     * @return 物流服务
     */
    @Override
    public List<CcairbagLogisticsServices> selectCcairbagLogisticsServicesList(CcairbagLogisticsServices ccairbagLogisticsServices)
    {
        return ccairbagLogisticsServicesMapper.selectCcairbagLogisticsServicesList(ccairbagLogisticsServices);
    }

    /**
     * 新增物流服务
     * 
     * @param ccairbagLogisticsServices 物流服务
     * @return 结果
     */
    @Override
    public int insertCcairbagLogisticsServices(CcairbagLogisticsServices ccairbagLogisticsServices)
    {
        ccairbagLogisticsServices.setCreateTime(DateUtils.getNowDate());
        ccairbagLogisticsServices.setDeleted(0);
        return ccairbagLogisticsServicesMapper.insertCcairbagLogisticsServices(ccairbagLogisticsServices);
    }

    /**
     * 修改物流服务
     * 
     * @param ccairbagLogisticsServices 物流服务
     * @return 结果
     */
    @Override
    public int updateCcairbagLogisticsServices(CcairbagLogisticsServices ccairbagLogisticsServices)
    {
        ccairbagLogisticsServices.setUpdateTime(DateUtils.getNowDate());
        return ccairbagLogisticsServicesMapper.updateCcairbagLogisticsServices(ccairbagLogisticsServices);
    }

    /**
     * 批量删除物流服务
     * 
     * @param serviceIds 需要删除的物流服务主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagLogisticsServicesByServiceIds(Long[] serviceIds)
    {
        return ccairbagLogisticsServicesMapper.deleteCcairbagLogisticsServicesByServiceIds(serviceIds);
    }

    /**
     * 删除物流服务信息
     * 
     * @param serviceId 物流服务主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagLogisticsServicesByServiceId(Long serviceId)
    {
        return ccairbagLogisticsServicesMapper.deleteCcairbagLogisticsServicesByServiceId(serviceId);
    }
}
