package com.lcdt.contract.dao;

import com.lcdt.contract.model.OrderProduct;
import java.util.List;

public interface OrderProductMapper {
    int deleteByPrimaryKey(Long opId);

    int insert(OrderProduct record);

    OrderProduct selectByPrimaryKey(Long opId);

    List<OrderProduct> selectAll();

    int updateByPrimaryKey(OrderProduct record);
}