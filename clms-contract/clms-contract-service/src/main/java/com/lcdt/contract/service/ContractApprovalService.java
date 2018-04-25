package com.lcdt.contract.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.contract.model.ContractApproval;
import com.lcdt.contract.web.dto.ContractApprovalDto;
import com.lcdt.contract.web.dto.ContractApprovalListDto;
import com.lcdt.contract.web.dto.ContractDto;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2018-03-05
 */
public interface ContractApprovalService {

    /**
     * 获取审批列表
     *
     * @param contractApprovalListDto
     * @return
     */
    PageInfo<List<ContractApprovalDto>> contractApprovalList(ContractApprovalListDto contractApprovalListDto, PageInfo pageInfo);

    /**
     * 获取待审批合同数量
     * @param userId
     * @param companyId
     * @return
     */
    int pendingApprovalNum(Long userId, Long companyId, Short type);

    /**
     * 同意审批
     *
     * @param contractApproval
     * @return
     */
    int agreeApproval(ContractApproval contractApproval);

    /**
     * 驳回审批
     * @param contractApproval
     * @return
     */
    int rejectApproval(ContractApproval contractApproval);

    /**
     * 撤销审批
     * @param contractApproval
     * @return
     */
    int revokeApproval(ContractApproval contractApproval);

    /**
     * 转办
     * @param contractApprovalList
     * @return
     */
    int turnDoApproval(List<ContractApproval> contractApprovalList);

    /**
     * 抄送
     * @param contractApprovalList
     * @return
     */
    int ccApproval(List<ContractApproval> contractApprovalList);

}

