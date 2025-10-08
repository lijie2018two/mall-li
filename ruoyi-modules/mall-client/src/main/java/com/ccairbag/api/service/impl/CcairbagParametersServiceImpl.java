package com.ccairbag.api.service.impl;

import com.ccairbag.api.mapper.CcairbagParametersMapper;
import com.ccairbag.api.service.ICcairbagParametersService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagParameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 参数表Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-24
 */
@Service
public class CcairbagParametersServiceImpl implements ICcairbagParametersService
{
    @Resource
    private CcairbagParametersMapper ccairbagParametersMapper;

    /**
     * 查询参数表
     * 
     * @param parameterId 参数表主键
     * @return 参数表
     */
    @Override
    public CcairbagParameters selectCcairbagParametersByParameterId(Long parameterId)
    {
        return ccairbagParametersMapper.selectCcairbagParametersByParameterId(parameterId);
    }

    /**
     * 查询参数表列表
     * 
     * @param ccairbagParameters 参数表
     * @return 参数表
     */
    @Override
    public List<CcairbagParameters> selectCcairbagParametersList(CcairbagParameters ccairbagParameters)
    {
        return ccairbagParametersMapper.selectCcairbagParametersList(ccairbagParameters);
    }

    /**
     * 新增参数表
     * 
     * @param ccairbagParameters 参数表
     * @return 结果
     */
    @Override
    public int insertCcairbagParameters(CcairbagParameters ccairbagParameters)
    {
        ccairbagParameters.setCreateTime(DateUtils.getNowDate());
        return ccairbagParametersMapper.insertCcairbagParameters(ccairbagParameters);
    }

    /**
     * 修改参数表
     * 
     * @param ccairbagParameters 参数表
     * @return 结果
     */
    @Override
    public int updateCcairbagParameters(CcairbagParameters ccairbagParameters)
    {
            ccairbagParameters.setUpdateTime(DateUtils.getNowDate());
        return ccairbagParametersMapper.updateCcairbagParameters(ccairbagParameters);
    }

    /**
     * 批量删除参数表
     * 
     * @param parameterIds 需要删除的参数表主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagParametersByParameterIds(Long[] parameterIds)
    {
        return ccairbagParametersMapper.deleteCcairbagParametersByParameterIds(parameterIds);
    }

    /**
     * 删除参数表信息
     * 
     * @param parameterId 参数表主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagParametersByParameterId(Long parameterId)
    {
        return ccairbagParametersMapper.deleteCcairbagParametersByParameterId(parameterId);
    }
}
