package com.lcdt.contract.service;

import com.lcdt.contract.model.Contract;
import com.lcdt.contract.model.ContractApproval;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017/10/11
 */
public interface ContractSerivce {

    /**
     * 新建合同
     * @return
     */
    int addContract();

    /**
     * 查询合同列表
     * @param contract 相关查询条件
     * @return
     */
    List<Contract> queryContract(Contract contract, ContractApproval contractApproval);


}
