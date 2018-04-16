package com.lcdt.traffic.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by yangbinq on 2018/4/16.
 * Desciption:财务-利润统计参数
 */
public class ProfitStatParamsDto implements java.io.Serializable {

    @ApiModelProperty(value = "运单创建日期始,格式：2018-01-02")
    private String createBegin;

    @ApiModelProperty(value = "运单创建日期止,格式：2018-01-02")
    private String createEnd;


    @ApiModelProperty(value = "运单号")
    private String waybillCode;


    public String getCreateBegin() {
        return createBegin;
    }

    public void setCreateBegin(String createBegin) {
        this.createBegin = createBegin;
    }

    public String getCreateEnd() {
        return createEnd;
    }

    public void setCreateEnd(String createEnd) {
        this.createEnd = createEnd;
    }

    public String getWaybillCode() {
        return waybillCode;
    }

    public void setWaybillCode(String waybillCode) {
        this.waybillCode = waybillCode;
    }
}
