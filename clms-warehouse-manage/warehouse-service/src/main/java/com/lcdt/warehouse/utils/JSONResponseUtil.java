package com.lcdt.warehouse.utils;


public final class JSONResponseUtil {


    public static<T> ResponseMessage success(T obj){
        ResponseMessage<T> tResponseMessage = new ResponseMessage<>();
        tResponseMessage.setData(obj);
        tResponseMessage.setResult(0);
        return tResponseMessage;
    }


}
