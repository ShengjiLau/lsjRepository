package com.lcdt.driver.wechat.api.util;

public class ResponseMessage<T> {


    private T data;
    private Integer code;

    private String message;

    public ResponseMessage(T data, Integer code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
