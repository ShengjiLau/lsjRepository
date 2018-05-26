package com.lcdt.warehouse.dto;

import com.lcdt.warehouse.entity.OutWarehouseOrder;

import java.util.List;

/**
 * Created by lyqishan on 2018/5/25
 */

public class OutWhOrderDto extends OutWarehouseOrder{

    private List<OutOrderGoodsInfoDto> outOrderGoodsInfoList;

    private int operationType;

    public List<OutOrderGoodsInfoDto> getOutOrderGoodsInfoList() {
        return outOrderGoodsInfoList;
    }

    public void setOutOrderGoodsInfoList(List<OutOrderGoodsInfoDto> outOrderGoodsInfoList) {
        this.outOrderGoodsInfoList = outOrderGoodsInfoList;
    }

    public int getOperationType() {
        return operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }
}
