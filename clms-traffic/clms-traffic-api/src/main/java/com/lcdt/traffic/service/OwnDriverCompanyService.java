package com.lcdt.traffic.service;

import com.lcdt.traffic.model.OwnDriver;

import java.util.List;

public interface OwnDriverCompanyService {

    List<OwnDriver> driverCompanys(Long driverId,Integer pageNo,Integer pageSize);

    void removeDriverCompany(Long ownDriverId,Long companyId);

}
