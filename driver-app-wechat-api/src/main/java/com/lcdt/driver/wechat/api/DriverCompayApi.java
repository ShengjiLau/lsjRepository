package com.lcdt.driver.wechat.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.security.helper.TokenSecurityInfoGetter;
import com.lcdt.traffic.model.OwnDriver;
import com.lcdt.traffic.service.OwnDriverCompanyService;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.service.CompanyService;
import com.lcdt.userinfo.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class DriverCompayApi {

    @Reference
    OwnDriverCompanyService ownDriverCompanyService;

    @Reference
    CompanyService companyService;

    @RequestMapping(value = "/remove",method = RequestMethod.POST)
    public String removeDriverCompany(Long ownerDriverId,Long companyId){
        User user = TokenSecurityInfoGetter.getUser();
        ownDriverCompanyService.removeDriverCompany(ownerDriverId,companyId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("message", "操作成功");

        return jsonObject.toString();
    }

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public List<OwnDriver> driverCompanyList(Integer pageNo,Integer pageSize){
        User user = TokenSecurityInfoGetter.getUser();
        List<OwnDriver> ownDrivers = ownDriverCompanyService.driverCompanys(user.getUserId(), pageNo, pageSize);
        for (OwnDriver ownDriver : ownDrivers) {
            companyService.selectById(ownDriver.getCompanyId());
        }
        return ownDrivers;
    }

}
