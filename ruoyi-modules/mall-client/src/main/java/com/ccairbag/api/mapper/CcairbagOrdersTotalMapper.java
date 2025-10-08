package com.ccairbag.api.mapper;


import com.ruoyi.system.api.domain.ccairbag.CcairbagOrdersTotal;

import java.util.List;

/**
 * 订单表;(ccairbag_orders_total)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2025-4-7
 */
public interface CcairbagOrdersTotalMapper{

    CcairbagOrdersTotal queryById(Long orderTotalId);

    List<CcairbagOrdersTotal> queryAll(CcairbagOrdersTotal ccairbagOrdersTotal);

    int insert(CcairbagOrdersTotal ccairbagOrdersTotal);


    int update(CcairbagOrdersTotal ccairbagOrdersTotal);

}