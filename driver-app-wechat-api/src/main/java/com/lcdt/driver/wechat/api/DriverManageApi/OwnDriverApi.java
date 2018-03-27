package com.lcdt.driver.wechat.api.DriverManageApi;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.clms.security.helper.TokenSecurityInfoGetter;
import com.lcdt.driver.dto.BaseDto;
import com.lcdt.driver.dto.PageBaseDto;
import com.lcdt.traffic.dto.DriverGroupDto;
import com.lcdt.traffic.dto.OwnDriverDto;
import com.lcdt.traffic.model.OwnDriver;
import com.lcdt.traffic.service.DriverGroupService;
import com.lcdt.traffic.service.OwnDriverService;
import com.lcdt.userinfo.model.Driver;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lyqishan on 2018/3/27
 */
@RestController
@RequestMapping("/api/own/driver")
@Api(value = "司机", description = "我的司机")
public class OwnDriverApi {

    Logger logger = LoggerFactory.getLogger(OwnDriverApi.class);

    @Reference
    private OwnDriverService ownDriverService;

    @Reference
    private DriverGroupService driverGroupService;


    @ApiOperation(value = "司机列表", notes = "获取司机")
    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('owndriver_list')")
    public PageBaseDto<List<OwnDriver>> ownDriverList(OwnDriverDto ownDriverDto) {
        Long companyId = TokenSecurityInfoGetter.getUserCompRel().getCompany().getCompId();//  获取companyId
        OwnDriver ownDriver = new OwnDriver();
        BeanUtils.copyProperties(ownDriverDto, ownDriver);
        ownDriver.setCompanyId(companyId);

        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(ownDriverDto.getPageNum());    //设置页码
        pageInfo.setPageSize(ownDriverDto.getPageSize());  //设置每页条数
        PageInfo<List<OwnDriver>> listPageInfo = ownDriverService.ownDriverList(ownDriver, pageInfo);
        logger.debug("司机总条数：" + listPageInfo.getTotal());
        logger.debug("listPageInfo:" + listPageInfo.toString());
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }

    @ApiOperation(value = "司机详情", notes = "司机详情包含证件信息")
    @GetMapping("/detail")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('owndriver_detail')")
    public BaseDto ownDriverList(Long ownDriverId) {
        Long companyId = TokenSecurityInfoGetter.getUserCompRel().getCompany().getCompId(); //  获取companyId
        OwnDriver ownDriver = new OwnDriver();
        OwnDriverDto ownDriverDto = ownDriverService.ownDriverDetail(ownDriverId, companyId);
        BaseDto baseDto = new BaseDto(ownDriverDto);
        return baseDto;
    }

    @ApiOperation(value = "获取司机位置", notes = "根据随车手机号获取司机(基站定位)")
    @GetMapping("/current_location")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('owndriver_list')")
    public PageBaseDto<List<Driver>> getGpstStatus(@RequestParam String[] driverPhoneArr) {
        List<String> driverPhoneList = Arrays.asList(driverPhoneArr);
        logger.debug("driverPhones:" + driverPhoneList.size());
        List<Driver> driverList = ownDriverService.getGpsInfo(driverPhoneList);
        PageBaseDto pageBaseDto = new PageBaseDto(driverList, driverList.size());
        return pageBaseDto;
    }


    @ApiOperation(value = "获取司机所属分组", notes = "获取司机所属分组")
    @GetMapping("/drivergroup")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('owndriver_group')")
    public PageBaseDto<List<DriverGroupDto>> setGroup() {
        Long companyId = TokenSecurityInfoGetter.getUserCompRel().getCompany().getCompId(); //  获取companyId
        List<DriverGroupDto> driverGroupDtoList = driverGroupService.selectRelationship(companyId);
        PageBaseDto pageBaseDto = new PageBaseDto(driverGroupDtoList, driverGroupDtoList.size());
        return pageBaseDto;
    }
}
