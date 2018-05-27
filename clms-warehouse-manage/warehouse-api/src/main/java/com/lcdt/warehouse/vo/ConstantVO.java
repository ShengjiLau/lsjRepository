package com.lcdt.warehouse.vo;

/**
 * Created by lyqishan on 2018/5/11
 */

public class ConstantVO {

    //入库单状态
    public static int IN_ORDER_STATUS_WATIE_STORAGE=1; //待入库
    public static int IN_ORDER_STATUS_HAVE_STORAGE=2;//已入库
    public static int IN_ORDER_STATUS_HAVE_CANCEL=3;//已取消


    //出库单状态
    public static int OUT_ORDER_STATUS_WATIE_OUTBOUND=1; //待出库
    public static int OUT_ORDER_STATUS_HAVE_OUTBOUND=2;//已出库
    public static int OUT_ORDER_STATUS_HAVE_CANCEL=3;//已取消
}
