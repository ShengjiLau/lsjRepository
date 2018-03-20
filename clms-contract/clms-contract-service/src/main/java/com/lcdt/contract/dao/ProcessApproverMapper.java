package com.lcdt.contract.dao;

import com.lcdt.contract.model.ProcessApprover;
import java.util.List;

public interface ProcessApproverMapper {
    int deleteByPrimaryKey(Long approverId);

    int insert(ProcessApprover record);

    ProcessApprover selectByPrimaryKey(Long approverId);

    List<ProcessApprover> selectAll();

    int updateByPrimaryKey(ProcessApprover record);

    /**
     * 批量插入审批人信息
     * @param processApproverList
     * @return
     */
    int insertBatch(List<ProcessApprover> processApproverList);

    /**
     * 根据审批流程id删除审批人信息
     * @param ProcessId
     * @return
     */
    int delByProcessId(Long ProcessId);

}