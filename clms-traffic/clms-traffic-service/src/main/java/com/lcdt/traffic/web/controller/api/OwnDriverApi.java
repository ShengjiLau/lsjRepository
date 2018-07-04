package com.lcdt.traffic.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.dto.DriverGroupDto;
import com.lcdt.traffic.dto.OwnDriverDto;
import com.lcdt.traffic.model.DriverGroupRelationship;
import com.lcdt.traffic.model.OwnDriver;
import com.lcdt.traffic.model.OwnDriverDao;
import com.lcdt.traffic.service.DriverGroupService;
import com.lcdt.traffic.service.OwnDriverService;
import com.lcdt.traffic.web.dto.*;
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
@Api(description = "我的司机管理api")
@RestController
@RequestMapping("/ownfleet/owndriver")
public class OwnDriverApi {

    Logger logger = LoggerFactory.getLogger(OwnDriverApi.class);

    @Autowired
    private OwnDriverService ownDriverService;

    @Autowired
    private DriverGroupService driverGroupService;

    @ApiOperation(value = "新增司机", notes = "新增司机")
    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('owndriver_add')")
    public JSONObject addOwnDriver(@Validated @RequestBody OwnDriverDto ownDriverDto, BindingResult bindingResult) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名
        ownDriverDto.setCompanyId(companyId);
        ownDriverDto.setCreateId(userId);
        ownDriverDto.setCreateName(userName);
        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()) {
            jsonObject.put("code", -1);
            jsonObject.put("message", bindingResult.getFieldError().getDefaultMessage());
            return jsonObject;
        }
        ownDriverService.addDriver(ownDriverDto);
        jsonObject.put("code", 0);
        jsonObject.put("message", "新增成功");

        return jsonObject;
    }

    @ApiOperation(value = "修改司机", notes = "更新司机")
    @PostMapping("/modify")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('owndriver_modify')")
    public JSONObject modOwnDriver(@Validated @RequestBody OwnDriverDto ownDriverDto, BindingResult bindingResult) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名
        ownDriverDto.setCompanyId(companyId);
        ownDriverDto.setUpdateId(userId);
        ownDriverDto.setUpdateName(userName);
        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()) {
            jsonObject.put("code", -1);
            jsonObject.put("message", bindingResult.getFieldError().getDefaultMessage());
            return jsonObject;
        }
        ownDriverService.modDriver(ownDriverDto);
        jsonObject.put("code", 0);
        jsonObject.put("message", "修改成功");
        return jsonObject;
    }

    @ApiOperation(value = "删除司机", notes = "删除司机")
    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('owndriver_delete')")
    public JSONObject delOwnDriver(@RequestBody OwnDriverDto ownDriverDto, BindingResult bindingResult) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名
        ownDriverDto.setCompanyId(companyId);
        ownDriverDto.setUpdateId(userId);
        ownDriverDto.setUpdateName(userName);
        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()) {
            jsonObject.put("code", -1);
            jsonObject.put("message", bindingResult.getFieldError().getDefaultMessage());
            return jsonObject;
        }
        int row = ownDriverService.delDriver(ownDriverDto);
        if(row>0){
            jsonObject.put("code", 0);
            jsonObject.put("message", "删除成功");
        }else{
            jsonObject.put("code", -1);
            jsonObject.put("message", "该数据已被删除");
        }
        return jsonObject;
    }

    @ApiOperation(value = "司机列表", notes = "获取司机")
    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('owndriver_list')")
    public PageBaseDto<List<OwnDriver>> ownDriverList(OwnDriverDto ownDriverDto) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
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
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        OwnDriver ownDriver = new OwnDriver();
//        BeanUtils.copyProperties(ownDriverDto, ownDriver);
        OwnDriverDto ownDriverDto = ownDriverService.ownDriverDetail(ownDriverId, companyId);
        BaseDto baseDto = new BaseDto(ownDriverDto);
        return baseDto;
    }

    @ApiOperation(value = "获取司机位置", notes = "根据随车手机号获取司机(基站定位)")
    @GetMapping("/current_location")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('owndriver_list')")
    public PageBaseDto<List<Driver>> getGpstStatus(@RequestParam String[] driverPhoneArr) {
//        String driverPhones = StringUtils.join(driverPhoneArr,",");
        List<String> driverPhoneList = Arrays.asList(driverPhoneArr);
        logger.debug("driverPhones:" + driverPhoneList.size());
        List<Driver> driverList = ownDriverService.getGpsInfo(driverPhoneList);
        PageBaseDto pageBaseDto = new PageBaseDto(driverList, driverList.size());
        return pageBaseDto;
    }

    @ApiOperation(value = "司机分组管理", notes = "设置司机所属的分组")
    @PostMapping("/groupset")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('owndriver_group')")
    public JSONObject setGroup(@Validated @RequestBody OwnDriverGroupRelationshipDto ownDriverGroupRelationshipDto, BindingResult bindingResult) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名

        List<DriverGroupRelationship> driverGroupRelationshipList = new ArrayList<>();
        ArrayList<Long> arrayList = new ArrayList<Long>(Arrays.asList(ownDriverGroupRelationshipDto.getDriverGroupIds()));  //转化为list
        for(Long driverGroupId : arrayList){
            DriverGroupRelationship driverGroupRelationship = new DriverGroupRelationship();
            driverGroupRelationship.setOwnDriverId(ownDriverGroupRelationshipDto.getOwnDriverId()); //设置ownDriverid
            driverGroupRelationship.setDriverGroupId(driverGroupId);    //设置driverGroupId
            //设置企业id及创建人信息
            driverGroupRelationship.setCompanyId(companyId);
            driverGroupRelationship.setCreateId(userId);
            driverGroupRelationship.setCreateName(userName);
            driverGroupRelationshipList.add(driverGroupRelationship);
        }
        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()) {
            jsonObject.put("code", -1);
            jsonObject.put("message", bindingResult.getFieldError().getDefaultMessage());
            return jsonObject;
        }
        ownDriverService.addGroupInfo(driverGroupRelationshipList, ownDriverGroupRelationshipDto.getOwnDriverId());
        jsonObject.put("code", 0);
        jsonObject.put("message", "设置成功");
        return jsonObject;
    }

    @ApiOperation(value = "获取司机所属分组", notes = "获取司机所属分组")
    @GetMapping("/drivergroup")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('owndriver_group')")
    public PageBaseDto<List<DriverGroupDto>> setGroup() {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        List<DriverGroupDto> driverGroupDtoList = driverGroupService.selectRelationship(companyId);
        PageBaseDto pageBaseDto = new PageBaseDto(driverGroupDtoList, driverGroupDtoList.size());
        return pageBaseDto;
    }
}
