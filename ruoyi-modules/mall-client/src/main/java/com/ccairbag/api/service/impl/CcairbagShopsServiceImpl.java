package com.ccairbag.api.service.impl;

import com.ccairbag.api.mapper.*;
import com.ccairbag.api.service.ICcairbagShopsService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.PageUtils;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.common.core.web.page.PageDataInfo;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagShops;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUsers;
import com.ruoyi.system.api.domain.ccairbag.dto.ShopMoneyDTO;
import com.ruoyi.system.api.model.LoginAppUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 店铺Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
public class CcairbagShopsServiceImpl implements ICcairbagShopsService
{
    @Resource
    private CcairbagShopsMapper ccairbagShopsMapper;
    @Resource
    private CcairbagProductsMapper ccairbagProductsMapper;
    @Resource
    private CcairbagIncomeRecordsMapper ccairbagIncomeRecordsMapper;
    @Resource
    private CcairbagWithdrawalMapper ccairbagWithdrawalMapper;
    @Resource
    private CcairbagOrdersMapper ccairbagOrdersMapper;
    @Resource
    private CcairbagUsersMapper ccairbagUsersMapper;

    @Override
    public CcairbagShops addExt(CcairbagShops ccairbagShops) {
        ccairbagShops.setCreateTime(DateUtils.getNowDate());
        ccairbagShops.setDeleted(0);
        int i = ccairbagShopsMapper.insertCcairbagShops(ccairbagShops);
        if (i>0){
            return ccairbagShops;
        }
        return null;
    }

    @Override
    public AppResult editShop(CcairbagShops ccairbagShops) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagShops shops = ccairbagShopsMapper.getShopByUserId(userId);
        if (oConvertUtils.isEmpty(shops)){
            return AppResult.error("店铺不存在");
        }
        shops.setShopLogo(ccairbagShops.getShopLogo());
        shops.setIntro(ccairbagShops.getIntro());
        shops.setShopName(ccairbagShops.getShopName());
        shops.setUpdateTime(DateUtils.getNowDate());
        ccairbagShopsMapper.updateCcairbagShops(shops);
        return AppResult.success("修改成功");
    }

    @Override
    public AppResult addShopBusinessQualification(CcairbagShops ccairbagShops) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        CcairbagUsers users = loginAppUser.getCcairbagUsers();
        CcairbagShops shops = ccairbagShopsMapper.getShopByUserId(users.getUserId());
        if (oConvertUtils.isEmpty(shops)){
            return AppResult.error("店铺不存在");
        }
        if (oConvertUtils.isNotEmpty(users.getQualificationStatus())){
            if (users.getQualificationStatus()==1){
                return AppResult.error("审核中");
            }
            if (users.getQualificationStatus()==3){
                return AppResult.error("已通过");
            }
        }

        shops.setBusinessQualificationImages(ccairbagShops.getBusinessQualificationImages());
        shops.setUpdateTime(DateUtils.getNowDate());
        ccairbagShopsMapper.updateCcairbagShops(shops);
        //修改用户表
        users.setQualificationStatus(1);
        users.setUpdateTime(DateUtils.getNowDate());
        ccairbagUsersMapper.updateCcairbagUsers(users);

        return AppResult.success("提交成功");
    }

    @Override
    public CcairbagShops getShopByUserId(Long userId) {
        CcairbagShops ccairbagShops = ccairbagShopsMapper.getShopByUserId(userId);
        return ccairbagShops;
    }

    @Override
    public AppResult<CcairbagShops> getShopById(Long shopId) {
        CcairbagShops shops = ccairbagShopsMapper.selectCcairbagShopsByShopId(shopId);
        BigDecimal rate = ccairbagProductsMapper.shopGoodRate(shopId);
        Integer sales = ccairbagProductsMapper.shopSales(shopId);

        shops.setSalesVolume(sales);
        shops.setGoodRate(rate);
        return new AppResult(shops);
    }

    @Override
    public AppResult<PageDataInfo<CcairbagShops>> getAllShopInfo() {
        CcairbagShops ccairbagShops1 = new CcairbagShops();
        ccairbagShops1.setApplicationStatus(1);
        PageUtils.startPage();
        List<CcairbagShops> ccairbagShops = ccairbagShopsMapper.selectCcairbagShopsList(ccairbagShops1);
        PageDataInfo<CcairbagShops> pageDataInfo = new PageDataInfo<>(ccairbagShops);
        PageUtils.clearPage();
        return new AppResult<>(pageDataInfo);
    }

    @Override
    public AppResult salesDashboard() {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagShops shops = ccairbagShopsMapper.getShopByUserId(userId);
        //总销售额
        //总订单数
        //商品数量
        //账户金额

        BigDecimal accountMoney = shops.getWalletBalanceExt().add(shops.getWalletBalance());
        int totalOrder =ccairbagOrdersMapper.getShopTotalOrder(shops.getShopId());
        int totalGoods = ccairbagOrdersMapper.getShopTotalGoods(shops.getShopId());
        BigDecimal totalMoney = ccairbagOrdersMapper.getShopTotalSales(shops.getShopId());
        Map<String, Object> map = new HashMap<>();
        map.put("totalMoney",totalMoney);
        map.put("totalOrder",totalOrder);
        map.put("totalGoods",totalGoods);
        map.put("accountMoney",accountMoney);
        return new AppResult(map);
    }

    @Override
    public AppResult getShopMoney() {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        CcairbagShops shops = ccairbagShopsMapper.getShopByUserId(userId);
        if (oConvertUtils.isEmpty(shops)) {
            return AppResult.error("商家不存在");
        }
        ShopMoneyDTO shopMoneyDTO = new ShopMoneyDTO();
        //可提现金额
        BigDecimal availableMoney = ccairbagIncomeRecordsMapper.getSumMoneyFor7Days(shops.getShopId());
        //冻结金额
        BigDecimal balanceMoney = ccairbagIncomeRecordsMapper.getSumMoneyFor7DaysIn(shops.getShopId());
        shopMoneyDTO.setPauseMoney(balanceMoney);
        shops.setWalletBalance(balanceMoney);
        //总金额
        BigDecimal sumMoney = availableMoney.add(balanceMoney);
        shopMoneyDTO.setTotalMoney(sumMoney);
        //已提现金额
        BigDecimal sumWithdrawMoney = ccairbagWithdrawalMapper.getWithdrawalMoneyByShopId(shops.getShopId());
        shopMoneyDTO.setAvailableMoney(availableMoney.subtract(sumWithdrawMoney));
        shopMoneyDTO.setSumWithdrawMoney(sumWithdrawMoney);
        shops.setWalletBalanceExt(shopMoneyDTO.getAvailableMoney());
        ccairbagShopsMapper.updateCcairbagShops(shops);
        return new AppResult(shopMoneyDTO);
    }
}
