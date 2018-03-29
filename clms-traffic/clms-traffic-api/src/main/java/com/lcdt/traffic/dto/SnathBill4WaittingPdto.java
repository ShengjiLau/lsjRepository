package com.lcdt.traffic.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by yangbinq on 2018/3/19.
 * Description: 待抢单条件
 */
public class SnathBill4WaittingPdto implements java.io.Serializable {

    @ApiModelProperty(value = "发布计划的企业ID集合，有多个id时用 , 隔开")
    private String compyIds; //发布计划的企业ID集合

    @ApiModelProperty(value = "排序字段， start_date--起运时间；arrive_date--显示到达时间")
    private String orderFiled; //排序字段

    @ApiModelProperty(value = "排序方式desc/asc")
    private String orderDesc;  //排序方式

    @ApiModelProperty(value = "登录司机ID")
    private Long driverId; //登录司机ID

    @ApiModelProperty(value = "页码")
    private int pageNo; //分页（第几页）

    @ApiModelProperty(value = "每页显示条数")
    private int pageSize;//每页多少



    public String getCompyIds() {
        return compyIds;
    }

    public void setCompyIds(String compyIds) {
        this.compyIds = compyIds;
    }

    public String getOrderFiled() {
        return orderFiled;
    }

    public void setOrderFiled(String orderFiled) {
        this.orderFiled = orderFiled;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
