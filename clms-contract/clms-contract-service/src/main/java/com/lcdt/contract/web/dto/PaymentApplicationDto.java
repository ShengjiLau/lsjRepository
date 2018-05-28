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

    private String paymentTimeEnd;

    private List<PaApproval> paApprovalList;

    private Short tempStatus;

    private int pageNum;

    private int pageSize;

    public String getPaymentTimeStart() {
        return paymentTimeStart;
    }

    public void setPaymentTimeStart(String paymentTimeStart) {
        this.paymentTimeStart = paymentTimeStart;
    }

    public String getPaymentTimeEnd() {
        return paymentTimeEnd;
    }

    public void setPaymentTimeEnd(String paymentTimeEnd) {
        this.paymentTimeEnd = paymentTimeEnd;
    }

    public List<PaApproval> getPaApprovalList() {
        return paApprovalList;
    }

    public void setPaApprovalList(List<PaApproval> paApprovalList) {
        this.paApprovalList = paApprovalList;
    }

    public Short getTempStatus() {
        return tempStatus;
    }

    public void setTempStatus(Short tempStatus) {
        this.tempStatus = tempStatus;
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
