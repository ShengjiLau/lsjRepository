package com.lcdt.contract.dao;

import com.lcdt.contract.model.OrderProductRelationship;

import java.util.List;

public interface OrderProductRelationshipMapper {
    int deleteByPrimaryKey(Long relationshipId);

    int insert(OrderProductRelationship record);

    OrderProductRelationship selectByPrimaryKey(Long relationshipId);

    List<OrderProductRelationship> selectAll();

    int updateByPrimaryKey(OrderProductRelationship record);

    OrderProductRelationshipDao selectByOpId(Long opId);
}