package com.lcdt.pay.dao;

import com.lcdt.pay.model.CompanyServiceCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyServiceCountMapper {
    int deleteByPrimaryKey(Integer countId);

    int insert(CompanyServiceCount record);

    CompanyServiceCount selectByPrimaryKey(Integer countId);

    List<CompanyServiceCount> selectAll();

    int updateByPrimaryKey(CompanyServiceCount record);

    List<CompanyServiceCount> selectByCompanyId(@Param("companyId") Long companyId, @Param("productName") String productName);

}