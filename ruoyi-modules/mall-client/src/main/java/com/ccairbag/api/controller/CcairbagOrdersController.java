package com.ccairbag.api.controller;

import com.alipay.api.AlipayApiException;
import com.ccairbag.api.domain.dto.CreateOrderDto;
import com.ccairbag.api.domain.dto.ShopCartDtoExt;
import com.ccairbag.api.service.ICcairbagOrdersService;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.CcairbagOrders;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUserAddr;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 订单Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/orders")
public class CcairbagOrdersController extends BaseController {
    @Autowired
    private ICcairbagOrdersService ccairbagOrdersService;

    @ApiOperation(value = "直接创建订单 并返回订单详情")
    @PostMapping("/createOrder")
    public AppResult createOrder(@RequestBody CreateOrderDto createOrderDto) {
        return ccairbagOrdersService.createOrder(createOrderDto);
    }

    @ApiOperation(value = "访客直接创建订单 并返回订单详情")
    @PostMapping("/createOrderExt")
    public AppResult createOrderExt(@RequestBody CreateOrderDto createOrderDto) {

        return ccairbagOrdersService.createOrderExt(createOrderDto);
    }

    @ApiOperation(value = "访客更新订单 收货地址")
    @PostMapping("/updateOrderExt")
    public AppResult updateOrderExt(@RequestBody CcairbagUserAddr ccairbagUserAddr) {
        if (oConvertUtils.isEmpty(ccairbagUserAddr)){
            return AppResult.error("地址信息不能为空");
        }
        return ccairbagOrdersService.updateOrderExt(ccairbagUserAddr);
    }

    @ApiOperation(value = "访客查看订单详情")
    @GetMapping("/getOrderInfoExt")
    public AppResult getOrderInfoExt(@ApiParam(value = "订单id") String orderId,@ApiParam(value = "随机数") String randomNum) {

        return ccairbagOrdersService.getOrderInfoExt(orderId,randomNum);
    }

    @ApiOperation(value = "根据议价id 创建订单")
    @GetMapping("/createOrderByNegotiationId")
    public AppResult<CcairbagOrders> createOrderByNegotiationId(@ApiParam(value = "订单id") Long id) {
        return ccairbagOrdersService.createOrderByNegotiationId(id);
    }


    @ApiOperation(value = "普通用户通过购物车创建订单")
    @PostMapping("/createOrderByCart")
    public AppResult createOrderByCart(@RequestBody ShopCartDtoExt shopCartDtoExt) {
        return ccairbagOrdersService.createOrderByCart(shopCartDtoExt);
    }



    @ApiOperation("买家查询订单详情（含订单明细）")
    @GetMapping("/getOrderDetails")
    public AppResult<CcairbagOrders> getOrderDetails(@ApiParam(value = "订单id") Long orderId) {
        if (oConvertUtils.isEmpty(orderId)) {
            throw new RuntimeException("订单ID不能为空");
        }
        return ccairbagOrdersService.getOrderDetails(orderId);
    }

    @ApiOperation("卖家查询订单详情（含订单明细）")
    @GetMapping("/getSellerOrderDetails")
    public AppResult<CcairbagOrders> getSellerOrderDetails(@ApiParam(value = "订单id") Long orderId) {
        if (oConvertUtils.isEmpty(orderId)) {
            throw new RuntimeException("订单ID不能为空");
        }
        return ccairbagOrdersService.getSellerOrderDetails(orderId);
    }

    @ApiOperation("回显金额 信息")
    @PostMapping("/getOrderAmount")
    public AppResult getOrderAmount(@ApiParam(value = "订单id数组") @RequestParam List<Long> orderIds,
                                    @ApiParam(value = "支付类型 0 paypal 1支付宝")  int payType) {
        if (orderIds.isEmpty()) {
            throw new RuntimeException("订单id不能为空");
        }
        return ccairbagOrdersService.getOrderAmount(orderIds,payType);
    }



    @ApiOperation("生成类型生成支付链接 ")
    @PostMapping("/generatePayPalLink")
    public AppResult<List<AppResult>> generatePayPalLink(@ApiParam(value = "订单id数组")
                                                         @RequestParam List<Long> orderIds
                                                        ) {
        if (orderIds.isEmpty()) {
            throw new RuntimeException("订单id不能为空");
        }

        return ccairbagOrdersService.generatePaymentLink(orderIds);
    }

    @ApiOperation("生成支付宝支付链接")
    @PostMapping("/generateAliPayLink")
    public AppResult generateAliPayLink(@ApiParam(value = "订单id数组")
                                                         @RequestParam List<Long> orderIds                                                        ) throws AlipayApiException {
        if (orderIds.isEmpty()) {
            throw new RuntimeException("订单id不能为空");
        }
        return ccairbagOrdersService.generateAliPayLink(orderIds);
    }

    @ApiOperation("生成stripe支付链接")
    @PostMapping("/generateStripePayLink")
    public AppResult generateStripePayLink(@ApiParam(value = "订单id数组")
                                        @RequestParam List<Long> orderIds) {
        if (orderIds.isEmpty()) {
            throw new RuntimeException("订单id不能为空");
        }
        return ccairbagOrdersService.generateStripePayLink(orderIds);
    }

    @ApiOperation("生成stripe转账")
    @PostMapping("/generateStripePayoutExt")
    public AppResult generateStripePayout() {

        return ccairbagOrdersService.generateStripePayoutExt();
    }

    @ApiOperation("生成visa支付链接")
    @PostMapping("/generateVisaPay")
    public AppResult generateVisaPay(@ApiParam(value = "订单id数组") @RequestParam List<Long> orderIds  )  {
        if (orderIds.isEmpty()) {
            throw new RuntimeException("订单id不能为空");
        }
        return ccairbagOrdersService.generateVisaPay(orderIds);
    }


    @ApiOperation("支付宝回调")
    @PostMapping("/alinotify")
    public String payNotify(HttpServletRequest request) throws Exception {
        return ccairbagOrdersService.alinotify(request);
    }





    @ApiOperation("Stripe回调")
    @PostMapping("/stripeNotify")
    public void visaNotify(HttpServletRequest request, HttpServletResponse response)  {
         ccairbagOrdersService.stripeNotify(request,response);
    }



    @ApiOperation("查询PayPal订单详情 ")
    @GetMapping("/paypal/getOrderInfo")
    public AppResult getOrderInfo( @RequestParam String id) {
        logger.info("==========>查询PayPal订单详情");
        return ccairbagOrdersService.getOrderInfo(id);
    }

    @ApiOperation(" 已支付 去查订单 后 捕获订单")
    @GetMapping("/paypal/onApproveOrder")
    public AppResult onApproveOrder( @RequestParam String id) {
        logger.info("==========>捕获订单");
        return ccairbagOrdersService.onApproveOrder(id);
    }

    @ApiOperation(" 查询该客户的订单列表 ")
    @GetMapping("/paypal/getOrderList")
    public AppResult getOrderList(  @RequestParam(required = false ) Integer orderStatus,
                                    @RequestParam(required = false ) String search) {
        logger.info("==========>获取订单列表");
        return ccairbagOrdersService.getOrderList(orderStatus,search);
    }

    @ApiOperation(" 查询该客户的订单状态数量 ")
    @GetMapping("/paypal/getOrderNum")
    public AppResult getOrderNum() {
        logger.info("==========>查询该客户的订单状态数量");
        return ccairbagOrdersService.getOrderNum();
    }

    @ApiOperation(" 查询商家的的订单状态数量 ")
    @GetMapping("/paypal/getShopOrderNum")
    public AppResult getShopOrderNum(){
        logger.info("==========>查询商家的的订单状态数量");
        return ccairbagOrdersService.getShopOrderNum();
    }

    @ApiOperation(" 查询商家的的订单列表 ")
    @GetMapping("/paypal/getShopOrderList")
    public AppResult getShopOrderList(  @RequestParam(required = false ) Integer orderStatus) {
        logger.info("==========>获取商家订单列表");
        return ccairbagOrdersService. getShopOrderList(orderStatus);
    }


    @ApiOperation(" 删除订单 ")
    @PostMapping("/delOrders")
    public AppResult delOrders(@ApiParam(value = "订单id数组") @RequestParam List<Long> orderIds,
                               @ApiParam(value = "用户类型") @RequestParam Integer userType) {
        logger.info("==========>删除订单");
        return ccairbagOrdersService.delOrders(orderIds,userType);
    }

    @ApiOperation(" 未支付 主动取消订单 ")
    @GetMapping("/closeOrder")
    public AppResult closeOrder(@ApiParam(value = "订单id") @RequestParam String orderId) {
        logger.info("==========>删除订单");
        return ccairbagOrdersService.closeOrder(orderId);
    }


}
