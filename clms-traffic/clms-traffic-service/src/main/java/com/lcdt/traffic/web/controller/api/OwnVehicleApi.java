package com.lcdt.traffic.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.dto.OwnVehicleDto;
import com.lcdt.traffic.model.OwnVehicle;
import com.lcdt.traffic.service.OwnVehicleService;
import com.lcdt.traffic.dto.BackstageVehicleDto;
import com.lcdt.traffic.web.dto.BaseDto;
import com.lcdt.traffic.web.dto.PageBaseDto;
import com.lcdt.userinfo.model.Driver;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017-12-14
 */
@Api(description = "我的车辆管理api")
@RestController
@RequestMapping("/ownfleet/ownvehicle")
public class OwnVehicleApi {

    Logger logger = LoggerFactory.getLogger(OwnVehicleApi.class);

    @Autowired
    private OwnVehicleService ownVehicleService;

    @ApiOperation(value = "新增车辆", notes = "新增车辆")
    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('ownvehicle_add')")
    public JSONObject addOwnVehicle(@Validated @RequestBody OwnVehicleDto ownVehicleDto, BindingResult bindingResult) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Long userId = SecurityInfoGetter.getUser().getUserId();
        String userName = SecurityInfoGetter.getUser().getRealName();
        ownVehicleDto.setCompanyId(companyId);
        ownVehicleDto.setCreateId(userId);
        ownVehicleDto.setCreateName(userName);
        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()) {
            jsonObject.put("code", -1);
            jsonObject.put("message", bindingResult.getFieldError().getDefaultMessage());
            return jsonObject;
        }
        ownVehicleService.addVehicle(ownVehicleDto);
        jsonObject.put("code", 0);
        jsonObject.put("message", "新增成功");

        return jsonObject;
    }

    @ApiOperation(value = "修改车辆", notes = "更新车辆")
    @PostMapping("/modify")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('ownvehicle_modify')")
    public JSONObject modOwnVehicle(@Validated @RequestBody OwnVehicleDto ownVehicleDto, BindingResult bindingResult) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Long userId = SecurityInfoGetter.getUser().getUserId();
        String userName = SecurityInfoGetter.getUser().getRealName();
        ownVehicleDto.setCompanyId(companyId);
        ownVehicleDto.setUpdateId(userId);
        ownVehicleDto.setUpdateName(userName);
        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()) {
            jsonObject.put("code", -1);
            jsonObject.put("message", bindingResult.getFieldError().getDefaultMessage());
            return jsonObject;
        }
        ownVehicleService.modVehicle(ownVehicleDto);
        jsonObject.put("code", 0);
        jsonObject.put("message", "修改成功");
        return jsonObject;
    }

    @ApiOperation(value = "删除车辆", notes = "删除车辆")
    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('ownvehicle_delete')")
    public JSONObject delOwnVehicle(@RequestBody OwnVehicleDto ownVehicleDto, BindingResult bindingResult) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Long userId = SecurityInfoGetter.getUser().getUserId();
        String userName = SecurityInfoGetter.getUser().getRealName();
        ownVehicleDto.setCompanyId(companyId);
        ownVehicleDto.setUpdateId(userId);
        ownVehicleDto.setUpdateName(userName);
        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()) {
            jsonObject.put("code", -1);
            jsonObject.put("message", bindingResult.getFieldError().getDefaultMessage());
            return jsonObject;
        }
        int row = ownVehicleService.delVehicle(ownVehicleDto);
        if(row>0){
            jsonObject.put("code", 0);
            jsonObject.put("message", "删除成功");
        }else{
            jsonObject.put("code", -1);
            jsonObject.put("message", "该数据已被删除");
        }

        return jsonObject;
    }

    @ApiOperation(value = "车辆列表", notes = "获取车辆")
    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('ownvehicle_list')")
    public PageBaseDto<List<OwnVehicle>> ownVehicleList(OwnVehicleDto ownVehicleDto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        OwnVehicle ownVehicle = new OwnVehicle();
        BeanUtils.copyProperties(ownVehicleDto, ownVehicle);
        ownVehicle.setCompanyId(companyId);

        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(ownVehicleDto.getPageNum());
        pageInfo.setPageSize(ownVehicleDto.getPageSize());
        PageInfo<List<OwnVehicle>> listPageInfo = ownVehicleService.ownVehicleList(ownVehicle, pageInfo);
        logger.debug("车辆总条数：" + listPageInfo.getTotal());
        logger.debug("listPageInfo:" + listPageInfo.toString());
        return new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
    }

    @ApiOperation(value = "车辆详情", notes = "车辆详情包含证件信息")
    @GetMapping("/detail")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('ownvehicle_list')")
    public BaseDto ownVehicleList(Long ownVehicleId) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        OwnVehicle ownVehicle = new OwnVehicle();
        OwnVehicleDto ownVehicleDto = ownVehicleService.ownVehicleDetail(ownVehicleId,companyId);
        return new BaseDto(ownVehicleDto);
    }

    @ApiOperation(value = "获取车辆位置", notes = "根据随车手机号获取车辆(基站定位)")
    @GetMapping("/current_location")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('ownvehicle_list')")
    public PageBaseDto<List<Driver>> getGpstStatus(@RequestParam String[] driverPhoneArr) {
//        String driverPhones = StringUtils.join(driverPhoneArr,",");
        List<String> driverPhoneList = Arrays.asList(driverPhoneArr);
        logger.debug("driverPhones:" + driverPhoneList.size());
        if(driverPhoneList.size()>0){
            List<Driver> driverList = ownVehicleService.getGpsInfo(driverPhoneList);
            return new PageBaseDto(driverList, driverList.size());
        }else {
            return new PageBaseDto(new ArrayList(),0);
        }


    }

    @ApiOperation(value = "公司车辆列表", notes = "大后台查看企业车辆")
    @GetMapping("/listforbackstage")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('ownvehicle_list')")
    public PageBaseDto<List<OwnVehicle>> backstageVehiclelist(BackstageVehicleDto dto) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(dto.getPageNum());
        pageInfo.setPageSize(dto.getPageSize());
        PageInfo<List<OwnVehicle>> listPageInfo = ownVehicleService.companyVehicleList(dto, pageInfo);
        logger.debug("车辆总条数：" + listPageInfo.getTotal());
        logger.debug("listPageInfo:" + listPageInfo.toString());
        return new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
    }

}
