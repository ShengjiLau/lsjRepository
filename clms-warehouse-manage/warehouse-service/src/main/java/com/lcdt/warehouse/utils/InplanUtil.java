package com.lcdt.warehouse.utils;

/**
 * Created by yangbinq on 2018/7/27.
 */
public class InplanUtil {


    /***
     * 入库类型
     * @param type
     * @return
     */
    public static String convertStorageType(String type){

        String result = "";
        switch (type) {
            case "01":
                result = "原料入库";
                break;
            case "02":
                result = "成品入库";
                break;
            case "03":
                result = "退换货入库";
                break;
            case "04":
                result = "采购入库";
                break;
            default:
                result = "其它";
        }

        return  result;
    }



}
