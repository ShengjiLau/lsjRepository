package com.lcdt.pay.service.impl;

import com.lcdt.pay.dao.PayBalanceMapper;
import com.lcdt.pay.dao.ServiceProductMapper;
import com.lcdt.pay.dao.ServiceProductPackageMapper;
import com.lcdt.pay.model.PayBalance;
import com.lcdt.pay.model.ServiceProduct;
import com.lcdt.pay.model.ServiceProductPackage;
import com.lcdt.pay.service.CompanyBalanceService;
import com.lcdt.pay.service.ServiceProductPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@com.alibaba.dubbo.config.annotation.Service
public class ServiceProductPackageServiceImpl implements ServiceProductPackageService {

    @Autowired
    ServiceProductPackageMapper mapper;

    @Autowired
    ServiceProductMapper productMapper;

    @Autowired
    ServiceProductPackageMapper productPackageMapper;

    @Autowired
    CompanyBalanceService balanceService;
    @Autowired
    PayBalanceMapper balanceMapper;

    @Override
    public ServiceProductPackage createServiceProductPackage(ServiceProductPackage servicePackage) {
        Integer productId = servicePackage.getProductId();
        ServiceProduct serviceProduct = productMapper.selectByPrimaryKey(productId);
        if (serviceProduct == null) {
            throw new RuntimeException("服务产品不存在");
        }
        mapper.insert(servicePackage);
        return servicePackage;
    }


    @Transactional
    public List<ServiceProductPackage> serviceProductPackageList(String packageType){
        List<ServiceProductPackage> serviceProductPackages = mapper.selectBypackageType(packageType);
        return serviceProductPackages;
    }





}
