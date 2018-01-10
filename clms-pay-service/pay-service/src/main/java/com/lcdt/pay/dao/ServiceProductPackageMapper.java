package com.lcdt.pay.dao;

import com.lcdt.pay.model.ServiceProductPackage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ServiceProductPackageMapper {
    int deleteByPrimaryKey(Integer packageId);

    int insert(ServiceProductPackage record);

    ServiceProductPackage selectByPrimaryKey(Integer packageId);

    List<ServiceProductPackage> selectAll();

    int updateByPrimaryKey(ServiceProductPackage record);


    List<ServiceProductPackage> selectBypackageType(@Param("packageType") String packageType);
}