package com.lcdt.userinfo.web.controller.api.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.dao.DriverMapper;
import com.lcdt.userinfo.dao.DriverVehicleAuthMapper;
import com.lcdt.userinfo.model.AdminUser;
import com.lcdt.userinfo.model.DriverVehicleAuth;
import com.lcdt.userinfo.utils.JSONResponseUtil;
import com.lcdt.userinfo.utils.ResponseMessage;
import com.lcdt.userinfo.web.controller.api.admin.dto.DriverQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/company")
public class DriverManageApi {

    @Autowired
    DriverMapper driverMapper;

    @Autowired
    DriverVehicleAuthMapper driverVehicleAuthMapper;

    @PostMapping("/list")
    private ResponseMessage list(DriverQueryDto driverQueryDto){
        PageInfo<AdminUser> pageInfo = PageHelper.startPage(driverQueryDto.getPageNo(), driverQueryDto.getPageSize()).doSelectPageInfo(() -> driverVehicleAuthMapper.selectByDriverQueryDto(driverQueryDto));
        return JSONResponseUtil.success(pageInfo);
    }
    @PostMapping("/updateAuth")
    private ResponseMessage updateAuth(Long authId,String remark,String authStatus){
        DriverVehicleAuth driverVehicleAuth = driverVehicleAuthMapper.selectByPrimaryKey(authId);
        driverVehicleAuth.setAuthStatus(authStatus);
        driverVehicleAuth.setAuthRemark(remark);
        driverVehicleAuthMapper.updateByPrimaryKey(driverVehicleAuth);
        return JSONResponseUtil.success(driverVehicleAuth);
    }


}
