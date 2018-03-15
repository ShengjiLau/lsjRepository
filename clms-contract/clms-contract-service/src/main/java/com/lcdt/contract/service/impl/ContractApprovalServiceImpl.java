package com.lcdt.contract.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.contract.dao.ContractApprovalMapper;
import com.lcdt.contract.model.ContractApproval;
import com.lcdt.contract.service.ContractApprovalService;
import com.lcdt.contract.web.dto.ContractApprovalDto;
import com.lcdt.contract.web.dto.ContractApprovalListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public PageInfo<List<ContractApprovalDto>> contractApprovalList(ContractApprovalListDto contractApprovalListDto, PageInfo pageInfo) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        List<ContractApprovalDto> contractApprovalDtoList = contractApprovalMapper.selectContractApprovalByCondition(contractApprovalListDto);
        List<ContractApprovalListDto> contractApprovalListDtoList = new ArrayList<>();
        /**
         * 整合数据，只保留一条审批人的信息，整合逻辑如下
         * 找出审批人当中审批状态为
         *
         * */
        for(ContractApprovalDto cad : contractApprovalDtoList){
            ContractApprovalListDto cald = new ContractApprovalListDto();
            cald.setType(cad.getType());
            cald.setTitle(cad.getTitle());
            cald.setContractCode(cad.getContractCode());
            cald.setApprovalStartDate(cad.getApprovalStartDate());
            for(ContractApproval contractApproval : cad.getContractApprovalList()){
                if(contractApproval.getSort()==0){  //发起人及创建人
                    cald.setCreateUserName(contractApproval.getUserName());
                }
                /**审批状态为 1 3 4 代表审批流程正在执行的状态或者已经结束（撤销/驳回），可作为列表审批关键环节的展示*/
                if(null!= contractApproval.getStatus()){
                    if(contractApproval.getStatus()==1 || contractApproval.getStatus() ==4){
                        cald.setApprovalStatus(contractApproval.getStatus());   //设置当前审批状态
                        cald.setCurrentUserName(contractApproval.getUserName());    //设置当前审批人
                    }else if(contractApproval.getStatus() == 3){    //撤销无需设置当前人员，只设置审批状态
                        cald.setApprovalStatus(contractApproval.getStatus());
                    }else if(cad.getApprovalStatus()==2){   //审批流程完成，无需再设置当前人
                        cald.setApprovalStatus(new Short("2"));
                    }
                }
            }
            contractApprovalListDtoList.add(cald);
        }
        PageInfo page = new PageInfo(contractApprovalListDtoList);
        return page;
    }
}
