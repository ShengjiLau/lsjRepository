package com.lcdt.contract.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.contract.dao.ContractMapper;
import com.lcdt.contract.model.Contract;
import com.lcdt.contract.service.ContractService;
import com.lcdt.contract.web.dto.ContractDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2018-02-28
 */
@Service
@Transactional
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractMapper contractMapper;

    @Override
    public int addContract(Contract contract) {
        return 0;
    }

    @Override
    public int modContract(Contract contract) {
        return 0;
    }

    @Override
    public int delContract(Long contractId) {
        return 0;
    }

    @Override
    public PageInfo<List<Contract>> ContractList(ContractDto contractDto, PageInfo pageInfo) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        PageInfo page = new PageInfo(contractMapper.selectByCondition(contractDto));
        return page;
    }
}
