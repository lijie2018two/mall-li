package com.ccairbag.api.service;

import com.ruoyi.system.domain.CcairbagLogisticsServices;

import java.util.List;

/**
 * 物流服务Service接口
 * 
 * @author lidabai
 * @date 2025-03-06
 */
public interface ICcairbagLogisticsServicesService 
{
    /**
     * 查询物流服务
     * 
     * @param serviceId 物流服务主键
     * @return 物流服务
     */
    public CcairbagLogisticsServices selectCcairbagLogisticsServicesByServiceId(Long serviceId);

    /**
     * 查询物流服务列表
     * 
     * @param ccairbagLogisticsServices 物流服务
     * @return 物流服务集合
     */
    public List<CcairbagLogisticsServices> selectCcairbagLogisticsServicesList(CcairbagLogisticsServices ccairbagLogisticsServices);

    /**
     * 新增物流服务
     * 
     * @param ccairbagLogisticsServices 物流服务
     * @return 结果
     */
    public int insertCcairbagLogisticsServices(CcairbagLogisticsServices ccairbagLogisticsServices);

    /**
     * 修改物流服务
     * 
     * @param ccairbagLogisticsServices 物流服务
     * @return 结果
     */
    public int updateCcairbagLogisticsServices(CcairbagLogisticsServices ccairbagLogisticsServices);

    /**
     * 批量删除物流服务
     * 
     * @param serviceIds 需要删除的物流服务主键集合
     * @return 结果
     */
    public int deleteCcairbagLogisticsServicesByServiceIds(Long[] serviceIds);

    /**
     * 删除物流服务信息
     * 
     * @param serviceId 物流服务主键
     * @return 结果
     */
    public int deleteCcairbagLogisticsServicesByServiceId(Long serviceId);
}
