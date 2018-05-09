package com.lcdt.warehouse.dto;

import com.lcdt.warehouse.entity.Allot;
import com.lcdt.warehouse.entity.AllotProduct;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by liz on 2018/5/8.
 */
public class AllotDto extends Allot{
    @ApiModelProperty(value = "查询调出开始时间")
    private String allotOutBeginTime;

    @ApiModelProperty(value = "查询调出结算时间")
    private String allotOutEndTime;

    @ApiModelProperty(value = "查询调入开始时间")
    private String allotInBeginTime;

    @ApiModelProperty(value = "查询调入结束时间")
    private String allotInEndTime;

    @ApiModelProperty(value = "商品list")
    private List<AllotProduct> allotProductList;

    public String getAllotOutBeginTime() {
        return allotOutBeginTime;
    }

    public void setAllotOutBeginTime(String allotOutBeginTime) {
        this.allotOutBeginTime = allotOutBeginTime;
    }

    public String getAllotOutEndTime() {
        return allotOutEndTime;
    }

    public void setAllotOutEndTime(String allotOutEndTime) {
        this.allotOutEndTime = allotOutEndTime;
    }

    public String getAllotInBeginTime() {
        return allotInBeginTime;
    }

    public void setAllotInBeginTime(String allotInBeginTime) {
        this.allotInBeginTime = allotInBeginTime;
    }

    public String getAllotInEndTime() {
        return allotInEndTime;
    }

    public void setAllotInEndTime(String allotInEndTime) {
        this.allotInEndTime = allotInEndTime;
    }

    public List<AllotProduct> getAllotProductList() {
        return allotProductList;
    }

    public void setAllotProductList(List<AllotProduct> allotProductList) {
        this.allotProductList = allotProductList;
    }
}
