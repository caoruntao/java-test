package com.caort.mail.pojo;

/**
 * @author Caort
 * @date 2021/11/26 下午2:00
 */
public class PageInfo {
    private int page;
    private int pageSize;
    private int total;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
