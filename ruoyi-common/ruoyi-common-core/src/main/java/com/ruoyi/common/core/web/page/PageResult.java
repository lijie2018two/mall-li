package com.ruoyi.common.core.web.page;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {

    /**
     * 总记录数
     */
    private long total;

    private int pages;
    /**
     * 消息状态码
     */
    private int code;

    /**
     * 消息内容
     */
    private String msg;

//    /**
//     * 当前页码
//     */
//    private int pageNum;
//    /**
//     * 每页数量
//     */
//    private int pageSize;
    /**
     * 返回的各类数据模型
     */
    private List<T> rows;

    public PageResult pageResult(PageInfo<T> pageInfo) {
        PageResult<T> pageResult = new PageResult<T>();
//        pageResult.setPageNum(pageInfo.getPageNum());
//        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setPages(pageInfo.getPages());
        pageResult.setRows(pageInfo.getList());
        pageResult.setMsg("查询成功");
        pageResult.setCode(200);
        return pageResult;
    }
    public PageResult() {
    }

}
