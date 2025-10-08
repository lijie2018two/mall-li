package com.ccairbag.api.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.ccairbag.api.domain.dto.PayPalConfig;
import com.ccairbag.api.mapper.CcairbagShopBankMapper;
import com.ccairbag.api.mapper.CcairbagShopsMapper;
import com.ccairbag.api.mapper.CcairbagWithdrawalMapper;
import com.ccairbag.api.service.ICcairbagWithdrawalService;
import com.ijpay.core.IJPayHttpResponse;
import com.ijpay.paypal.PayPalApi;
import com.ijpay.paypal.PayPalApiConfig;
import com.ijpay.paypal.PayPalApiConfigKit;
import com.ijpay.paypal.accesstoken.AccessToken;
import com.ijpay.paypal.accesstoken.AccessTokenKit;
import com.ijpay.paypal.enums.PayPalApiUrl;
import com.ruoyi.common.core.utils.PageUtils;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.common.core.web.page.PageDataInfo;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagShopBank;
import com.ruoyi.system.api.domain.ccairbag.CcairbagShops;
import com.ruoyi.system.api.domain.ccairbag.CcairbagWithdrawal;
import com.ruoyi.system.api.model.LoginAppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 提现记录Service业务层处理
 * 
 * @author lidabai
 * @date 2025-04-27
 */
@Service
@Slf4j
public class CcairbagWithdrawalServiceImpl implements ICcairbagWithdrawalService
{
    @Resource
    private CcairbagWithdrawalMapper ccairbagWithdrawalMapper;
    @Resource
    private CcairbagShopsMapper ccairbagShopsMapper;
    @Resource
    private CcairbagShopBankMapper ccairbagShopBankMapper;

    @Autowired
    private PayPalConfig paypalConfig;

    @Override
    public AppResult list() {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagShops shops = ccairbagShopsMapper.getShopByUserId(userId);
        PageUtils.startPage();
        List<CcairbagWithdrawal> list =ccairbagWithdrawalMapper.selectListByShopId(shops.getShopId());

        for (CcairbagWithdrawal ccairbagWithdrawal : list) {
            String accountNumber = ccairbagWithdrawal.getAccountNumber();
            if (ccairbagWithdrawal.getPayType()==1){
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
                ccairbagWithdrawal.setAccountNumberExt(accountNumber);


            }else {
                // 6222020200111111111 变成 6222********1111
                accountNumber = accountNumber.substring(0,4)+"********"+accountNumber.substring(accountNumber.length()-4,accountNumber.length());
                ccairbagWithdrawal.setAccountNumberExt(accountNumber);  ;
            }
        }
        PageDataInfo<CcairbagWithdrawal> pageDataInfo = new PageDataInfo<>(list);
        PageUtils.clearPage();
        return new AppResult(pageDataInfo);
    }

    @Override
    public AppResult getInfo(Long id) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagShops shops = ccairbagShopsMapper.getShopByUserId(userId);
        CcairbagWithdrawal withdrawal = ccairbagWithdrawalMapper.selectCcairbagWithdrawalById(id);
        CcairbagShopBank bank = ccairbagShopBankMapper.selectCcairbagShopBankById( withdrawal.getBankId());
        withdrawal.setAccountNumber(bank.getAccountNumber());
        withdrawal.setPayType(bank.getPayType());
        if (!withdrawal.getShopId().equals(shops.getShopId())){
            return new AppResult("没有权限");
        }
        return new AppResult(withdrawal);
    }

    @Override
    public AppResult addWithdrawal(CcairbagWithdrawal ccairbagWithdrawal) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagShops shops = ccairbagShopsMapper.getShopByUserId(userId);
        CcairbagShopBank bank = ccairbagShopBankMapper.selectCcairbagShopBankById(ccairbagWithdrawal.getBankId());
        if (oConvertUtils.isEmpty(bank)){
            return AppResult.error("银行卡不存在");
        }
        if (shops.getWalletBalanceExt().compareTo(ccairbagWithdrawal.getMoney())<0){
            return AppResult.error("钱包余额不足");
        }
        ccairbagWithdrawal.setApplyTime(new Date());
        ccairbagWithdrawal.setShopId(shops.getShopId());
        ccairbagWithdrawal.setShopName(shops.getShopName());
        ccairbagWithdrawal.setDrawMoney(BigDecimal.ZERO);
        ccairbagWithdrawal.setRealMoney(ccairbagWithdrawal.getMoney());
        ccairbagWithdrawal.setStatus(3);
        ccairbagWithdrawal.setDeleted(0);
        ccairbagWithdrawalMapper.insertCcairbagWithdrawal(ccairbagWithdrawal);
        withdrawalPaypalMoeny(ccairbagWithdrawal);
        //提现成功 后 减少商家表的 可提现金额
        shops.setWalletBalanceExt(shops.getWalletBalanceExt().subtract(ccairbagWithdrawal.getMoney()));
        ccairbagShopsMapper.updateCcairbagShops(shops);
        return AppResult.success("申请成功，审批通过，");
//        return AppResult.success("申请成功，等待审批");
    }

    public PayPalApiConfig getConfig() {
        PayPalApiConfig config = new PayPalApiConfig();
        config.setClientId(paypalConfig.getClientId());
        config.setSecret(paypalConfig.getSecret());
        config.setSandBox(paypalConfig.isSandBox());
        config.setDomain(paypalConfig.getDomain());
        PayPalApiConfigKit.setThreadLocalApiConfig(config);
        return config;
    }

    private void withdrawalPaypalMoeny(CcairbagWithdrawal withdrawal) {
        // 调用退款
        PayPalApiConfig config = getConfig();
        String baseUrl = "";
        if (paypalConfig.isSandBox()){
            // 测试环境
            baseUrl = PayPalApiUrl.SANDBOX_GATEWAY.getUrl();
        }else {
            // 生产环境
            baseUrl = PayPalApiUrl.LIVE_GATEWAY.getUrl();
        }
        AccessToken accessToken = AccessTokenKit.get(config.getClientId());

        CcairbagShopBank bank = ccairbagShopBankMapper.selectCcairbagShopBankById(withdrawal.getBankId());
        String url = baseUrl+"/v1/payments/payouts";


        Map<String, Object> amount = new HashMap<>(4);
        amount.put("value", withdrawal.getMoney());
        amount.put("currency", "USD");
//        Map<String, Object> method = new HashMap<>();
//        Map<String, Object> phone = new HashMap<>();
//
//
//        phone.put("country_code", "91");
//        phone.put("national_number", "9999988888");
//        method.put("phone", phone);
        Map<String, Object> senderBatchHeader = new HashMap<>(4);
        senderBatchHeader.put("sender_batch_id", withdrawal.getId());
        senderBatchHeader.put("email_subject", "You have a payout!");
        senderBatchHeader.put("email_message", "You have received a payout! Thanks for using our service!");
        Map<String, Object> item = new HashMap<>(4);
        item.put("recipient_type", "EMAIL");
        item.put("amount", amount);
        item.put("note", "withdrawal");
        item.put("receiver", bank.getAccountNumber());
        item.put("sender_item_id", "item_1");
//        item.put("notification_language", "fr-FR");
//        item.put("alternate_notification_method",method);
        List<Map<String, Object>> itemList = new ArrayList<>();
        itemList.add(item);
        Map<String, Object> map = new HashMap<>(4);
        map.put("items", itemList);
        map.put("sender_batch_header", senderBatchHeader);
        JSONObject json = new JSONObject(map);

        log.info(json.toJSONString());
        String data = JSONUtil.toJsonStr(map);
        log.info(data);
        try {
            IJPayHttpResponse response = PayPalApi.post(url,data,PayPalApi.getBaseHeaders(accessToken));
            log.info("withdrawalPaypalMoeny:{}",response);
        }catch (Exception e){
            log.error("withdrawalPaypalMoeny:{}",e);
        }



    }
}
