package com.lcdt.contract.web.dto;

import com.lcdt.contract.model.Order;
import com.lcdt.contract.model.OrderApproval;

import java.util.List;

public class OrderApprovalDto extends Order{

    private List<OrderApproval> orderApprovalList;

    public List<OrderApproval> getOrderApprovalList() {
        return orderApprovalList;
    }

    public void setOrderApprovalList(List<OrderApproval> orderApprovalList) {
        this.orderApprovalList = orderApprovalList;
    }
}