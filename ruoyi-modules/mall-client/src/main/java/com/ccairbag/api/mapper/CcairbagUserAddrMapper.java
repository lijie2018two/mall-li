package com.ccairbag.api.mapper;

import com.ruoyi.system.api.domain.ccairbag.CcairbagUserAddr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户地址管理Mapper接口
 * 
 * @author lidabai
 * @date 2025-02-20
 */
public interface CcairbagUserAddrMapper 
{
    /**
     * 查询用户地址管理
     * 
     * @param addrId 用户地址管理主键
     * @return 用户地址管理
     */
    public CcairbagUserAddr selectCcairbagUserAddrByAddrId(Long addrId);

    public List<CcairbagUserAddr> selectCcairbagUserAddrByUserId(Long userId);

    List<CcairbagUserAddr>  selectCcairbagUserAddrByUserIdExt(Long userId);
    /**
     * 查询用户地址管理列表
     * 
     * @param ccairbagUserAddr 用户地址管理
     * @return 用户地址管理集合
     */
    public List<CcairbagUserAddr> selectCcairbagUserAddrList(CcairbagUserAddr ccairbagUserAddr);

    /**
     * 新增用户地址管理
     * 
     * @param ccairbagUserAddr 用户地址管理
     * @return 结果
     */
    public int insertCcairbagUserAddr(CcairbagUserAddr ccairbagUserAddr);

    /**
     * 修改用户地址管理
     * 
     * @param ccairbagUserAddr 用户地址管理
     * @return 结果
     */
    public int updateCcairbagUserAddr(CcairbagUserAddr ccairbagUserAddr);

    /**
     * 删除用户地址管理
     * 
     * @param addrId 用户地址管理主键
     * @return 结果
     */
    public int deleteCcairbagUserAddrByAddrId(Long addrId);

    /**
     * 批量删除用户地址管理
     * 
     * @param addrIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCcairbagUserAddrByAddrIds(Long[] addrIds);

    void setCommonAddr(@Param("userId") Long userId, @Param("addrType") Integer addrType);

}
