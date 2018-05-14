package com.lcdt.contract.web.dto;

import com.lcdt.contract.model.BillingRecord;

/**
 * @AUTHOR liuh
 * @DATE 2018-05-07
 */
public class BillingRecordDto extends BillingRecord {

    private String billingDateStart;

    private String billingDateEnd;

    private int pageNum;

    private int pageSize;

    public String getBillingDateStart() {
        return billingDateStart;
    }

    public void setBillingDateStart(String billingDateStart) {
        this.billingDateStart = billingDateStart;
    }

    public String getBillingDateEnd() {
        return billingDateEnd;
    }

    public void setBillingDateEnd(String billingDateEnd) {
        this.billingDateEnd = billingDateEnd;
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
