package com.lcdt.contract.dao;

import com.lcdt.contract.model.ApprovalProcess;
import java.util.List;

public interface ApprovalProcessMapper {
    int deleteByPrimaryKey(Long processId);

    int insert(ApprovalProcess record);

    ApprovalProcess selectByPrimaryKey(Long processId);

    List<ApprovalProcess> selectAll();

    int updateByPrimaryKey(ApprovalProcess record);
}