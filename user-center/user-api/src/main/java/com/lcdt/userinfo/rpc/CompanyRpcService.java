package com.lcdt.userinfo.rpc;

import com.lcdt.userinfo.model.Company;

/**
 * Created by yangbinq on 2018/1/30.
 */
public interface CompanyRpcService {


    Company findCompanyByCid(Long companyId);

}
