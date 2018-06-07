package com.lcdt.traffic.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xrr on 2018/5/31.
 */
public class WaybillUtil {
    public final static Map<String,String> map_waybill_status = new HashMap<String,String>() {
        {
            put("1","运单生成"); //
            put("2","入厂");//
            put("3","出库");//
            put("4","发货"); //
            put("5","卸货"); //
            put("6","签收确认");//
            put("7","运单完成"); //
            put("8","运单取消"); //
        }
    };
}
