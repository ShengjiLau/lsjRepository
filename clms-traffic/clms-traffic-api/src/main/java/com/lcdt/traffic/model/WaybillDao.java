package com.lcdt.traffic.model;

import java.util.List;

/**
 * Created by lyqishan on 2017/12/28
 */

public class WaybillDao extends Waybill{
    List<WaybillItems> waybillItemsList;
    List<WaybillTransferRecord> waybillTransferRecordList;
    public List<WaybillItems> getWaybillItemsList() {
        return waybillItemsList;
    }

    public void setWaybillItemsList(List<WaybillItems> waybillItemsList) {
        this.waybillItemsList = waybillItemsList;
    }

    public List<WaybillTransferRecord> getWaybillTransferRecordList() {
        return waybillTransferRecordList;
    }

    public void setWaybillTransferRecordList(List<WaybillTransferRecord> waybillTransferRecordList) {
        this.waybillTransferRecordList = waybillTransferRecordList;
    }
}
