package com.lcdt.pay.service.impl;

import com.lcdt.pay.dao.CompanyServiceCountMapper;
import com.lcdt.pay.model.CompanyServiceCount;
import com.lcdt.pay.model.ServiceProductPackage;
import com.lcdt.pay.service.CompanyServiceCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CompanyServiceCountImpl implements CompanyServiceCountService {

    @Autowired
    CompanyServiceCountMapper countMapper;

    @Override
    public List<CompanyServiceCount> companyServiceCount(Long companyId) {
        List<CompanyServiceCount> companyServiceCounts = countMapper.selectByCompanyId(companyId, null);
        return companyServiceCounts;
    }


}
