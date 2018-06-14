package com.lcdt.pay.rpc;

import com.lcdt.pay.model.CompanyServiceCount;

import java.util.List;

public interface CompanyServiceCountService {

    CompanyServiceCount reduceCompanyProductCount(Long companyId,String serviceName,Integer reduceNum);

    List<CompanyServiceCount> companyServiceCount(Long companyId);

    CompanyServiceCount reduceCompanyProductCount(Long companyId,String serviceName,Integer reduceNum,String username,String des);

    boolean checkCompanyProductCount(Long companyId, String serviceName, Integer reduceNum);

    /**
     * 获取剩余条数
     * @param companyId
     * @param serviceName
     * @return
     */
    int companyProductCount(Long companyId, String serviceName);

}
