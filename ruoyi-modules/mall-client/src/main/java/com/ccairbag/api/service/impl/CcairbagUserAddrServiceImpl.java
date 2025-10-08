package com.ccairbag.api.service.impl;

import com.ccairbag.api.mapper.CcairbagOrderDetailsMapper;
import com.ccairbag.api.mapper.CcairbagProductsMapper;
import com.ccairbag.api.mapper.CcairbagUserAddrMapper;
import com.ccairbag.api.service.ICcairbagUserAddrService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUserAddr;
import com.ruoyi.system.api.model.LoginAppUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户地址管理Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
public class CcairbagUserAddrServiceImpl implements ICcairbagUserAddrService
{
    @Resource
    private CcairbagUserAddrMapper ccairbagUserAddrMapper;
    @Resource
    private CcairbagProductsMapper ccairbagProductsMapper;
    @Resource
    private CcairbagOrderDetailsMapper ccairbagOrderDetailsMapper;
    @Override
    public AppResult setUserAddr(CcairbagUserAddr ccairbagUserAddr) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        if (!ccairbagUserAddr.getUserId().equals(loginAppUser.getCcairbagUsers().getUserId())){
            return AppResult.error("Illegal operation, user ID does not match");
        }
        ccairbagUserAddr.setStatus(1);
        if (oConvertUtils.isNotEmpty(ccairbagUserAddr.getCommonAddr())){
            if (ccairbagUserAddr.getCommonAddr()==1){
                //把改用户的其他地址都设置为0
                ccairbagUserAddrMapper.setCommonAddr(ccairbagUserAddr.getUserId(),ccairbagUserAddr.getAddrType());
            }
        }
        if (oConvertUtils.isEmpty(ccairbagUserAddr.getAddrId())){
            CcairbagUserAddr ccairbagUserAddr1 = new CcairbagUserAddr();
            ccairbagUserAddr1.setUserId(ccairbagUserAddr.getUserId());
            ccairbagUserAddr1.setAddrType(ccairbagUserAddr.getAddrType());
            List<CcairbagUserAddr> addrList = ccairbagUserAddrMapper.selectCcairbagUserAddrList(ccairbagUserAddr1);
            if (addrList.isEmpty()){
                ccairbagUserAddr.setCommonAddr(1);
            }
            ccairbagUserAddr.setCreateTime(DateUtils.getNowDate());
            ccairbagUserAddr.setDeleted(0);
            int i = ccairbagUserAddrMapper.insertCcairbagUserAddr(ccairbagUserAddr);
            if (i > 0){
                return AppResult.success("Address added successfully");
            }
            return AppResult.error("Failed to add address");
        }else {
            ccairbagUserAddr.setUpdateTime(DateUtils.getNowDate());
            int i = ccairbagUserAddrMapper.updateCcairbagUserAddr(ccairbagUserAddr);
            if (i > 0){
                return AppResult.success("Address updated successfully");
            }
            return AppResult.error("Failed to update address");
        }

    }

    @Override
    public AppResult getUserAddrType(Integer addrType) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        CcairbagUserAddr ccairbagUserAddr = new CcairbagUserAddr();
        ccairbagUserAddr.setAddrType(addrType);
        ccairbagUserAddr.setUserId(loginAppUser.getCcairbagUsers().getUserId());
        List<CcairbagUserAddr> list = ccairbagUserAddrMapper.selectCcairbagUserAddrList(ccairbagUserAddr);
        return new AppResult(list);
    }

    @Override
    public List<CcairbagUserAddr> selectCcairbagUserAddrByUserId(Long userId){
        return ccairbagUserAddrMapper.selectCcairbagUserAddrByUserId(userId);
    }

    @Override
    public AppResult delectAddr(Long addrId) {
        CcairbagUserAddr ccairbagUserAddr = ccairbagUserAddrMapper.selectCcairbagUserAddrByAddrId(addrId);
        if (oConvertUtils.isEmpty(ccairbagUserAddr)){
            return AppResult.error("Address does not exist");
        }
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        if (!ccairbagUserAddr.getUserId().equals(loginAppUser.getCcairbagUsers().getUserId())){
            return AppResult.error("Illegal operation, user ID does not match");
        }
        //判断 这个地址是否绑定商品
        int x = ccairbagProductsMapper.ishaveProductByAddrId(addrId);
        if (x>0){
            return AppResult.error("This address is bound to a product and cannot be deleted");
        }
        //判断 是否关联订单  关联订单的不让删除了
        int j  = ccairbagOrderDetailsMapper.getNumByUserId(ccairbagUserAddr.getUserId());
        if (j>0){
            return AppResult.error("This address is associated with an order and cannot be deleted");
        }
        ccairbagUserAddr.setDeleted(1);
        int i = ccairbagUserAddrMapper.deleteCcairbagUserAddrByAddrId(addrId);
        if (i > 0){
            return AppResult.success("Address deleted successfully");
        }
        return AppResult.error("Failed to delete address");
    }
}
