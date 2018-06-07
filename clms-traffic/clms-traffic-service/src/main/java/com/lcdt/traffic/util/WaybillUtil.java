package com.lcdt.traffic.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xrr on 2018/5/31.
 */
public class WaybillUtil {
    public final static Map<Short,String> map_waybill_status = new HashMap<Short,String>() {
        {
            put((short)1,"运单生成"); //
            put((short)2,"入厂");//
            put((short)3,"出库");//
            put((short)4,"发货"); //
            put((short)5,"卸货"); //
            put((short)6,"签收确认");//
            put((short)7,"运单完成"); //
            put((short)8,"运单取消"); //
        }
    };
}
