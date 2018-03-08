package com.lcdt.contract.dao;

import com.lcdt.contract.model.ProcessApprover;
import java.util.List;

public interface ProcessApproverMapper {
    int deleteByPrimaryKey(Long approverId);

    int insert(ProcessApprover record);

    ProcessApprover selectByPrimaryKey(Long approverId);

    List<ProcessApprover> selectAll();

    int updateByPrimaryKey(ProcessApprover record);
}