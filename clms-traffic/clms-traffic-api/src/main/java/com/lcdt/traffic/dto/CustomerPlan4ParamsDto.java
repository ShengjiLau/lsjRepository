package com.lcdt.traffic.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by yangbinq on 2018/4/4.
 */
public class CustomerPlan4ParamsDto implements java.io.Serializable {


    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "排序字段， start_date--起运时间；arrive_date--显示到达时间")
    private String orderFiled; //排序字段

    @ApiModelProperty(value = "排序方式desc/asc")
    private String orderDesc;  //排序方式

    @ApiModelProperty(value = "页码")
    private int pageNo; //分页（第几页）

    @ApiModelProperty(value = "每页显示条数")
    private int pageSize;//每页多少

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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
