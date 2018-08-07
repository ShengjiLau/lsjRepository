package com.lcdt.driver.dto;

import com.github.pagehelper.Page;
import com.lcdt.converter.ResponseData;

import java.util.ArrayList;
import java.util.List;

/**
 * 统一分页response内容
 * @AUTHOR liuh
 * @DATE 2017-11-28
 */
public class PageBaseDto<T> implements ResponseData {

    private List<T> list;
    private long total;

    public PageBaseDto(List<T> list) {
        if (list instanceof Page) {
            this.total = ((Page) list).getTotal();
            this.list = ((Page) list).getResult();
        }else{
            this.list = list;
            this.total = Long.valueOf(list.size());
        }
    }

    public PageBaseDto(List<T> list, long total) {
        this.list = list==null? (List<T>) new ArrayList<T>() :list;
        this.total = total;
    }

    public PageBaseDto() {

    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
