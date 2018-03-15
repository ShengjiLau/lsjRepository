package com.lcdt.contract.web.dto;

import com.lcdt.contract.model.Contract;
import com.lcdt.contract.model.ContractApproval;

import java.util.List;

public class ContractApprovalDto extends Contract{

    private List<ContractApproval> contractApprovalList;

    public List<ContractApproval> getContractApprovalList() {
        return contractApprovalList;
    }

    public void setContractApprovalList(List<ContractApproval> contractApprovalList) {
        this.contractApprovalList = contractApprovalList;
    }
}