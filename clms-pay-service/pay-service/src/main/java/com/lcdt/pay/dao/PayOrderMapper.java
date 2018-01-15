package com.lcdt.pay.dao;

import com.lcdt.pay.model.PayOrder;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface PayOrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(PayOrder record);

    PayOrder selectByPrimaryKey(Long orderId);

    List<PayOrder> selectAll();

    int updateByPrimaryKey(PayOrder record);

    List<PayOrder> selectByOrderNo(String orderNo);

    List<PayOrder> selectByCompanyId(@Param("companyId") Long companyId, @Param("orderType") Integer orderType, @Param("beginTime") Date beginTime,@Param("endTime") Date endTime,@Param("payType") Integer payType);

    List<PayOrder> selectPayOrderByProductName(@Param("companyId") Long companyId, @Param("productName") String productName,@Param("beginTime") Date beginTime,@Param("endTime") Date endTime);
}
