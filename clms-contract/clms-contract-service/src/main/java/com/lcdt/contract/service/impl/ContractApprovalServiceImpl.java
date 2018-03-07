package com.lcdt.contract.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.contract.dao.ContractApprovalMapper;
import com.lcdt.contract.model.ContractDto;
import com.lcdt.contract.service.ContractApprovalService;
import com.lcdt.contract.web.dto.ContractApprovalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2018-03-05
 */
@Service
@Transactional
public class ContractApprovalServiceImpl implements ContractApprovalService {

    @Autowired
    private ContractApprovalMapper contractApprovalMapper;

    @Override
    public PageInfo<List<ContractApprovalDto>> contractApprovalList(ContractDto contractDto, PageInfo pageInfo) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        PageInfo page = new PageInfo(contractApprovalMapper.selectContractApprovalByCondition(contractDto));
        return page;
    }
}
