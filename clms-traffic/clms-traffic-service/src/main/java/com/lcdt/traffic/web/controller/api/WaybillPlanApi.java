package com.lcdt.traffic.web.controller.api;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.model.WaybillPlan;
import com.lcdt.traffic.service.WaybillPlanService;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.traffic.web.dto.WaybillParamsDto;
import com.lcdt.traffic.web.dto.WaybillPlanListParamsDto;
import com.lcdt.traffic.web.dto.WaybillPlanResultDto;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2017/12/13.
 */

@RestController
@RequestMapping("/api/wabillplan")
@Api(value = "计划",description = "计划接口")
public class WaybillPlanApi {

    @Autowired
    private WaybillPlanService waybillPlanService;

    @ApiOperation("创建--发布")
    @RequestMapping(value = "/createWaybillPlan",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_create_waybill_plan')")
    public String createWaybillPlan(@RequestBody WaybillParamsDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setCreateId(loginUser.getUserId());
        dto.setCreateName(loginUser.getRealName());
        dto.setCompanyId(companyId);
        dto.setPlanSource(ConstantVO.PLAN_SOURCE_ENTERING); //计划来源-录入
        waybillPlanService.createWaybillPlan(dto, (short) 1);
        return null;
   }


    @ApiOperation("暂存计划")
    @RequestMapping(value = "/storageWaybillPlan",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_storage_waybill_plan')")
    public WaybillPlan storageWaybillPlan(@Validated WaybillParamsDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setCreateId(loginUser.getUserId());
        dto.setCreateName(loginUser.getRealName());
        dto.setCompanyId(companyId);
        dto.setPlanSource(ConstantVO.PLAN_SOURCE_ENTERING);//计划来源-录入
        return  waybillPlanService.createWaybillPlan(dto, (short) 2);
    }


    @ApiOperation("创建完--发布")
    @RequestMapping(value = "/publishWaybillPlan",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_publish_waybill_plan')")
    public WaybillPlan publishWaybillPlan(@RequestBody WaybillParamsDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setCreateId(loginUser.getUserId());
        dto.setCreateName(loginUser.getRealName());
        dto.setCompanyId(companyId);
        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        return waybillPlanService.publishWayBillPlan(dto);
    }


    @ApiOperation("我的计划-列表")
    @RequestMapping(value = "/myPlanList",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_my_waybill_plan_list')")
    public WaybillPlanResultDto myPlanList(@Validated WaybillPlanListParamsDto dto,
                             @ApiParam(value = "页码",required = true) @RequestParam Integer pageNo,
                             @ApiParam(value = "每页显示条数",required = true) @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("page_no", pageNo);
        map.put("page_size", pageSize);

        StringBuffer sb = new StringBuffer();
        if (StringUtil.isNotEmpty(dto.getGroupIds())) {//业务组
            sb.append(" find_in_set('"+dto.getGroupIds()+"',group_id)");
        } else {
            List<Group> groupList = SecurityInfoGetter.groups();
            for(int i=0;i<groupList.size();i++) {
                Group group = groupList.get(i);
                sb.append(" find_in_set('"+group.getGroupId()+"',group_id)");
                if(i!=groupList.size()-1){
                    sb.append(" or ");
                }
            }
            sb.append(")");
        }
        if (!sb.toString().isEmpty()) {
            map.put("groupIds", sb.toString());
        }
        if (StringUtil.isNotEmpty(dto.getCustomerName())) {
            map.put("customerName",dto.getCustomerName());
        }
        if (StringUtil.isNotEmpty(dto.getSerialCode())) {
            map.put("serialCode",dto.getSerialCode());
        }
        if (StringUtil.isNotEmpty(dto.getPlanStatus())) {
            map.put("planStatus",dto.getPlanStatus());
        }
        if (StringUtil.isNotEmpty(dto.getReceiveProvince())) {
            map.put("receiveProvince",dto.getReceiveProvince());
        }
        if (StringUtil.isNotEmpty(dto.getReceiveCity())) {
            map.put("receiveCity",dto.getReceiveCity());
        }
        if (StringUtil.isNotEmpty(dto.getReceiveCounty())) {
            map.put("receiveCounty",dto.getReceiveCounty());
        }
        if (StringUtil.isNotEmpty(dto.getPubdateBegin())) { //发布时间
            map.put("pubdateBegin",dto.getPubdateBegin());
        }
        if (StringUtil.isNotEmpty(dto.getPubdateEnd())) {
            map.put("pubdateEnd",dto.getPubdateEnd());
        }
        if (StringUtil.isNotEmpty(dto.getGoodsInfo())) {
            map.put("goodsInfo",dto.getGoodsInfo());
        }
        PageInfo pageInfo = waybillPlanService.wayBillPlanList(map);
        WaybillPlanResultDto dto1 = new WaybillPlanResultDto();
        dto1.setList(pageInfo.getList());
        dto1.setTotal(pageInfo.getTotal());
        return dto1;
    }




}


