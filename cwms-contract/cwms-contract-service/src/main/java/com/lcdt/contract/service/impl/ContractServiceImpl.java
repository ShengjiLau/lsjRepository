package com.lcdt.contract.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.contract.dao.ContractApprovalMapper;
import com.lcdt.contract.dao.ContractMapper;
import com.lcdt.contract.model.Contract;
import com.lcdt.contract.model.ContractApproval;
import com.lcdt.contract.service.ContractSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017/10/11
 */

@Transactional //整个类方法都开启事务管理
@Service
public class ContractServiceImpl implements ContractSerivce{

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private ContractApprovalMapper contractApprovalMapper;

    @Override
    public int addContract(Contract contract,ContractApproval contractApproval) {
        /* 1.合同主表基础信息保存 2.基础信息保存成功保存审批信息 */
        try {
            //合同表信息保存
            contractMapper.insert(contract);
            //审批信息保存
            contractApprovalMapper.insert(contractApproval);
            return 1;
        } catch (Exception e) {
            return 0;
        } finally {
            throw new RuntimeException();
        }
    }


    @Override
    public List<Contract> queryContract(Contract contract, ContractApproval contractApproval) {
        return null;
    }
}
