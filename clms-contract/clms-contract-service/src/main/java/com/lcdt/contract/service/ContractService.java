package com.lcdt.contract.service;


import com.github.pagehelper.PageInfo;
import com.lcdt.contract.model.Contract;
import com.lcdt.contract.model.ContractLog;
import com.lcdt.contract.web.dto.ContractDto;

import java.util.List;
import java.util.Map;

/**
 * @AUTHOR liuh
 * @DATE 2018-02-28
 */
public interface ContractService {
    /**
     * 新增合同
     * @param dto
     * @return
     */
    int addContract(ContractDto dto);

    /**
     * 修改合同
     * @param dto
     * @return
     */
    int modContract(ContractDto dto);

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
    PageInfo<List<Contract>> ontractList(ContractDto contractDto, PageInfo pageInfo);

    /**
     * 终止合同
     * @param contract
     * @return
     */
    int modContractStatus(Contract contract);

    /**
     * 合同详情
     * @param contractId
     * @return
     */
    ContractDto selectByPrimaryKey(Long contractId);
    /**
     * 获取单个合同日志列表
     * @return
     */
    PageInfo<List<ContractLog>> contractLogList(Map map);

    /**
     * 上传附件
     * @param contract
     * @return
     */
    public boolean uploadAttachment(ContractDto contract);
}