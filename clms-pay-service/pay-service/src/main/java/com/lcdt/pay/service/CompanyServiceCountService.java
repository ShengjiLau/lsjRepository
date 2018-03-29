package com.lcdt.pay.service;

import com.lcdt.pay.model.CompanyServiceCount;
import com.lcdt.pay.model.ServiceProductPackage;

import java.util.List;

public interface CompanyServiceCountService {

    List<CompanyServiceCount> companyServiceCount(Long companyId);

    CompanyServiceCount reduceCompanyProductCount(Long companyId,String serviceName,Integer reduceNum);

    boolean checkCompanyProductCount(Long companyId,String serviceName,Integer reduceNum);

    /**
     * 获取剩余条数
     * @param companyId
     * @param serviceName
     * @return
     */
    int companyProductCount(Long companyId,String serviceName);

}
