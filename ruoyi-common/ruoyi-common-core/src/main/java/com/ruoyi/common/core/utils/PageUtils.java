package com.ruoyi.common.core.utils;

import com.github.pagehelper.PageHelper;
import com.ruoyi.common.core.utils.sql.SqlUtil;
import com.ruoyi.common.core.web.page.PageDomain;
import com.ruoyi.common.core.web.page.TableSupport;


/**
 * 分页工具类
 * 
 * @author ruoyi
 */
public class PageUtils extends PageHelper
{
    /**
     * 设置请求分页数据
     */
    public static void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
        Boolean reasonable = pageDomain.getReasonable();
        PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
    }

    /**
     * 清理分页的线程变量
     */
    public static void clearPage()
    {
        PageHelper.clearPage();
    }

    public static void startPage(Integer pageNum2,Integer pageSize2)
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
        Integer pageNum = pageNum2 == null ? pageDomain.getPageNum() : pageNum2;
        Integer pageSize = pageSize2 == null ? pageDomain.getPageSize() :pageSize2;
        Boolean reasonable = pageDomain.getReasonable();
        PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
    }

    public static void startPage(Integer pageNum2,Integer pageSize2,String orderBy2)
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageNum2 == null ? pageDomain.getPageNum() : pageNum2;
        Integer pageSize = pageSize2 == null ? pageDomain.getPageSize() :pageSize2;
        Boolean reasonable = pageDomain.getReasonable();
        String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
        if(StringUtils.isEmpty(orderBy)){
            orderBy = orderBy2;
        }
        PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
    }
}
