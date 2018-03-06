package com.lcdt.contract.model;

import java.util.Date;

/**
 * @AUTHOR liuh
 * @DATE 2018-02-28
 */
public class ContractDto extends  Contract{



    private int pageNum;

    private int pageSize;


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
