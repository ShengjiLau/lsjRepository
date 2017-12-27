package com.lcdt.pay.service;

import com.lcdt.pay.model.CompanyServiceCount;

public interface CompanyServiceCountService {

    CompanyServiceCount companyServiceCount(Long companyId);

    CompanyServiceCount addCount(Long companyId,Integer num);



}
