package com.lcdt.pay.dao;

import com.lcdt.pay.model.ServiceProduct;

import java.util.List;

public interface ServiceProductMapper {
    int deleteByPrimaryKey(Integer productId);

    int insert(ServiceProduct record);

    ServiceProduct selectByPrimaryKey(Integer productId);

    List<ServiceProduct> selectAll();

    int updateByPrimaryKey(ServiceProduct record);

    List<ServiceProduct> selectProductByName(String productName);

}