package com.lcdt.driver.wechat.api.DriverManageApi;

import com.lcdt.clms.security.helper.TokenSecurityInfoGetter;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.UserCompRel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/company/")
public class CompanyApi {

    @RequestMapping(value = "/get",method = RequestMethod.POST)
    public Company companyInfo(){
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        return userCompRel.getCompany();
    }

}
