package com.lcdt.pay.dao;

import com.lcdt.pay.model.PayOrder;

import java.util.List;

public interface PayOrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(PayOrder record);

    PayOrder selectByPrimaryKey(Long orderId);

    List<PayOrder> selectAll();

    int updateByPrimaryKey(PayOrder record);

    List<PayOrder> selectByOrderNo(String orderNo);

}
