package com.lcdt.contract.dao;

import com.lcdt.contract.model.OrderApproval;
import java.util.List;

public interface OrderApprovalMapper {
    int deleteByPrimaryKey(Long oaId);

    int insert(OrderApproval record);

    OrderApproval selectByPrimaryKey(Long oaId);

    List<OrderApproval> selectAll();

    int updateByPrimaryKey(OrderApproval record);
}