package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUserAddr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CcairbagUserAddrMapper;
import com.ruoyi.system.service.ICcairbagUserAddrService;

/**
 * 用户地址管理Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
public class CcairbagUserAddrServiceImpl implements ICcairbagUserAddrService 
{
    @Autowired
    private CcairbagUserAddrMapper ccairbagUserAddrMapper;

    /**
     * 查询用户地址管理
     * 
     * @param addrId 用户地址管理主键
     * @return 用户地址管理
     */
    @Override
    public CcairbagUserAddr selectCcairbagUserAddrByAddrId(Long addrId)
    {
        return ccairbagUserAddrMapper.selectCcairbagUserAddrByAddrId(addrId);
    }

    /**
     * 查询用户地址管理列表
     * 
     * @param ccairbagUserAddr 用户地址管理
     * @return 用户地址管理
     */
    @Override
    public List<CcairbagUserAddr> selectCcairbagUserAddrList(CcairbagUserAddr ccairbagUserAddr)
    {
        return ccairbagUserAddrMapper.selectCcairbagUserAddrList(ccairbagUserAddr);
    }

    /**
     * 新增用户地址管理
     * 
     * @param ccairbagUserAddr 用户地址管理
     * @return 结果
     */
    @Override
    public int insertCcairbagUserAddr(CcairbagUserAddr ccairbagUserAddr)
    {
        ccairbagUserAddr.setCreateTime(DateUtils.getNowDate());
        return ccairbagUserAddrMapper.insertCcairbagUserAddr(ccairbagUserAddr);
    }

    /**
     * 修改用户地址管理
     * 
     * @param ccairbagUserAddr 用户地址管理
     * @return 结果
     */
    @Override
    public int updateCcairbagUserAddr(CcairbagUserAddr ccairbagUserAddr)
    {
        ccairbagUserAddr.setUpdateTime(DateUtils.getNowDate());
        return ccairbagUserAddrMapper.updateCcairbagUserAddr(ccairbagUserAddr);
    }

    /**
     * 批量删除用户地址管理
     * 
     * @param addrIds 需要删除的用户地址管理主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagUserAddrByAddrIds(Long[] addrIds)
    {
        return ccairbagUserAddrMapper.deleteCcairbagUserAddrByAddrIds(addrIds);
    }

    /**
     * 删除用户地址管理信息
     * 
     * @param addrId 用户地址管理主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagUserAddrByAddrId(Long addrId)
    {
        return ccairbagUserAddrMapper.deleteCcairbagUserAddrByAddrId(addrId);
    }
}
