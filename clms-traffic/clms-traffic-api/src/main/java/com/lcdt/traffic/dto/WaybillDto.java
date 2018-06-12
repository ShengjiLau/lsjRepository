package com.lcdt.traffic.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lcdt.traffic.model.Waybill;
import io.swagger.annotations.ApiModelProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lyqishan on 2017/12/21
 * 运单新增dto
 */

public class WaybillDto extends Waybill implements java.io.Serializable {

    private List<WaybillItemsDto> waybillItemsDtoList;

    public List<WaybillItemsDto> getWaybillItemsDtoList() {
        return waybillItemsDtoList;
    }

    public void setWaybillItemsDtoList(List<WaybillItemsDto> waybillItemsDtoList) {
        this.waybillItemsDtoList = waybillItemsDtoList;
    }
}
