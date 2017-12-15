package com.lcdt.traffic.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.dto.OwnVehicleDto;
import com.lcdt.traffic.service.OwnVehicleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @AUTHOR liuh
 * @DATE 2017-12-14
 */
@Api(description = "我的车辆管理api")
@RestController
@RequestMapping("/ownfleet/ownvehicle/")
public class OwnVehicleApi {

    Logger logger = LoggerFactory.getLogger(OwnVehicleApi.class);

    @Autowired
    private OwnVehicleService ownVehicleService;

    @ApiOperation(value = "新增车辆", notes = "新增车辆")
    @PostMapping("/add")
    public JSONObject addOwnVehicle(@Validated OwnVehicleDto ownVehicleDto, BindingResult bindingResult) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名
        ownVehicleDto.setCompnayId(companyId);
        ownVehicleDto.setCreateId(userId);
        ownVehicleDto.setCreateName(userName);
        JSONObject jsonObject = new JSONObject();

        ownVehicleService.addVehicle(ownVehicleDto);
        return null;
    }

}
