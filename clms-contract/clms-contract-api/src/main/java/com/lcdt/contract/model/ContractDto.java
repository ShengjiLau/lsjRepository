package com.lcdt.contract.model;

import java.util.Date;

/**
 * @AUTHOR liuh
 * @DATE 2018-02-28
 */
public class ContractDto extends  Contract{

    private Date signBeginDate;
    private Date signEndDate;

    private int pageNum;

    private int pageSize;

    public Date getSignBeginDate() {
        return signBeginDate;
    }

    public void setSignBeginDate(Date signBeginDate) {
        this.signBeginDate = signBeginDate;
    }

    public Date getSignEndDate() {
        return signEndDate;
    }

    public void setSignEndDate(Date signEndDate) {
        this.signEndDate = signEndDate;
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
