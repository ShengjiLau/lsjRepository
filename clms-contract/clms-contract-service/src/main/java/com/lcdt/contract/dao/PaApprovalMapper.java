package com.lcdt.contract.dao;

import com.lcdt.contract.model.PaApproval;
import com.lcdt.contract.web.dto.PaApprovalDto;
import com.lcdt.contract.web.dto.PaApprovalListDto;

import java.util.List;

public interface PaApprovalMapper {
    int deleteByPrimaryKey(Long paaId);

    int insert(PaApproval record);

    int insertSelective(PaApproval record);

    PaApproval selectByPrimaryKey(Long paaId);

    int updateByPrimaryKeySelective(PaApproval record);

    int updateByPrimaryKey(PaApproval record);

    int insertBatch(List<PaApproval> paApprovalList);

    List<PaApprovalDto> selectPaApprovalByCondition(PaApprovalListDto paApprovalListDto);

    int selectPendingNum(Long userId, Long companyId);

    /**
     * 更新审批人状态
     * @param paApproval
     * @return
     */
    int updateStatus(PaApproval paApproval);

    List<PaApproval> selectByPaId(Long paId);

    /**
     * 查询合同抄送的人
     * @param paId
     * @param paApprovalList
     * @return
     */
    List<PaApproval> selectCC(Long paId, List<PaApproval> paApprovalList);
}