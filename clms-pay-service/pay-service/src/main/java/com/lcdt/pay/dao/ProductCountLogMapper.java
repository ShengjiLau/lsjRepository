package com.lcdt.pay.dao;

import com.lcdt.pay.rpc.ProductCountLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

import java.util.List;

public interface ProductCountLogMapper {
    int deleteByPrimaryKey(Long serviceCountLogId);

    int insert(ProductCountLog record);

    ProductCountLog selectByPrimaryKey(Long serviceCountLogId);

    List<ProductCountLog> selectAll();

    int updateByPrimaryKey(ProductCountLog record);

    List<ProductCountLog> selectByProductNameCompanyId(@Param("companyId") Long companyId,@Param("productName") String productName,@Param("beginTime") Date beginTime,@Param("endTime") Date endTime
    ,@Param("logType") Integer logType);

}