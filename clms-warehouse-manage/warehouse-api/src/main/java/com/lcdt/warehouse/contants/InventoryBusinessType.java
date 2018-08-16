package com.lcdt.warehouse.contants;

public final class InventoryBusinessType {

    public static final Integer OUTORDER = 1;//出库

    public static final Integer INORDER = 0;//入库

    public static final Integer  FORMATORDER = 3;//盘库
    
    public static final Integer SHIFT_ORDER = 2; //移库
    
    public static final Integer ALLOT_ORDER = 4; //调拨

    public static final Integer INVENTORY_INIT = 5; // 库存初始化
    
    public static final Integer TRANSFER_ORDER_MATERIAL = 6;//库存转换单消耗商品
    
    public static final Integer TRANSFER_ORDER_PRODUCT = 7;//库存转换单生成商品

}
