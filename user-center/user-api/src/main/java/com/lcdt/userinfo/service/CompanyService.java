package com.lcdt.userinfo.service;

import com.lcdt.userinfo.dto.WmsCompanyDto;
import com.lcdt.userinfo.model.CompanyMember;
import com.lcdt.userinfo.model.WmsCompany;

import java.util.List;

/**
 * Created by ybq on 2017/8/15.
 */
public interface CompanyService {

    WmsCompany createWmsCompany(WmsCompanyDto dto);

    List<WmsCompany> wmsCompayList(WmsCompanyDto dto);

    CompanyMember  joinCompany(WmsCompanyDto dto);

}
