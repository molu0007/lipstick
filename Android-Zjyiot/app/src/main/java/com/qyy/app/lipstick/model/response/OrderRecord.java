package com.qyy.app.lipstick.model.response;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dengwg
 * @date 2018/3/20
 */
public class OrderRecord {



    private int offset;
    private int limit;
    private int pageNo;
    private int pageSize;
    private int totalCount;
    private int totalPages;
    private int first;


    private List<OrderDetail> result=new ArrayList<>();

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public List<OrderDetail> getResult() {
        return result;
    }

    public void setResult(List<OrderDetail> result) {
        this.result = result;
    }


}
