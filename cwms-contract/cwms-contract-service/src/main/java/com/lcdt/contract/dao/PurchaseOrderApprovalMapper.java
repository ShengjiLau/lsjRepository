package com.lcdt.contract.dao;

import com.lcdt.contract.model.PurchaseOrderApproval;

import java.util.List;

public interface PurchaseOrderApprovalMapper {
    int deleteByPrimaryKey(Long poaId);

    int insert(PurchaseOrderApproval record);

    PurchaseOrderApproval selectByPrimaryKey(Long poaId);

    List<PurchaseOrderApproval> selectAll();

    int updateByPrimaryKey(PurchaseOrderApproval record);
}