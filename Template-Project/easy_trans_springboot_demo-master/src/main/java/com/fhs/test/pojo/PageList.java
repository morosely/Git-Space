package com.fhs.test.pojo;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.ArrayList;
import java.util.List;

public class PageList<E> {

    List<E> rows;

    long total;

    long page;

    long pageSize;

    public PageList() {
    }

    public PageList(List<E> c) {
        if (c instanceof IPage) {
            IPage<E> page = (IPage)c;
            this.setRows(page.getRecords());
            this.total = page.getTotal();
            this.page = page.getCurrent();
            this.pageSize = page.getSize();
        } else {
            this.setRows(c);
            this.total = (long)this.rows.size();
        }

    }

    public PageList(IPage<E> page) {
        this.setRows(page.getRecords());
        this.total = page.getTotal();
        this.page = page.getCurrent();
        this.pageSize = page.getSize();
    }

    public List<E> getRows() {
        return this.rows;
    }

    public void setRows(List<E> rows) {
        this.rows = rows;
        if (rows.size() == 0) {
            this.rows = new ArrayList();
        }

    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPage() {
        return this.page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }
}
