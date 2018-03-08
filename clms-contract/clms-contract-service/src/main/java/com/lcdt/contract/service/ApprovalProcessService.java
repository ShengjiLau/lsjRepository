package com.lcdt.contract.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.contract.web.dto.ApprovalProcessDto;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2018-03-08
 */
public interface ApprovalProcessService {

    /**
     * 获取审批流程列表
     * @param approvalProcessDto
     * @param pageInfo
     * @return
     */
    PageInfo<List<ApprovalProcessDto>> approvalProcessList(ApprovalProcessDto approvalProcessDto,PageInfo pageInfo);

    /**
     * 新增审批流程
     * @param approvalProcessDto
     * @return
     */
    int addApprovalProcess(ApprovalProcessDto approvalProcessDto);

    /**
     * 编辑修改审批流程
     * @param approvalProcessDto
     * @return
     */
    int modApprovalProcess(ApprovalProcessDto approvalProcessDto);

    /**
     * 删除审批流程
     * @param approvalProcessDto
     * @return
     */
    int delApprovalProcess(ApprovalProcessDto approvalProcessDto);
}
