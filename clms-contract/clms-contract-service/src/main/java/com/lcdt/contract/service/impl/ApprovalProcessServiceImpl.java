package com.lcdt.contract.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.contract.dao.ApprovalProcessMapper;
import com.lcdt.contract.dao.ProcessApproverMapper;
import com.lcdt.contract.model.ApprovalProcess;
import com.lcdt.contract.model.ProcessApprover;
import com.lcdt.contract.service.ApprovalProcessService;
import com.lcdt.contract.web.dto.ApprovalProcessDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2018-03-08
 */
@Service
@Transactional
public class ApprovalProcessServiceImpl implements ApprovalProcessService {

    @Autowired
    private ApprovalProcessMapper approvalProcessMapper;
    @Autowired
    private ProcessApproverMapper processApproverMapper;


    @Override
    public PageInfo<List<ApprovalProcessDto>> approvalProcessList(ApprovalProcessDto approvalProcessDto, PageInfo pageInfo) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        PageInfo page = new PageInfo(approvalProcessMapper.selectByCondition(approvalProcessDto.getCompanyId(), approvalProcessDto.getProcessType()));
        return page;
    }

    @Override
    public int addApprovalProcess(ApprovalProcessDto approvalProcessDto) {
        /*分为两部：1.先保存审批主表信息 2.批量保存审批人和抄送人信息*/
        ApprovalProcess approvalProcess = new ApprovalProcess();
        BeanUtils.copyProperties(approvalProcessDto, approvalProcess);   //拷贝审批流程主表属性
        int row = 0;
        try {
            row = approvalProcessMapper.insert(approvalProcess);
            if (row > 0 && null != approvalProcess.getProcessId()) {    // 判断主表是否插入成功并返回主键id
                if (null != approvalProcessDto.getProcessApproverList() && approvalProcessDto.getProcessApproverList().size() > 0) {
                    for (ProcessApprover processApprover : approvalProcessDto.getProcessApproverList()) {
                        processApprover.setProcessId(approvalProcess.getProcessId());   //循环设置关联的主表的主键processId
                    }
                    row += processApproverMapper.insertBatch(approvalProcessDto.getProcessApproverList());  //批量插入审批人信息
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("保存失败！");
        }
        return row;
    }

    @Override
    public int modApprovalProcess(ApprovalProcessDto approvalProcessDto) {
        /*分为三步部：1.先更新审批主表信息 2.删除之前保留的审批人信息 3.批量保存审批人和抄送人信息*/
        ApprovalProcess approvalProcess = new ApprovalProcess();
        BeanUtils.copyProperties(approvalProcessDto, approvalProcess);   //拷贝审批流程主表属性
        int row = 0;
        try {
            row = approvalProcessMapper.updateByPrimaryKey(approvalProcess);
            if (row > 0 && null != approvalProcess.getProcessId()) {    // 判断主表更新是否成功
                if (null != approvalProcessDto.getProcessApproverList() && approvalProcessDto.getProcessApproverList().size() > 0) {
                    //删除审批人
                    processApproverMapper.delByProcessId(approvalProcess.getProcessId());
                    for (ProcessApprover processApprover : approvalProcessDto.getProcessApproverList()) {
                        processApprover.setProcessId(approvalProcess.getProcessId());   //循环设置关联的主表的主键processId
                    }
                    row += processApproverMapper.insertBatch(approvalProcessDto.getProcessApproverList());  //批量插入审批人信息
                }
            }else {
                throw new RuntimeException("修改失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败！");
        }
        return row;
    }

    @Override
    public int delApprovalProcess(Long processId,Long companyId) {
        int row = 0;
        try {
            // 先删除审批人信息，在删除审批流程主表
            row = processApproverMapper.delByProcessId(processId);
            if(row > 0){
                row += approvalProcessMapper.deleteByPrimaryKey(processId,companyId);
            }else{
                throw new RuntimeException("删除失败!");
            }
            if(row <= 0){
                throw new RuntimeException("删除失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("删除失败!");
        }
        return row;
    }


    @Override
    public List<ApprovalProcessDto> approvalProcessShow(ApprovalProcess approvalProcess) {
        return approvalProcessMapper.selectByCondition(approvalProcess.getCompanyId(), approvalProcess.getProcessType());
    }

    @Override
    public List<ApprovalProcessDto> approvalProcessDetail(ApprovalProcess approvalProcess){
        return  approvalProcessMapper.selectDetail(approvalProcess.getCompanyId(),approvalProcess.getProcessId());
    }
}
