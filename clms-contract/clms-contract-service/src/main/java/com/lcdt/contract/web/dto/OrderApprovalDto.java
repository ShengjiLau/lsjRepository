package com.lcdt.contract.web.dto;

import com.lcdt.contract.model.Order;
import com.lcdt.contract.model.OrderApproval;
import com.lcdt.contract.model.OrderProduct;

import java.util.List;

public class OrderApprovalDto extends Order{

    private List<OrderProduct> orderProductList;
    private List<OrderApproval> orderApprovalList;

    public List<OrderProduct> getOrderProductList() {
        return orderProductList;
    }

    public void setOrderProductList(List<OrderProduct> orderProductList) {
        this.orderProductList = orderProductList;
    }

    public List<OrderApproval> getOrderApprovalList() {
        return orderApprovalList;
    }

    public void setOrderApprovalList(List<OrderApproval> orderApprovalList) {
        this.orderApprovalList = orderApprovalList;
    }
}