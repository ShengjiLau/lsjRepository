package com.lcdt.contract.web.dto;

import com.lcdt.contract.model.Contract;
import com.lcdt.contract.model.ContractApproval;

import java.util.List;

public class ContractApprovalDto extends Contract{

    private List<ContractApproval> contractApprovalList;

    private String approvalCreateName;

    public List<ContractApproval> getContractApprovalList() {
        return contractApprovalList;
    }

    public void setContractApprovalList(List<ContractApproval> contractApprovalList) {
        this.contractApprovalList = contractApprovalList;
    }

    public String getApprovalCreateName() {
        return approvalCreateName;
    }

    public void setApprovalCreateName(String approvalCreateName) {
        this.approvalCreateName = approvalCreateName;
    }
}