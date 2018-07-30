package com.lcdt.manage.dto;

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
        this.list = list;
        total = list.size();
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
