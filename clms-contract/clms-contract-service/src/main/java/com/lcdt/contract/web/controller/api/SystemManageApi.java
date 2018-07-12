package com.lcdt.contract.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.contract.service.SystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @AUTHOR liuh
 * @DATE 2018-07-05
 * <p>
 * 本类用于处理系统特殊需求（比如系统初始化，数据清理）
 */

@Api(description = "系统管理api")
@RestController
@RequestMapping("/contract")
public class SystemManageApi {

    @Autowired
    private SystemService systemService;

    @ApiOperation(value = "销售单审批列表", notes = "销售单审批列表")
    @GetMapping("/cleardata")
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') ")
    public JSONObject clearContractData(Long companyId){
        JSONObject jsonObject = new JSONObject();
        int rows = systemService.clearContractData(companyId);
        if (rows > 0) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "操作成功");
            jsonObject.put("rows",rows);
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "操作失败");
        }
        return jsonObject;
    }
}
