package com.lcdt.contract.dao;

import com.lcdt.contract.model.ApprovalProcess;
import com.lcdt.contract.web.dto.ApprovalProcessDto;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;


public interface ApprovalProcessMapper {
    int deleteByPrimaryKey(Long processId,Long companyId);

    int insert(ApprovalProcess record);

    ApprovalProcess selectByPrimaryKey(Long processId);

    List<ApprovalProcess> selectAll();

    int updateByPrimaryKey(ApprovalProcess record);

    List<ApprovalProcessDto> selectByCondition(Long companyId,Short processType);

    List<ApprovalProcessDto> selectDetail(Long companyId,Long processId);
}