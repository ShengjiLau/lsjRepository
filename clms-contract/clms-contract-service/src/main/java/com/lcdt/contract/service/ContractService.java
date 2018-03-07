package com.lcdt.contract.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.contract.model.Contract;
import com.lcdt.contract.web.dto.ContractDto;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2018-02-28
 */
public interface ContractService {

    /**
     * 新增合同
     * @param contract
     * @return
     */
    int addContract(Contract contract);

    /**
     * 修改合同
     * @param contract
     * @return
     */
    int modContract(Contract contract);

    /**
     * 删除单个合同
     * @param contractId
     * @return
     */
    int delContract(Long contractId);

    /**
     * 获取合同列表
     * @return
     */
    PageInfo<List<Contract>> ContractList(ContractDto contractDto, PageInfo pageInfo);
}
