package com.ccairbag.api.mapper;

import com.ruoyi.system.domain.CcairbagLogisticsServices;

import java.util.List;

/**
 * 物流服务Mapper接口
 * 
 * @author lidabai
 * @date 2025-03-06
 */
public interface CcairbagLogisticsServicesMapper 
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
     * 删除物流服务
     * 
     * @param serviceId 物流服务主键
     * @return 结果
     */
    public int deleteCcairbagLogisticsServicesByServiceId(Long serviceId);

    /**
     * 批量删除物流服务
     * 
     * @param serviceIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCcairbagLogisticsServicesByServiceIds(Long[] serviceIds);
}
