package com.lcdt.traffic.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by yangbinq on 2018/4/3.
 */
public class Plan4CarrierParamsDto implements java.io.Serializable{

/*    @ApiModelProperty(value = "发布计划的企业ID集合，有多个id时用 , 隔开")
    private String compyIds; //发布计划的企业ID集合*/

    @ApiModelProperty(value = "计划状态  待发布-10\n" +
            "\n" +
            "竞价中-15\n" +
            "\n" +
            "审批中-20\n" +
            "\n" +
            "派单中-30\n" +
            "\n" +
            "已派完-40\n" +
            "\n" +
            "已完成-50\n" +
            "\n" +
            "已取消-60\n")
    private String planStatus;

    @ApiModelProperty(value = "派单类型0-其它\n" +
            "1-直派\n" +
            "2-竞价\n")
    private String sendOrderType;



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


/*    public String getCompyIds() {
        return compyIds;
    }

    public void setCompyIds(String compyIds) {
        this.compyIds = compyIds;
    }*/

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public String getSendOrderType() {
        return sendOrderType;
    }

    public void setSendOrderType(String sendOrderType) {
        this.sendOrderType = sendOrderType;
    }

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
