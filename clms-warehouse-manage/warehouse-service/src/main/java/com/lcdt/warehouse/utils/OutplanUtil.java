package com.lcdt.warehouse.utils;

/**
 * Created by yangbinq on 2018/7/27.
 */
public class OutplanUtil {


    /***
     * 出库类型
     * @param type
     * @return
     */
    public static String convertStorageType(String type){

        String result = "";
        switch (type) {
            case "01":
                result = "原料出库";
                break;
            case "02":
                result = "成品出库";
                break;
            case "03":
                result = "残次品出库";
                break;
            default:
                result = "其它";
        }

        return  result;
    }


}
