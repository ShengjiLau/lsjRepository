package com.lcdt.driver.wechat.api.DriverManageApi;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.clms.security.helper.TokenSecurityInfoGetter;
import com.lcdt.driver.dto.BaseDto;
import com.lcdt.driver.dto.PageBaseDto;
import com.lcdt.traffic.dto.OwnVehicleDto;
import com.lcdt.traffic.model.OwnVehicle;
import com.lcdt.traffic.service.OwnVehicleService;
import com.lcdt.userinfo.model.Driver;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lyqishan on 2018/3/27
 */
@RestController
@RequestMapping("/api/own/vehicle")
@Api(value = "车辆", description = "我的车辆")
public class OwnVehicleApi {

    Logger logger = LoggerFactory.getLogger(OwnVehicleApi.class);

    @Reference
    private OwnVehicleService ownVehicleService;


    @ApiOperation(value = "车辆列表", notes = "获取车辆")
    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('ownvehicle_list')")
    public PageBaseDto<List<OwnVehicle>> ownVehicleList(OwnVehicleDto ownVehicleDto) {
        Long companyId = TokenSecurityInfoGetter.getUserCompRel().getCompany().getCompId(); //  获取companyId
        OwnVehicle ownVehicle = new OwnVehicle();
        BeanUtils.copyProperties(ownVehicleDto, ownVehicle);
        ownVehicle.setCompanyId(companyId);

        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(ownVehicleDto.getPageNum());    //设置页码
        pageInfo.setPageSize(ownVehicleDto.getPageSize());  //设置每页条数
        PageInfo<List<OwnVehicle>> listPageInfo = ownVehicleService.ownVehicleList(ownVehicle, pageInfo);
        logger.debug("车辆总条数：" + listPageInfo.getTotal());
        logger.debug("listPageInfo:" + listPageInfo.toString());
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }

    @ApiOperation(value = "车辆详情", notes = "车辆详情包含证件信息")
    @GetMapping("/detail")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('ownvehicle_list')")
    public BaseDto ownVehicleList(Long ownVehicleId) {
        Long companyId = TokenSecurityInfoGetter.getUserCompRel().getCompany().getCompId(); //  获取companyId
        OwnVehicle ownVehicle = new OwnVehicle();
        OwnVehicleDto ownVehicleDto = ownVehicleService.ownVehicleDetail(ownVehicleId,companyId);
        BaseDto baseDto = new BaseDto(ownVehicleDto);
        return baseDto;
    }

    @ApiOperation(value = "获取车辆位置", notes = "根据随车手机号获取车辆(基站定位)")
    @GetMapping("/current_location")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('ownvehicle_list')")
    public PageBaseDto<List<Driver>> getGpstStatus(@RequestParam String[] driverPhoneArr) {
        List<String> driverPhoneList = Arrays.asList(driverPhoneArr);
        logger.debug("driverPhones:" + driverPhoneList.size());
        if(driverPhoneList.size()>0){
            List<Driver> driverList = ownVehicleService.getGpsInfo(driverPhoneList);
            PageBaseDto pageBaseDto = new PageBaseDto(driverList, driverList.size());
            return pageBaseDto;
        }else {
            return new PageBaseDto(new ArrayList(),0);
        }


    }

}
