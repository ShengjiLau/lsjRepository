package com.lcdt.contract.dao;

import com.lcdt.contract.model.Order;
import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(Order record);

    Order selectByPrimaryKey(Long orderId);

    List<Order> selectAll();

    int updateByPrimaryKey(Order record);
}