package com.ccairbag.api.service.impl;

import com.ccairbag.api.domain.dto.ShopCartDto;
import com.ccairbag.api.mapper.CcairbagProductsMapper;
import com.ccairbag.api.mapper.CcairbagShoppingCartsMapper;
import com.ccairbag.api.service.ICcairbagShoppingCartsService;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.ccairbag.CcairbagProducts;
import com.ruoyi.system.api.domain.ccairbag.CcairbagShoppingCarts;
import com.ruoyi.system.api.model.LoginAppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 购物车Service业务层处理
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@Service
public class CcairbagShoppingCartsServiceImpl implements ICcairbagShoppingCartsService
{
    @Resource
    private CcairbagShoppingCartsMapper ccairbagShoppingCartsMapper;
    @Resource
    private CcairbagProductsMapper ccairbagProductsMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public AppResult addShopCars(CcairbagShoppingCarts ccairbagShoppingCarts) {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        int x = ccairbagShoppingCartsMapper.countUserShopByProduct(userId,ccairbagShoppingCarts.getProdId());
        if (x > 0) {
            return AppResult.error("自己发布的商品则不能加入购物车");
        }
        ccairbagShoppingCarts.setUserId(userId);
        List<CcairbagShoppingCarts> ccairbagShoppingCartsx = ccairbagShoppingCartsMapper.selectShopCars(userId);
        int flag = 0;
        if (!ccairbagShoppingCartsx.isEmpty()) {
            for (CcairbagShoppingCarts ccairbagShoppingCart : ccairbagShoppingCartsx){
                if (ccairbagShoppingCart.getProdId().equals(ccairbagShoppingCarts.getProdId())) {
                    Integer quantity = ccairbagShoppingCart.getQuantity()+ccairbagShoppingCarts.getQuantity();
                    ccairbagShoppingCart.setQuantity(quantity);
                    ccairbagShoppingCartsMapper.updateCcairbagShoppingCarts(ccairbagShoppingCart);
                    flag = 1;
                }
            }
        }
        if (flag == 0){
            int i = ccairbagShoppingCartsMapper.insertCcairbagShoppingCarts(ccairbagShoppingCarts);
            if (i > 0) {
                return AppResult.success();
            }
            return AppResult.error("添加失败");
        }
        return AppResult.success();

    }

    @Override
    public AppResult delShopCars(Long cartId,Integer quantity) {
        CcairbagShoppingCarts carts = ccairbagShoppingCartsMapper.selectCcairbagShoppingCartsByCartId(cartId);
        if (carts.getQuantity()>quantity){
            carts.setQuantity(carts.getQuantity()-quantity);
            ccairbagShoppingCartsMapper.updateCcairbagShoppingCarts(carts);
            int i  = ccairbagShoppingCartsMapper.updateCcairbagShoppingCarts(carts);
            if (i > 0) {
                return AppResult.success();
            }
        }else if(Objects.equals(carts.getQuantity(), quantity)){
            int i = ccairbagShoppingCartsMapper.deleteCcairbagShoppingCartsByCartId(cartId);
            if (i > 0) {
                return AppResult.success();
            }
        }else {
            return AppResult.error("移除失败,移除数量已超过上线");
        }
        return AppResult.error("移除失败");
    }

    @Override
    public AppResult delShopCarsExt(Long cartId) {
        int i = ccairbagShoppingCartsMapper.deleteCcairbagShoppingCartsByCartId(cartId);
        if (i > 0) {
            return AppResult.success();
        }
        return AppResult.error("删除失败");
    }

    @Override
    public AppResult<List<ShopCartDto>> selectShopCars() {
        LoginAppUser loginAppUser = SecurityUtils.getLoginAppUser();
        Long userId = loginAppUser.getCcairbagUsers().getUserId();
        if (oConvertUtils.isNotEmpty(userId)) {
            CcairbagShoppingCarts carts = new CcairbagShoppingCarts();
            carts.setUserId(userId);
            // 这个查询 做关联 到商品表 和商铺表
            List<CcairbagShoppingCarts> ccairbagShoppingCarts = ccairbagShoppingCartsMapper.selectShopCars(userId);

            for (CcairbagShoppingCarts ccairbagShoppingCart : ccairbagShoppingCarts){
                CcairbagProducts products = ccairbagProductsMapper.selectCcairbagProductsByProductId(ccairbagShoppingCart.getProdId());
                BigDecimal price = BigDecimal.ZERO;
                if (oConvertUtils.isNotEmpty(products.getDiscount())) {
                    // 如果存在折扣，使用折扣价：原价 * (discount / 100)
                    BigDecimal discountRate = new BigDecimal(products.getDiscount()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                    price = products.getPrice().multiply(discountRate).setScale(2, RoundingMode.HALF_UP);
                    price = products.getPrice().subtract(price);
                } else {
                    // 如果没有折扣，直接使用原价
                    price = products.getPrice();
                }
                ccairbagShoppingCart.setPrice(price);
            }
            // 使用一个 Map 来存储每个店铺及其对应的商品列表
            Map<Long, List<CcairbagShoppingCarts>> shopToProductsMap = new HashMap<>();

            for (CcairbagShoppingCarts ccairbagShoppingCart : ccairbagShoppingCarts) {
                Long shopId = ccairbagShoppingCart.getShopId(); // 假设 CcairbagShoppingCarts 中有 shopId 字段
                if (!shopToProductsMap.containsKey(shopId)) {
                    shopToProductsMap.put(shopId, new ArrayList<>());
                }
                String key = "product_stock:" + ccairbagShoppingCart.getProdId();
                int i = redisService.getCacheObject(key);
                ccairbagShoppingCart.setTotalQuantity(i);
                shopToProductsMap.get(shopId).add(ccairbagShoppingCart);
            }

            // 将 Map 转换为 List<ShopCartDto>
            List<ShopCartDto> shopCartGroups = new ArrayList<>();
            for (Map.Entry<Long, List<CcairbagShoppingCarts>> entry : shopToProductsMap.entrySet()) {
                Long shopId = entry.getKey();
                List<CcairbagShoppingCarts> products = entry.getValue();

                // 获取店铺名称
                String shopName = products.get(0).getShopName(); // 假设每个店铺的商品都有相同的 shopName
                String shopLogo = products.get(0).getShopLogo();
                // 创建 ShopCartDto 对象
                ShopCartDto shopCartDto = new ShopCartDto();
                shopCartDto.setShopId(shopId);
                shopCartDto.setShopName(shopName);
                shopCartDto.setProducts(products);
                shopCartDto.setShopLogo(shopLogo);
                shopCartGroups.add(shopCartDto);
            }

            return new AppResult<>(shopCartGroups);
        }
        return AppResult.error("请先登录");
    }


}
