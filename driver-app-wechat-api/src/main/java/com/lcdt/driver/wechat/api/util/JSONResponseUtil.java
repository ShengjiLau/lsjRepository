package com.lcdt.driver.wechat.api.util;


public final class JSONResponseUtil {

    private static final int SUCCESS_CODE = 0;

    public static<T> ResponseMessage success(T obj, String message) {
        return new ResponseMessage(obj, SUCCESS_CODE, message);
    }


    public static<T> ResponseMessage success(T obj){
        return success(obj, "请求成功");
    }


}
