package com.lcdt.userinfo.service;

import com.lcdt.userinfo.dto.CompanyDto;
import com.lcdt.userinfo.exception.CompanyExistException;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.CompanyMember;

import java.util.List;

/**
 * Created by ybq on 2017/8/15.
 */
public interface CompanyService {

    /**
     * 创建企业
     * @param dto
     * @return
     * @throws CompanyExistException
     */
     Company createWmsCompany(CompanyDto dto) throws CompanyExistException;

    /***
     * 加入企业
     * @param dto
     * @return
     * @throws CompanyExistException
     */
    CompanyMember joinCompany(CompanyDto dto) throws CompanyExistException;

    /***
     * 获取企业列表
     * @param dto
     * @return
     */
    List<CompanyMember> wmsCompayList(CompanyDto dto);



}
