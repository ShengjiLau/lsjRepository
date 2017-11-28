package com.lcdt.items.web.dto;

import com.lcdt.converter.ResponseData;

import java.util.List;

/**
 * 统一分页response内容
 * @AUTHOR liuh
 * @DATE 2017-11-28
 */
public class BaseDto<T> implements ResponseData{

    private List<T> list;
    private long total;

    public BaseDto(List<T> list) {
        this.list = list;
    }

    public BaseDto(List<T> list, long total) {
        this.list = list;
        this.total = total;
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
