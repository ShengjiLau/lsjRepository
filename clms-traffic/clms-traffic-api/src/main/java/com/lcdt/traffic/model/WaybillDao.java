package com.lcdt.traffic.model;

import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * Created by lyqishan on 2017/12/28
 */

public class WaybillDao extends Waybill{
    List<WaybillItems> waybillItemsList;
    List<WaybillTransferRecord> waybillTransferRecordList;
    List<WaybillPositionSetting> waybillPositionSettingList;
    List<WaybillLeaveMsg> waybillLeaveMsgList;

    JSONArray electronicalReceiptJson;

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

    public List<WaybillPositionSetting> getWaybillPositionSettingList() {
        return waybillPositionSettingList;
    }

    public void setWaybillPositionSettingList(List<WaybillPositionSetting> waybillPositionSettingList) {
        this.waybillPositionSettingList = waybillPositionSettingList;
    }

    public List<WaybillLeaveMsg> getWaybillLeaveMsgList() {
        return waybillLeaveMsgList;
    }

    public void setWaybillLeaveMsgList(List<WaybillLeaveMsg> waybillLeaveMsgList) {
        this.waybillLeaveMsgList = waybillLeaveMsgList;
    }

    public JSONArray getElectronicalReceiptJson() {
        return JSONArray.parseArray(getElectronicalReceipt());
    }

    public void setElectronicalReceiptJson(JSONArray electronicalReceiptJson) {
        this.electronicalReceiptJson = electronicalReceiptJson;
    }
}
