package com.shirleydl.mtest.vo;

import java.util.ArrayList;
import java.util.List;

public class PageHelper<T> {
    // 实体类集合
    private List<T> data = new ArrayList<T>();

    private long current;

    private long size;
    // 数据总条数
    private long total;

    private long pages;


    public PageHelper() {
        super();
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }
}