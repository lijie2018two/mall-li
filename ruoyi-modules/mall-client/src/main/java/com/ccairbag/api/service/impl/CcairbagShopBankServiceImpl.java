package com.ccairbag.api.service.impl;

import com.ccairbag.api.mapper.CcairbagShopBankMapper;
import com.ccairbag.api.mapper.CcairbagShopsMapper;
import com.ccairbag.api.service.ICcairbagShopBankService;
import com.ruoyi.common.core.utils.PageUtils;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.common.core.web.page.PageDataInfo;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagShopBank;
import com.ruoyi.system.api.domain.ccairbag.CcairbagShops;
import com.ruoyi.system.api.model.LoginAppUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 绑定的银行卡Service业务层处理
 * 
 * @author lidabai
 * @date 2025-04-27
 */
@Service
public class CcairbagShopBankServiceImpl implements ICcairbagShopBankService
{
    @Resource
    private CcairbagShopBankMapper ccairbagShopBankMapper;
    @Resource
    private CcairbagShopsMapper ccairbagShopsMapper;
    @Override
    public AppResult list() {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagShops shops = ccairbagShopsMapper.getShopByUserId(userId);

        if (oConvertUtils.isEmpty(shops)) {
            return AppResult.error("商家不存在");
        }
        CcairbagShopBank shopBank = new CcairbagShopBank();
        shopBank.setShopId(shops.getShopId());
        PageUtils.startPage();
        List<CcairbagShopBank> shopBanks = ccairbagShopBankMapper.selectCcairbagShopBankList(shopBank);
        for (CcairbagShopBank bank : shopBanks) {
            String accountNumber = bank.getAccountNumber();
            if (bank.getPayType()==1){
                // dfsdfsdf@163.com  变成 xxxx4355@163.com
                int atIndex = accountNumber.indexOf('@');
                if (atIndex != -1 && atIndex > 0) {
                    String prefix = accountNumber.substring(0, atIndex);
                    int prefixLength = prefix.length();

                    String maskedPart = prefixLength > 4
                            ? prefix.substring(prefixLength - 4)
                            : prefix;

                    accountNumber = "xxxx" + maskedPart + accountNumber.substring(atIndex);
                } else {
                    accountNumber = "INVALID_EMAIL";
                }
                bank.setAccountNumberExt(accountNumber);


            }else {
                // 6222020200111111111 变成 6222********1111
                accountNumber = accountNumber.substring(0,4)+"********"+accountNumber.substring(accountNumber.length()-4,accountNumber.length());
                bank.setAccountNumberExt(accountNumber);  ;
            }
        }
        PageDataInfo<CcairbagShopBank> pageDataInfo = new PageDataInfo<>(shopBanks);
        PageUtils.clearPage();
        return new AppResult(pageDataInfo);
    }


    @Override
    public AppResult getInfo(Long id) {
        CcairbagShopBank shopBank = ccairbagShopBankMapper.selectCcairbagShopBankById(id);
        if (oConvertUtils.isEmpty(shopBank) ){
            return AppResult.error("银行卡不存在");
        }
        return new AppResult(shopBank);
    }

    @Override
    public AppResult addBankCars(CcairbagShopBank ccairbagShopBank) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagShops shops = ccairbagShopsMapper.getShopByUserId(userId);

        ccairbagShopBank.setShopId(shops.getShopId());
        ccairbagShopBank.setDeleted(0);
        ccairbagShopBank.setShopName(shops.getShopName());
        ccairbagShopBankMapper.insertCcairbagShopBank(ccairbagShopBank);
        return AppResult.success("添加成功");
    }

    @Override
    public AppResult editBankCars(CcairbagShopBank ccairbagShopBank) {
        if (oConvertUtils.isEmpty(ccairbagShopBank.getId())){
            return AppResult.error("id不能为空");
        }
        CcairbagShopBank shopBank = ccairbagShopBankMapper.selectCcairbagShopBankById(ccairbagShopBank.getId());
        if (oConvertUtils.isEmpty(shopBank)){
            return AppResult.error("该银行卡不存在");
        }
        ccairbagShopBankMapper.updateCcairbagShopBank(ccairbagShopBank);
        return new AppResult(shopBank);
    }

    @Override
    public AppResult delBankCars(Long id) {
        if (oConvertUtils.isEmpty(id)){
            return AppResult.error("id不能为空");
        }
        CcairbagShopBank shopBank = ccairbagShopBankMapper.selectCcairbagShopBankById(id);
        if (oConvertUtils.isEmpty(shopBank)){
            return AppResult.error("该银行卡不存在");
        }
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagShops shops = ccairbagShopsMapper.getShopByUserId(userId);

        if (!shops.getShopId().equals(shopBank.getShopId())){
            return AppResult.error("该银行卡不属于该商家");
        }
        shopBank.setDeleted(1);
        ccairbagShopBankMapper.updateCcairbagShopBank(shopBank);
        return AppResult.success("删除成功");

    }
}
