package com.lcdt.userinfo.rpc;

import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.Group;
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
     * @param compId
     * @return
     */
    User findCompanyCreate(Long compId);



    /***
     * 根据电话查询对应企业用户
     * @param phone
     * @return
     */
    User findUserByPhone(String phone);


    /***
     * 根据用户ID获取用户信息
     * @param userId
     * @return
     */
    User selectByPrimaryKey(Long userId);


    Group selectGroupById(Long groupId);

}
