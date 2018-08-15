package com.lcdt.contract.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lyqishan on 2018/8/15
 */

public class CustomerOrderStatusParams {
    @ApiModelProperty(value = "销售单id")
    private Long orderId;
    @ApiModelProperty(value = "企业id",hidden = true)
    private Long companyId;
    @ApiModelProperty(value = "客户销售单状态",hidden = true)
    private Short customerOrderStatus;

    public Long getOrderId() {
        return orderId;
    }

    public CustomerOrderStatusParams setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public CustomerOrderStatusParams setCompanyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public Short getCustomerOrderStatus() {
        return customerOrderStatus;
    }

    public CustomerOrderStatusParams setCustomerOrderStatus(Short customerOrderStatus) {
        this.customerOrderStatus = customerOrderStatus;
        return this;
    }
}
