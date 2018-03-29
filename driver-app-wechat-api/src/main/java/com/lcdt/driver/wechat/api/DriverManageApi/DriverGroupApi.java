package com.lcdt.driver.wechat.api.DriverManageApi;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.clms.security.helper.TokenSecurityInfoGetter;
import com.lcdt.converter.ArrayListResponseWrapper;
import com.lcdt.driver.dto.PageBaseDto;
import com.lcdt.traffic.dto.DriverGroupDto2;
import com.lcdt.traffic.model.DriverAndGroup;
import com.lcdt.traffic.model.DriverGroup;
import com.lcdt.traffic.service.DriverGroupService;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by lyqishan on 2018/3/27
 */
@RestController
@RequestMapping("/api/own/drivergroup")
@Api(value = "分组", description = "车辆分组")
public class DriverGroupApi {
    Logger logger = LoggerFactory.getLogger(OwnDriverApi.class);

    @Reference
    private DriverGroupService driverGroupService;

    @ApiOperation(value = "分组列表", notes = "分组列表")
    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('owndriver_group')")
    public PageBaseDto<List<DriverGroup>> getOwnDriverList() {
        Long companyId = TokenSecurityInfoGetter.getUserCompRel().getCompany().getCompId();
        List<DriverGroup> driverGroupList = driverGroupService.selectAll(companyId);
        PageBaseDto pageBaseDto = new PageBaseDto(driverGroupList,driverGroupList.size());
        return pageBaseDto;
    }

    @ApiOperation(value = "分组列表和对应组的司机信息", notes = "分组列表和对应组的司机信息")
    @GetMapping("/groupdriver")
    public PageBaseDto<List<DriverAndGroup>> getDriverAndGroupList(int pageNum, int pageSize) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(pageNum);    //设置页码
        pageInfo.setPageSize(pageSize);  //设置每页条数
        Long companyId = TokenSecurityInfoGetter.getUserCompRel().getCompany().getCompId(); //  获取companyId
        PageInfo<List<DriverAndGroup>> driverAndGroupList = driverGroupService.selectDriverAndGroup(companyId,pageInfo);
        PageBaseDto pageBaseDto = new PageBaseDto(driverAndGroupList.getList(), driverAndGroupList.getTotal());
        return pageBaseDto;
    }


    @ApiOperation(value = "根据司机分组获取司机信息", notes = "根据groupIds获取分组及司机信息")
    @GetMapping("/getdrivers")
    public List<DriverGroupDto2> driverList(String driverGroupId) {
        Long companyId = TokenSecurityInfoGetter.getUserCompRel().getCompany().getCompId(); //  获取companyId
        if(null==driverGroupId || driverGroupId==""){
            throw new RuntimeException("参数为空");
        }
        String [] groupIds = driverGroupId.split(",");
        List<DriverGroupDto2> driverGroupDto2List = driverGroupService.driverListByGroupId2(companyId,groupIds);
        ArrayListResponseWrapper arrayListResponseWrapper = new ArrayListResponseWrapper(driverGroupDto2List);
        return arrayListResponseWrapper;
    }
}
