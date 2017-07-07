package com.ruanyh.util.common.paging;


import java.util.List;

/**
 * 分页数据
 * @param <T>
 */
public class Page<T> {
    private Integer pageNum;        // 当前页码
    private Integer pageSize;       // 每页记录数
    private Integer totalRecord;    // 总记录数
    private Integer totalPage;      // 总页数
    private List<T> data;           // 查询结果集

    public Page(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum == null || pageNum < 1 ? 1 : pageNum;
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
    }

    public Integer getTotalPage() {
        totalPage = totalRecord / pageSize;
        if (totalRecord % pageSize != 0) {
            totalPage += 1;
        }
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
