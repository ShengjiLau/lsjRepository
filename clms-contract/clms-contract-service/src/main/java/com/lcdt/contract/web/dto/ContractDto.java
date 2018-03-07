package com.lcdt.contract.web.dto;

import com.lcdt.contract.model.Contract;
import com.lcdt.contract.model.ContractApproval;

import java.util.List;

/**
 * Created by liz on 2018/3/7.
 */
public class ContractDto extends Contract{

    private List<ContractApproval> contractApprovalList;

    private int pageNum;

    private int pageSize;

    public List<ContractApproval> getContractApprovalList() {
        return contractApprovalList;
    }

    public void setContractApprovalList(List<ContractApproval> contractApprovalList) {
        this.contractApprovalList = contractApprovalList;
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
