package com.lcdt.userinfo.rpc;

import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.User;

/**
 * Created by yangbinq on 2018/1/30.
 */
public interface CompanyRpcService {

    /**
     * 根据企业ID获取企业信息
     * @param companyId
     * @return
     */
    Company findCompanyByCid(Long companyId);


    /***
     * 获取企业创建者
     * @param companyId
     * @return
     */
    User findCompanyCreate(Long companyId);

}
