package com.ruoyi.system.mapper;

import com.ruoyi.system.api.domain.ccairbag.CcairbagParameters;

import java.util.List;

/**
 * 参数表Mapper接口
 * 
 * @author lidabai
 * @date 2025-02-24
 */
public interface CcairbagParametersMapper 
{
    /**
     * 查询参数表
     * 
     * @param parameterId 参数表主键
     * @return 参数表
     */
    public CcairbagParameters selectCcairbagParametersByParameterId(Long parameterId);

    /**
     * 查询参数表列表
     * 
     * @param ccairbagParameters 参数表
     * @return 参数表集合
     */
    public List<CcairbagParameters> selectCcairbagParametersList(CcairbagParameters ccairbagParameters);

    /**
     * 新增参数表
     * 
     * @param ccairbagParameters 参数表
     * @return 结果
     */
    public int insertCcairbagParameters(CcairbagParameters ccairbagParameters);

    /**
     * 修改参数表
     * 
     * @param ccairbagParameters 参数表
     * @return 结果
     */
    public int updateCcairbagParameters(CcairbagParameters ccairbagParameters);

    /**
     * 删除参数表
     * 
     * @param parameterId 参数表主键
     * @return 结果
     */
    public int deleteCcairbagParametersByParameterId(Long parameterId);

    /**
     * 批量删除参数表
     * 
     * @param parameterIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCcairbagParametersByParameterIds(Long[] parameterIds);
}
