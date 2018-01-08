package com.lcdt.traffic.web.dto;

import com.lcdt.converter.ResponseData;

import java.util.List;

/**
 * 统一分页response内容
 * @AUTHOR liuh
 * @DATE 2017-11-28
 */
public class BaseDto<T> implements ResponseData{

    private T detail;

    public BaseDto(T detail) {
        this.detail = detail;
    }

    public T getDetail() {
        return detail;
    }

    public void setDetail(T detail) {
        this.detail = detail;
    }
}
