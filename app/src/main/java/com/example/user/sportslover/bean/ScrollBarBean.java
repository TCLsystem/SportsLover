package com.example.user.sportslover.bean;

import java.util.List;

/**
 * Created by user on 17-10-10.
 */

public class ScrollBarBean {

    private int total;

    private List<ScrollPerBarBean> scrollPerBarBeanList;

    public List<ScrollPerBarBean> getLists() {
        return scrollPerBarBeanList;
    }

    public void setLists(List<ScrollPerBarBean> scrollPerBarBeanList) {
        this.scrollPerBarBeanList = scrollPerBarBeanList;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
