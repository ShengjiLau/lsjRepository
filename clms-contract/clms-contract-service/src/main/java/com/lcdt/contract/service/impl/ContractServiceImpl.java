package com.lcdt.contract.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.contract.dao.ContractMapper;
import com.lcdt.contract.model.Contract;
import com.lcdt.contract.web.dto.ContractDto;
import com.lcdt.contract.service.ContractService;
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
    public int addContract(ContractDto dto) {
//        int result = contractMapper.insert(contract);
//        if(null!=c() && ownDriverDto.getOwnDriverCertificateList().size()>0) {
//            //设置证件的创建人/id创建人/公司id
//            for (OwnDriverCertificate ownDriverCertificate : ownDriverDto.getOwnDriverCertificateList()) {
//                ownDriverCertificate.setOwnDriverId(ownDriver.getOwnDriverId());
//                ownDriverCertificate.setCreateId(ownDriverDto.getCreateId());
//                ownDriverCertificate.setCreateName(ownDriverDto.getCreateName());
//                ownDriverCertificate.setCompanyId(ownDriverDto.getCompanyId());
//            }
//            ownDriverCertificateMapper.insertBatch(ownDriverDto.getOwnDriverCertificateList());  //批量插入车辆证件
//        }
//        return result;
        return 0;
    }

    @Override
    public int modContract(ContractDto contract) {
//        int result = contractMapper.updateByPrimaryKey(contract);
//        return result;
        return 0;
    }

    @Override
    public int delContract(Long contractId) {
        return 0;
    }

    @Override
    public PageInfo<List<Contract>> ontractList(ContractDto contractDto, PageInfo pageInfo) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        PageInfo page = new PageInfo(contractMapper.selectByCondition(contractDto));
        return page;
    }

    @Override
    public int modContractStatus(Contract contract) {
        int result = contractMapper.updateContractStatus(contract);
        return result;
    }
}
