package com.lcdt.traffic.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.converter.ArrayListResponseWrapper;
import com.lcdt.traffic.model.DriverAndGroup;
import com.lcdt.traffic.model.DriverGroup;
import com.lcdt.traffic.service.DriverGroupService;
import com.lcdt.traffic.web.dto.DriverGroupDto2;
import com.lcdt.traffic.web.dto.PageBaseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017-12-25
 */
@Api(description = "司机分组管理api")
@RestController
@RequestMapping("/ownfleet/drivergroup")
public class DriverGroupApi {

    Logger logger = LoggerFactory.getLogger(OwnDriverApi.class);

    @Autowired
    private DriverGroupService driverGroupService;

    @ApiOperation(value = "新增分组", notes = "新增分组")
    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('drivergroup_add')")
    public JSONObject addOwnDriver(@RequestBody DriverGroup driverGroup, BindingResult bindingResult) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名
        driverGroup.setCompanyId(companyId);
        driverGroup.setCreateId(userId);
        driverGroup.setCreateName(userName);
        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()) {
            jsonObject.put("code", -1);
            jsonObject.put("message", bindingResult.getFieldError().getDefaultMessage());
            return jsonObject;
        }
        driverGroupService.addDriverGroup(driverGroup);
        jsonObject.put("code", 0);
        jsonObject.put("message", "新增成功");
        return jsonObject;
    }

    @ApiOperation(value = "修改分组", notes = "修改分组")
    @PostMapping("/modify")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('drivergroup_modify')")
    public JSONObject modOwnDriver(@RequestBody DriverGroup driverGroup, BindingResult bindingResult) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名
        driverGroup.setCompanyId(companyId);
        driverGroup.setUpdateId(userId);
        driverGroup.setUpdateName(userName);
        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()) {
            jsonObject.put("code", -1);
            jsonObject.put("message", bindingResult.getFieldError().getDefaultMessage());
            return jsonObject;
        }
        driverGroupService.modDriverGroup(driverGroup);
        jsonObject.put("code", 0);
        jsonObject.put("message", "修改成功");
        return jsonObject;
    }

    @ApiOperation(value = "删除分组", notes = "删除分组")
    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('drivergroup_delete')")
    public JSONObject delOwnDriver(@RequestBody DriverGroup driverGroup, BindingResult bindingResult) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名
        driverGroup.setCompanyId(companyId);
        driverGroup.setUpdateId(userId);
        driverGroup.setUpdateName(userName);
        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()) {
            jsonObject.put("code", -1);
            jsonObject.put("message", bindingResult.getFieldError().getDefaultMessage());
            return jsonObject;
        }
        driverGroupService.delDriverGroup(driverGroup);
        jsonObject.put("code", 0);
        jsonObject.put("message", "删除成功");
        return jsonObject;
    }

    @ApiOperation(value = "分组列表", notes = "分组列表")
    @PostMapping("/list")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('drivergroup_list')")
    public PageBaseDto<List<DriverGroup>> getOwnDriverList() {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        List<DriverGroup> driverGroupList = driverGroupService.selectAll(companyId);
        PageBaseDto pageBaseDto = new PageBaseDto(driverGroupList,driverGroupList.size());
        return pageBaseDto;
    }

    @ApiOperation(value = "分组列表和对应组的司机信息", notes = "分组列表和对应组的司机信息")
    @PostMapping("/groupdriver")
    public PageBaseDto<List<DriverAndGroup>> getDriverAndGroupList(int pageNum,int pageSize) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(pageNum);    //设置页码
        pageInfo.setPageSize(pageSize);  //设置每页条数
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        PageInfo<List<DriverAndGroup>> driverAndGroupList = driverGroupService.selectDriverAndGroup(companyId,pageInfo);
        PageBaseDto pageBaseDto = new PageBaseDto(driverAndGroupList.getList(), driverAndGroupList.getTotal());
        return pageBaseDto;
    }


    @ApiOperation(value = "根据司机分组获取司机信息", notes = "根据groupIds获取分组及司机信息")
    @GetMapping("/getdrivers")
//    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('owndriver_drivergroup')")
    public List<DriverGroupDto2> driverList(String driverGroupId) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        if(null==driverGroupId || driverGroupId==""){
            throw new RuntimeException("参数为空");
        }
        String [] groupIds = driverGroupId.split(",");
        List<DriverGroupDto2> driverGroupDto2List = driverGroupService.driverListByGroupId2(companyId,groupIds);
        ArrayListResponseWrapper arrayListResponseWrapper = new ArrayListResponseWrapper(driverGroupDto2List);
        return arrayListResponseWrapper;
    }
}
