package com.lcdt.contract.web.dto;

import com.lcdt.contract.model.ApprovalProcess;
import com.lcdt.contract.model.ProcessApprover;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2018-03-08
 */
public class ApprovalProcessDto extends ApprovalProcess{

    private List<ProcessApprover> processApproverList;

    private int pageNum;

    private int pageSize;

    public List<ProcessApprover> getProcessApproverList() {
        return processApproverList;
    }

    public void setProcessApproverList(List<ProcessApprover> processApproverList) {
        this.processApproverList = processApproverList;
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
