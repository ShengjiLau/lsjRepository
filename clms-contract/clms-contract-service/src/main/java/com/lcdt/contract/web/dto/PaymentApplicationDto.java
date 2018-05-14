package com.lcdt.contract.web.dto;

import com.lcdt.contract.model.PaApproval;
import com.lcdt.contract.model.PaymentApplication;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2018-05-08
 */
public class PaymentApplicationDto extends PaymentApplication {

    private String paymentTimeStart;

    private String PaymentTimeEnd;

    private List<PaApproval> paApprovalList;

    private int pageNum;

    private int pageSize;

    public String getPaymentTimeStart() {
        return paymentTimeStart;
    }

    public void setPaymentTimeStart(String paymentTimeStart) {
        this.paymentTimeStart = paymentTimeStart;
    }

    public String getPaymentTimeEnd() {
        return PaymentTimeEnd;
    }

    public void setPaymentTimeEnd(String paymentTimeEnd) {
        PaymentTimeEnd = paymentTimeEnd;
    }

    public List<PaApproval> getPaApprovalList() {
        return paApprovalList;
    }

    public void setPaApprovalList(List<PaApproval> paApprovalList) {
        this.paApprovalList = paApprovalList;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
