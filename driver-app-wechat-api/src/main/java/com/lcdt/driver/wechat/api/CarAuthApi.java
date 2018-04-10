package com.lcdt.driver.wechat.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.clms.security.helper.TokenSecurityInfoGetter;
import com.lcdt.driver.dto.AddDriverVehicleAuthDto;
import com.lcdt.driver.dto.PageBaseDto;
import com.lcdt.userinfo.model.DriverVehicleAuth;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.rpc.CarAuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/car")
public class CarAuthApi {

    @Reference
    CarAuthService carAuthService;

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public PageBaseDto<DriverVehicleAuth> authList(Integer pageNo,Integer pageSize){
        User user = TokenSecurityInfoGetter.getUser();
        List<DriverVehicleAuth> driverVehicleAuths = carAuthService.vehicleList(user.getUserId(), pageNo, pageSize);
        return new PageBaseDto<DriverVehicleAuth>(driverVehicleAuths);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public DriverVehicleAuth addAuth(AddDriverVehicleAuthDto addDriverVehicleAuthDto) {
        User user = TokenSecurityInfoGetter.getUser();
        DriverVehicleAuth driverVehicleAuth = new DriverVehicleAuth();
        BeanUtils.copyProperties(addDriverVehicleAuthDto,driverVehicleAuth);
        driverVehicleAuth.setCreateDate(new Date());
        driverVehicleAuth.setCreateId(user.getUserId());
        driverVehicleAuth.setDriverId(user.getUserId());
        DriverVehicleAuth driverVehicleAuth1 = carAuthService.addVehicleAuth(driverVehicleAuth);
        return driverVehicleAuth1;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public DriverVehicleAuth updateAuth(Long authId,AddDriverVehicleAuthDto addDriverVehicleAuthDto) {
        User user = TokenSecurityInfoGetter.getUser();
        DriverVehicleAuth driverVehicleAuth = carAuthService.selectById(authId);
        BeanUtils.copyProperties(addDriverVehicleAuthDto,driverVehicleAuth);
        driverVehicleAuth.setCreateId(user.getUserId());
        driverVehicleAuth.setUpdateDate(new Date());
        driverVehicleAuth.setUpdateId(user.getUserId());
        driverVehicleAuth.setUpdateName(user.getRealName());
        DriverVehicleAuth driverVehicleAuth2 = carAuthService.updateVehicleAuth(driverVehicleAuth);
        return driverVehicleAuth2;
    }


}
