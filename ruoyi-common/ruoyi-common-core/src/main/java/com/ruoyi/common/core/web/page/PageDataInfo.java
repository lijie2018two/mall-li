package com.ruoyi.common.core.web.page;

import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.constant.HttpStatus;

import java.io.Serializable;
import java.util.List;

/**
 * 表格分页数据对象
 * 
 * @author ruoyi
 */
public class PageDataInfo<T> implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 总记录数 */
    private long total;

    /** 列表数据 */
    private List<T> rows;

    /** 消息状态码 */
    private int code;

    /** 消息内容 */
    private String msg;

    private int pages;

    /**
     * 表格数据对象
     */
    public PageDataInfo()
    {
    }

    /**
     * 分页
     *
     * @param list 列表数据
     * @param total 总记录数
     */
    public PageDataInfo(List<T> list, int total)
    {
        this.code = HttpStatus.SUCCESS;
        this.msg = "";
        this.rows = list;
        this.total = total;
    }

    public PageDataInfo(List<T> list)
    {
        this.code = HttpStatus.SUCCESS;
        this.msg = "";
        this.rows = list;
        PageInfo pageInfo2 = new PageInfo(list);
        this.total = pageInfo2.getTotal();
        this.pages = pageInfo2.getPages();
    }

    public PageDataInfo(int code,String msg, List<T> list, int total)
    {
        this.code = code;
        this.msg = msg;
        this.rows = list;
        this.total = total;
    }
    public PageDataInfo(String msg, List<T> list, int total)
    {
        this.code = HttpStatus.SUCCESS;
        this.msg = msg;
        this.rows = list;
        this.total = total;
    }

    public long getTotal()
    {
        return total;
    }

    public void setTotal(long total)
    {
        this.total = total;
    }

    public List<?> getRows()
    {
        return rows;
    }

    public void setRows(List<T> rows)
    {
        this.rows = rows;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}