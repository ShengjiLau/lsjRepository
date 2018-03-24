package com.lcdt.traffic.web.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.service.ReportFormsService;
import com.lcdt.traffic.util.GroupIdsUtil;
import com.lcdt.userinfo.model.Group;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @AUTHOR liuh
 * @DATE 2017-12-25
 */
@Api(description = "首页统计api")
@RestController
@RequestMapping("/reportforms")
public class ReportFormsApi {

    Logger logger = LoggerFactory.getLogger(ReportFormsApi.class);

    @Autowired
    private ReportFormsService reportFormsService;

    @ApiOperation(value = "计划统计", notes = "报表统计-计划统计")
    @GetMapping("/plan")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('reportforms_plan')")
    public JSONObject planOverview(@RequestParam String param) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Map map = (Map) JSON.parseObject(param);
        String ids = map.get("groupIds") != null && map.get("groupIds") != "" ? map.get("groupIds").toString() : "";
        map.put("companyId", companyId);
        if(!"".equals(ids)){
            String[] idArr = ids.split(",");
            List groupIds = Arrays.asList(idArr);
            map.put("groupIds", groupIds);
        }
        JSONObject jsonObject = new JSONObject();
        try {
            Map<String, Object> resultMap = reportFormsService.planStatistics(map);

            JSONObject jsonResult = new JSONObject(resultMap);
            jsonObject.put("code", 0);
            jsonObject.put("message", "查询成功");
            jsonObject.put("data", jsonResult);
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("code", -1);
            jsonObject.put("message", new RuntimeException(e));
        }
        return jsonObject;
    }

    @ApiOperation(value = "运单统计", notes = "运输首页概览-运单统计")
    @GetMapping("/own/waybill")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('indexoverview')")
    public JSONObject waybillOverview(@RequestParam String param) {
        Long companyId = SecurityInfoGetter.getCompanyId();      //  获取companyId
        Map map = (Map) JSON.parseObject(param);
        String ids = map.get("groupIds") != null && map.get("groupIds") != "" ? map.get("groupIds").toString() : "";
        map.put("companyId", companyId);
        map.put("groupIds", GroupIdsUtil.getOwnGroupIds(ids));
        JSONObject jsonObject = new JSONObject();
        try {
            Map<String, Object> resultMap = reportFormsService.queryOwnWaybillStatistics(map);

            JSONObject jsonResult = new JSONObject(resultMap);
            jsonObject.put("code", 0);
            jsonObject.put("message", "查询成功");
            jsonObject.put("data", jsonResult);
        } catch (Exception e) {
            jsonObject.put("code", -1);
            jsonObject.put("message", new RuntimeException(e));
        }
        return jsonObject;
    }

    @ApiOperation(value = "客户运单统计", notes = "运输首页概览-运单统计")
    @GetMapping("/customer/waybill")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('indexoverview')")
    public JSONObject customerWaybillOverview(@RequestParam String param) {
        Long companyId = SecurityInfoGetter.getCompanyId();      //  获取companyId
        Map map = (Map) JSON.parseObject(param);
        String ids = map.get("groupIds") != null && map.get("groupIds") != "" ? map.get("groupIds").toString() : "";
        map.put("companyId", companyId);
        map.put("groupIds",GroupIdsUtil.getCustomerGroupIds(ids));
        JSONObject jsonObject = new JSONObject();
        try {
            Map<String, Object> resultMap = reportFormsService.queryCustomerWaybillStatistics(map);

            JSONObject jsonResult = new JSONObject(resultMap);
            jsonObject.put("code", 0);
            jsonObject.put("message", "查询成功");
            jsonObject.put("data", jsonResult);
        } catch (Exception e) {
            jsonObject.put("code", -1);
            jsonObject.put("message", new RuntimeException(e));
        }
        return jsonObject;
    }



}
