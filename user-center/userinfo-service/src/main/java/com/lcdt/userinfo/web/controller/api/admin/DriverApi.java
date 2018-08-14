package com.lcdt.userinfo.web.controller.api.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.dao.DriverMapper;
import com.lcdt.userinfo.dao.DriverVehicleAuthMapper;
import com.lcdt.userinfo.model.AdminUser;
import com.lcdt.userinfo.model.Driver;
import com.lcdt.userinfo.model.DriverVehicleAuth;
import com.lcdt.userinfo.service.DriverService;
import com.lcdt.userinfo.utils.JSONResponseUtil;
import com.lcdt.userinfo.utils.ResponseMessage;
import com.lcdt.userinfo.web.controller.api.admin.dto.DriverQueryDto;
import com.lcdt.userinfo.web.controller.api.admin.dto.VehicleAuthDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/admin/driver")
public class DriverApi {

    @Autowired
    DriverMapper driverMapper;

    @Autowired
    DriverVehicleAuthMapper driverVehicleAuthMapper;

    @Autowired
    DriverService driverService;

    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('admin_cars_select')")
    public ResponseMessage list(DriverQueryDto driverQueryDto){
        PageInfo<AdminUser> pageInfo = PageHelper.startPage(driverQueryDto.getPageNo(), driverQueryDto.getPageSize()).doSelectPageInfo(() -> driverVehicleAuthMapper.selectByDriverQueryDto(driverQueryDto));
        return JSONResponseUtil.success(pageInfo);
    }
    @PostMapping("/updateAuth")
    public ResponseMessage updateAuth(Long authId,String remark,String authStatus){
        DriverVehicleAuth driverVehicleAuth = driverVehicleAuthMapper.selectByPrimaryKey(authId);
        driverVehicleAuth.setAuthStatus(authStatus);
        driverVehicleAuth.setAuthRemark(remark);
        driverVehicleAuthMapper.updateByPrimaryKey(driverVehicleAuth);
        return JSONResponseUtil.success(driverVehicleAuth);
    }


    @PostMapping("/driverlist")
    @ApiOperation("司机列表")
    @PreAuthorize("hasAnyAuthority('admin_driver_select')")
    public ResponseMessage driverList(DriverQueryDto driverQueryDto){
        PageInfo<Driver> pageInfo = PageHelper.startPage(driverQueryDto.getPageNo(), driverQueryDto.getPageSize()).doSelectPageInfo(() -> driverMapper.selectByDriverQueryDto(driverQueryDto));
        return JSONResponseUtil.success(pageInfo);
    }
    @PostMapping("/updateDriverAuth")
    @ApiOperation("司机认证")
    public ResponseMessage updateDriverAuth(Long driverId,String remark,String authStatus){
        Driver driver = driverMapper.selectByPrimaryKey(driverId);
        driver.setAuthStatus(Integer.valueOf(authStatus));
        driver.setAuthRemark(remark);
        driver.setAuthTime(new Date());
        driver.setAuthUser(SecurityInfoGetter.getUser().getRealName());
        driverMapper.updateByPrimaryKey(driver);
        return JSONResponseUtil.success(driver);
    }

    @PostMapping("/drivercatnum")
    @ApiOperation("司机车辆数量")
    @PreAuthorize("hasAnyAuthority('admin_driver_car')")
    public ResponseMessage driverCarNum(Long driverId){
        Integer integer = driverMapper.selectCarnumBydriverId(driverId);
        return JSONResponseUtil.success(integer);
    }

    @PostMapping("/vehicleAuth")
    @ApiOperation("车辆认证")
    public ResponseMessage updateAuth(VehicleAuthDto auth){
        auth.setAuthTime(new Date());
        auth.setAuthName(SecurityInfoGetter.getUser().getRealName());
        int row = driverVehicleAuthMapper.updateAuthStatus(auth);
        return JSONResponseUtil.success(row);
    }

    @PostMapping("/driverDetail")
    @ApiOperation("司机详情")
    @PreAuthorize("hasAnyAuthority('admin_driver_select')")
    public ResponseMessage driverDetails(Long userId){
        Driver driver = driverMapper.selectByPrimaryKey(userId);
        return JSONResponseUtil.success(driver);
    }

    @PostMapping("/addDriver")
    @ApiOperation("司机新增")
    public ResponseMessage addDriver(Driver driver){
        int row = driverService.addDriver(driver);
        if(row>0){
            return JSONResponseUtil.success(row);
        }
        return JSONResponseUtil.failure("新增失败",-1);
    }

    @PostMapping("/modifyDriver")
    @ApiOperation("司机编辑")
    public ResponseMessage modifyDriver(Driver driver){
        int row = driverMapper.updateByPrimaryKey(driver);
        if (row>0){
            return JSONResponseUtil.success(row);
        }
        return JSONResponseUtil.failure("编辑失败",-1);
    }
}
