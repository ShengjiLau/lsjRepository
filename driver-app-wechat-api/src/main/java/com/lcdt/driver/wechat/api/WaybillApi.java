package com.lcdt.driver.wechat.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.driver.dto.PageBaseDto;
import com.lcdt.traffic.dto.DriverWaybillListParsmsDto;
import com.lcdt.traffic.dto.DriverWaybillParamsDto;
import com.lcdt.traffic.model.Waybill;
import com.lcdt.traffic.service.WaybillRpcService;
import com.lcdt.userinfo.model.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyqishan on 2018/3/19
 */
@RestController
@RequestMapping("/waybill")
public class WaybillApi {
    @Reference
    WaybillRpcService waybillRpcService;


    @ApiOperation("司机运单--列表")
    @RequestMapping(value = "/driver/list", method = RequestMethod.GET)
   // @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_own_waybill_list')")
    public PageBaseDto<List<Waybill>> driverWaybillList(DriverWaybillListParsmsDto dto) {
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dto.setDriverId(loginUser.getUserId());
        PageInfo<List<Waybill>> listPageInfo = waybillRpcService.queryDriverWaybillList(dto);
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }

    @ApiOperation("司机运单--修改状态")
    @RequestMapping(value = "/driver/modifystatus", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_own_modify_status')")
    public JSONObject modifyDriverWaybillStatus(DriverWaybillParamsDto dto) {
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        int result=waybillRpcService.modifyWaybillStatusByDriver(dto);
        if(result>0){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("code",0);
            jsonObject.put("message","修改成功");
            return jsonObject;
        }else{
            throw new RuntimeException("修改失败");
        }
    }

    @ApiOperation("司机运单--上传电子回单")
    @RequestMapping(value = "/driver/modifyreceipt", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_own_modify_status')")
    public JSONObject modifyDriverWaybillReceipt(DriverWaybillParamsDto dto) {
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        int result=waybillRpcService.modifyWaybillReceiptByDriver(dto);
        if(result>0){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("code",0);
            jsonObject.put("message","上传成功");
            return jsonObject;
        }else{
            throw new RuntimeException("上传失败");
        }
    }

}
