package com.lcdt.contract.dto;

import com.lcdt.contract.model.Contract;
import com.lcdt.contract.model.ContractApproval;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017/10/11
 */
public class ContractDto {

    private Contract contract;
    private ContractApproval contractApproval;
    private List<Contract> contractList;
    private List<ContractApproval> contractApprovalList;

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public ContractApproval getContractApproval() {
        return contractApproval;
    }

    public void setContractApproval(ContractApproval contractApproval) {
        this.contractApproval = contractApproval;
    }

    public List<Contract> getContractList() {
        return contractList;
    }

    public void setContractList(List<Contract> contractList) {
        this.contractList = contractList;
    }

    public List<ContractApproval> getContractApprovalList() {
        return contractApprovalList;
    }

    public void setContractApprovalList(List<ContractApproval> contractApprovalList) {
        this.contractApprovalList = contractApprovalList;
    }
}
