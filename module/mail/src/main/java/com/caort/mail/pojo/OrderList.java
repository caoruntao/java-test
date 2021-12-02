package com.caort.mail.pojo;

import java.util.List;

/**
 * @author Caort
 * @date 2021/11/26 下午2:06
 */
public class OrderList {
    private PageInfo pageInfo;
    private List<SimpleOrderInfo> list;

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<SimpleOrderInfo> getList() {
        return list;
    }

    public void setList(List<SimpleOrderInfo> list) {
        this.list = list;
    }
}
