package com.lcdt.driver.wechat.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.clms.security.helper.TokenSecurityInfoGetter;
import com.lcdt.userinfo.model.DriverVehicleAuth;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.rpc.CarAuthService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarAuthApi {

    @Reference
    CarAuthService carAuthService;

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public List<DriverVehicleAuth> authList(Integer pageNo,Integer pageSize){
        User user = TokenSecurityInfoGetter.getUser();
        List<DriverVehicleAuth> driverVehicleAuths = carAuthService.vehicleList(user.getUserId(), pageNo, pageSize);
        return driverVehicleAuths;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public DriverVehicleAuth addAuth(DriverVehicleAuth driverVehicleAuth) {
        DriverVehicleAuth driverVehicleAuth1 = carAuthService.addVehicleAuth(driverVehicleAuth);
        return driverVehicleAuth1;
    }



}
