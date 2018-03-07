package com.lcdt.contract.model;

import java.util.Date;

/**
 * @AUTHOR liuh
 * @DATE 2018-02-28
 */
public class ContractDto extends  Contract{

    /**
     * 列表状态筛选
     * 我的申请 - 0
     * 待我审批 - 1
     * 我已审批 - 2
     * 抄送 - 3
     */
    private int tempStatus;

    //当前登陆用户id
    private Long userId;

    private int pageNum;

    private int pageSize;

    public int getTempStatus() {
        return tempStatus;
    }

    public void setTempStatus(int tempStatus) {
        this.tempStatus = tempStatus;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
