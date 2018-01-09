package com.lcdt.pay.service;

import com.lcdt.pay.model.CompanyServiceCount;
import com.lcdt.pay.model.ServiceProductPackage;

import java.util.List;

public interface CompanyServiceCountService {

    List<CompanyServiceCount> companyServiceCount(Long companyId);



}
