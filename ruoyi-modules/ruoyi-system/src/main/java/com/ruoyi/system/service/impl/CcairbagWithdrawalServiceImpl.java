package com.ruoyi.system.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.ijpay.core.IJPayHttpResponse;
import com.ijpay.paypal.PayPalApi;
import com.ijpay.paypal.PayPalApiConfig;
import com.ijpay.paypal.PayPalApiConfigKit;
import com.ijpay.paypal.accesstoken.AccessToken;
import com.ijpay.paypal.accesstoken.AccessTokenKit;
import com.ijpay.paypal.enums.PayPalApiUrl;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.domain.ccairbag.CcairbagWithdrawal;
import com.ruoyi.system.domain.PayPalConfig;
import com.ruoyi.system.mapper.CcairbagWithdrawalMapper;
import com.ruoyi.system.service.ICcairbagWithdrawalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private PayPalConfig paypalConfig;
    /**
     * 查询提现记录
     * 
     * @param id 提现记录主键
     * @return 提现记录
     */
    @Override
    public CcairbagWithdrawal selectCcairbagWithdrawalById(Long id)
    {
        return ccairbagWithdrawalMapper.selectCcairbagWithdrawalById(id);
    }

    /**
     * 查询提现记录列表
     * 
     * @param ccairbagWithdrawal 提现记录
     * @return 提现记录
     */
    @Override
    public List<CcairbagWithdrawal> selectCcairbagWithdrawalList(CcairbagWithdrawal ccairbagWithdrawal)
    {
        return ccairbagWithdrawalMapper.selectCcairbagWithdrawalList(ccairbagWithdrawal);
    }

    /**
     * 新增提现记录
     * 
     * @param ccairbagWithdrawal 提现记录
     * @return 结果
     */
    @Override
    public int insertCcairbagWithdrawal(CcairbagWithdrawal ccairbagWithdrawal)
    {
        ccairbagWithdrawal.setCreateTime(DateUtils.getNowDate());
        return ccairbagWithdrawalMapper.insertCcairbagWithdrawal(ccairbagWithdrawal);
    }

    /**
     * 修改提现记录
     * 
     * @param ccairbagWithdrawal 提现记录
     * @return 结果
     */
    @Override
    public AjaxResult updateCcairbagWithdrawal(CcairbagWithdrawal ccairbagWithdrawal)
    {
        ccairbagWithdrawal.setUpdateTime(DateUtils.getNowDate());
        ccairbagWithdrawalMapper.updateCcairbagWithdrawal(ccairbagWithdrawal);
        if (ccairbagWithdrawal.getStatus() != 1) {
            // 进行提现
            withdrawalPaypalMoeny(ccairbagWithdrawal);
            //提现成功 后 减少商家表的 可提现金额
        }
        return AjaxResult.success();
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


        String url = baseUrl+"/v1/payments/payouts";


        Map<String, Object> amount = new HashMap<>(4);
        amount.put("value", withdrawal.getMoney());
        amount.put("currency", "USD");
        Map<String, Object> method = new HashMap<>();
        Map<String, Object> phone = new HashMap<>();


        phone.put("country_code", "91");
        phone.put("national_number", "9999988888");
        method.put("phone", phone);
        Map<String, Object> senderBatchHeader = new HashMap<>(4);
        senderBatchHeader.put("sender_batch_id", "Payouts_2018_100007");
        senderBatchHeader.put("email_subject", "You have a payout!");
        senderBatchHeader.put("email_message", "You have received a payout! Thanks for using our service!");
        Map<String, Object> item = new HashMap<>(4);
        item.put("recipient_type", "EMAIL");
        item.put("amount", amount);
        item.put("note", "提现");
        item.put("receiver", "sb-e2dix41015699@personal.example.com");
        item.put("sender_item_id", "item_1");
        item.put("notification_language", "fr-FR");
        item.put("alternate_notification_method",method);
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

    /**
     * 批量删除提现记录
     * 
     * @param ids 需要删除的提现记录主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagWithdrawalByIds(Long[] ids)
    {
        return ccairbagWithdrawalMapper.deleteCcairbagWithdrawalByIds(ids);
    }

    /**
     * 删除提现记录信息
     * 
     * @param id 提现记录主键
     * @return 结果
     */
    @Override
    public int deleteCcairbagWithdrawalById(Long id)
    {
        return ccairbagWithdrawalMapper.deleteCcairbagWithdrawalById(id);
    }
}
