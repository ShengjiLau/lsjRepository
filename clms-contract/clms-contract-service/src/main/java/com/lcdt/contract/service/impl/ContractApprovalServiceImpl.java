package com.lcdt.contract.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.dao.ContractApprovalMapper;
import com.lcdt.contract.dao.ContractMapper;
import com.lcdt.contract.model.ContractApproval;
import com.lcdt.contract.service.ContractApprovalService;
import com.lcdt.contract.web.dto.ContractApprovalDto;
import com.lcdt.contract.web.dto.ContractApprovalListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private ContractMapper contractMapper;

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
                        cald.setCaId(contractApproval.getCaId());
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

    @Override
    public int agreeApproval(ContractApproval contractApproval) {

        Long companyId = SecurityInfoGetter.getCompanyId();
        contractApproval.setStatus(new Short("2"));     //审批状态
        contractApproval.setTime(new Date());   //审批时间
        /**
         * 同意审批：
         * 1.审批人状态变为已完成
         * 2.下一位审批人状态变为审批中
         *
         * 如果是最后一个审批人
         * 1.则更新合同表审批状态为2 - 已完成
         * 2.审批结束时间随之更新
         */
        int rows = 0;
        try {
            rows = contractApprovalMapper.updateStatus(contractApproval);
            List<ContractApproval> caList = contractApprovalMapper.selectByContractId(contractApproval.getContractId());
            for(int i=0;i<caList.size();i++){
                ContractApproval ca = caList.get(i);
                if(ca.getCaId().equals(contractApproval.getCaId())){    //找出当前正在审核的人
                    if(ca.getSort().longValue()==caList.size()){  //如果正在审核的人为最后一人，则审批流程结束
                        contractMapper.updateApprovalStatus(contractApproval.getContractId(),companyId,new Short("2"));
                        break;
                    }else{  //否则更新下一位审核人状态为审批中
                        contractApproval.setCaId(caList.get(i+1).getCaId());
                        contractApproval.setStatus(new Short("1"));
                        contractApproval.setTime(null);   //置空之前设置的值
                        rows += contractApprovalMapper.updateStatus(contractApproval);
                        break;
                    }
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RuntimeException("操作失败!");
        }
        return rows;
    }

    @Override
    public int rejectApproval(ContractApproval contractApproval) {

        Long companyId = SecurityInfoGetter.getCompanyId();
        contractApproval.setStatus(new Short("2"));     //审批状态
        contractApproval.setTime(new Date());   //审批时间
        /**
         * 驳回的处理逻辑：即审批终止，
         * 1.设置当前人的审批状态为4 已驳回
         * 2.合同主表更新审批状态为4 已驳回
         * 3.设置合同主表审批结束时间
         */
        int rows = 0;
        try {
            rows = contractApprovalMapper.updateStatus(contractApproval);
            contractMapper.updateApprovalStatus(contractApproval.getContractId(),companyId,new Short("4"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RuntimeException("操作失败！");
        }
        return rows;
    }

    @Override
    public int revokeApproval(ContractApproval contractApproval) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        contractApproval.setStatus(new Short("2"));     //审批状态
        contractApproval.setTime(new Date());   //审批时间
        /**
         * 撤销的处理逻辑：即审批终止，
         * 1.设置当前人的审批状态为3 已撤销
         * 2.合同主表更新审批状态为3 已撤销
         * 3.设置合同主表审批结束时间
         */
        int rows = 0;
        try {
            rows = contractApprovalMapper.updateStatus(contractApproval);
            contractMapper.updateApprovalStatus(contractApproval.getContractId(),companyId,new Short("3"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RuntimeException("操作失败！");
        }
        return rows;
    }

    @Override
    public int turnDoApproval(List<ContractApproval> contractApprovalList) {
        ContractApproval contractApproval =  contractApprovalList.get(0);    //第一个为当前审批人
        Long companyId = SecurityInfoGetter.getCompanyId();
        contractApproval.setStatus(new Short("5"));     //审批状态 5 - 转办
        contractApproval.setTime(new Date());   //审批时间
        /**
         * 转办处理逻辑：
         * 1.当前审批人状态置为 5 （转办）
         * 2.新增一条审批人记录，并设置对应sort为当前人的sort，并设置审批状态为审批中
         *
         */
        int rows = 0;
        try {
            rows = contractApprovalMapper.updateStatus(contractApproval);
            ContractApproval contractApproval1 = contractApprovalList.get(1);   //第二个为接收转办的人
            //获取当前人的审批记录
            ContractApproval ca = contractApprovalMapper.selectByPrimaryKey(contractApproval.getCaId());
            contractApproval1.setSort(ca.getSort());    //设置审批顺序
            contractApproval1.setContractId(ca.getContractId());
            contractApproval1.setStatus(new Short("1"));   //设置审批状态为审批中
            contractApproval1.setActionType(new Short("0"));    //为审批类型
            contractApproval1.setTime(new Date());
            contractApprovalMapper.insert(contractApproval1);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RuntimeException("操作失败！");
        }
        return rows;
    }

    @Override
    public int ccApproval(List<ContractApproval> contractApprovalList) {
        ContractApproval contractApproval =  contractApprovalList.get(0);    //第一个携带合同信息，合同主键contractId
        Long companyId = SecurityInfoGetter.getCompanyId();
        Long contractId = contractApproval.getContractId();
        contractApprovalList.remove(0); //移除第一个只携带合同信息的记录，剩余的即为需要抄送的人员记录
        /**
         * 抄送处理逻辑：
         * 1.获取抄送合同的主键信息
         * 2.组织抄送人信息记录并批量更新
         */
        int row = 0;
        try {
            for(ContractApproval ca : contractApprovalList){
                ca.setContractId(contractId);
                ca.setActionType(new Short("1"));
                ca.setTime(new Date());
            }
            row = contractApprovalMapper.insertBatch(contractApprovalList);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RuntimeException("操作失败！");

        }
        return row;
    }



    
}
