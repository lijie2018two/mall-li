package com.ccairbag.api.controller;

import com.ccairbag.api.service.ICcairbagOrderDetailsService;
import com.ruoyi.common.core.utils.oConvertUtils;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AppResult;
import com.ruoyi.system.api.domain.ccairbag.dto.RefundOrderDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单详情Controller
 * 
 * @author lidabai
 * @date 2025-02-20
 */
@RestController
@RequestMapping("ccairbag/details")
public class CcairbagOrderDetailsController extends BaseController {
    @Autowired
    private ICcairbagOrderDetailsService ccairbagOrderDetailsService;


    @ApiOperation(" 给子订单添加 快递单号 ")
    @GetMapping("/setDvyFlowNum")
    public AppResult setDvyFlowNum(@ApiParam("detailId") Long detailId,
                                   @ApiParam(value = "快递单号") String dvyFlowNum,
                                   @ApiParam(value = "快递公司编码") String courierCode,
                                   @ApiParam(value = "快递公司名称") String logisticsName) throws Exception {
        logger.info("==========>给子订单添加 快递单号");
        return ccairbagOrderDetailsService.setDvyFlowNum(detailId, dvyFlowNum, courierCode, logisticsName);
    }

    @ApiOperation(" 用户订单 签收 ")
    @GetMapping("/signOrder")
    public AppResult signOrder(@ApiParam("detailId") Long detailId) {
        logger.info("==========>用户订单 签收 ");
        return ccairbagOrderDetailsService.signOrder(detailId);
    }

    @ApiOperation(" 用户 申请退款 进入表单页面 获取字典")
    @GetMapping("/refundOrderIng")
    public AppResult refundOrderIng(@ApiParam("detailId") Long detailId) {
        logger.info("==========>用户申请退款  进入表单页面 ");
        return ccairbagOrderDetailsService.refundOrderIng(detailId);
    }



    @ApiOperation(" 卖家取消订单 进入表单页面获取字典")
    @GetMapping("/shopRefundOrderIng")
    public AppResult shopRefundOrderIng(@ApiParam("detailId") Long detailId) {
        logger.info("==========>卖家取消订单  进入表单页面 ");
        return ccairbagOrderDetailsService.shopRefundOrderIng(detailId);
    }


    @ApiOperation(" 用户 申请退款 提交表单")
    @PostMapping("/refundOrder")
    public AppResult refundOrder(@RequestBody RefundOrderDTO refundOrderDTO) {
        logger.info("==========>用户申请退款  提交表单 ");
        if (oConvertUtils.isEmpty(refundOrderDTO.getDetailId())) {
            return AppResult.error("The sub-order ID cannot be empty.");
        }
        return ccairbagOrderDetailsService.refundOrder(refundOrderDTO);
    }


    @ApiOperation(" 用户 填写退货运单号 ")
    @PostMapping("/refundOrderExt")
    public AppResult refundOrderExt(@RequestBody RefundOrderDTO refundOrderDTO) {
        logger.info("==========>用户 填写退货运单号 ");
        if (oConvertUtils.isEmpty(refundOrderDTO.getDetailId())) {
            return AppResult.error("The sub-order ID cannot be empty.");
        }
        if (oConvertUtils.isEmpty(refundOrderDTO.getRefundDvyFlowNum())) {
            return AppResult.error("退货运单号不能为空");
        }
        return ccairbagOrderDetailsService.refundOrderExt(refundOrderDTO);
    }

    /***
     * 1-待发货（卖家未填运单号，未发货）→ 卖家主动发起退款 → 10-退款成功（系统直接触发退款，无需审核）→ 8-退货完成（终态，反向履约闭环）
     * @param refundOrderDTO
     * @return
     */
    @ApiOperation(" 商家取消订单（退款） 提交表单")
    @PostMapping("/refundShopOrder")
    public AppResult refundShopOrder(@RequestBody RefundOrderDTO refundOrderDTO) {
        logger.info("==========>商家取消订单（退款）  提交表单 ");
        if (oConvertUtils.isEmpty(refundOrderDTO.getDetailId())) {
            return AppResult.error("The sub-order ID cannot be empty.");
        }
        return ccairbagOrderDetailsService.refundShopOrder(refundOrderDTO);
    }

    @ApiOperation(" 查看 退款类型原因 字典数组 ")
    @GetMapping("/getRefundOrderType")
    public AppResult getRefundOrderType() {
        logger.info("==========> 查看 退款类型原因 字典数组 ");

        return ccairbagOrderDetailsService.getRefundOrderType();
    }


    /**
     *
     * 判断是否填写了运单号，
     * 未填写运单号：1 - 待发货 → 3 - 退款申请中 → 10 - 退款成功 → 8 - 退货完成
     * 已填写运单号：2 - 待收货 → 3 - 退款申请中 → 4 - 待退货 → 5 - 退货运输中 → 10 - 退款成功 → 8 - 退货完成
     * @param refundOrderDTO
     * @return
     */
    @ApiOperation(" 商家审核 第一次审核 运输中 退款订单  ")
    @PostMapping("/refundShopOrderSign")
    public AppResult refundShopOrderSign(@RequestBody RefundOrderDTO refundOrderDTO) {
        logger.info("==========>商家审核 第一次审核 运输中 退款订单 ");
        if (oConvertUtils.isEmpty(refundOrderDTO.getDetailId())) {
            return AppResult.error("The sub-order ID cannot be empty.");
        }
        return ccairbagOrderDetailsService.refundShopOrderSign(refundOrderDTO);
    }

    @ApiOperation(" 商家审核 第二次审核 退货运输中 退款订单  ")
    @PostMapping("/refundShopOrderSignExt")
    public AppResult refundShopOrderSignExt(@RequestBody RefundOrderDTO refundOrderDTO) {
        logger.info("==========>商家审核 第二次审核 退货运输中 退款订单  ");
        if (oConvertUtils.isEmpty(refundOrderDTO.getDetailId())) {
            return AppResult.error("The sub-order ID cannot be empty.");
        }
        return ccairbagOrderDetailsService.refundShopOrderSignExt(refundOrderDTO);
    }



    @ApiOperation(" 用户提交平台介入  ")
    @PostMapping("/platformIntervention")
    public AppResult platformIntervention(@RequestBody RefundOrderDTO refundOrderDTO) {
        logger.info("==========>用户提交平台介入 ");
        if (oConvertUtils.isEmpty(refundOrderDTO.getDetailId())) {
            return AppResult.error("The sub-order ID cannot be empty.");
        }
        if (oConvertUtils.isEmpty(refundOrderDTO.getBuyerMsgExt())) {
            return AppResult.error("The reason for requesting platform intervention cannot be empty.");
        }
        if (oConvertUtils.isEmpty(refundOrderDTO.getPhotoFilesExt())) {
            return AppResult.error("The images for requesting platform intervention cannot be empty.");
        }

        return ccairbagOrderDetailsService.platformIntervention(refundOrderDTO);
    }

    @ApiOperation(" 查询子订单的 物流信息 ")
    @GetMapping("/getDvyFlowInfo")
    public AppResult getDvyFlowInfo(@ApiParam("detailId") Long detailId) {
        logger.info("==========>查询子订单的 物流信息");
        return ccairbagOrderDetailsService.getDvyFlowInfo(detailId);
    }






    @ApiOperation(" 查询物流公司列表 ")
    @GetMapping("/getDvyCompanyList")
    public AppResult getDvyCompanyList() {
        logger.info("==========>查询物流公司列表");
        return ccairbagOrderDetailsService.getDvyCompanyList();
    }

}

