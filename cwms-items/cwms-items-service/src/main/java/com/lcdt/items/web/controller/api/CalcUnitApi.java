package com.lcdt.items.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.items.model.CalcUnit;
import com.lcdt.items.service.CalcUnitService;
import com.lcdt.items.web.dto.PageBaseDto;
import com.lcdt.userinfo.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by lyqishan on 2017/11/24
 */

@Api(description = "单位管理api")
@RestController
@RequestMapping("/items/calcunit")
public class CalcUnitApi {
    Logger log = LoggerFactory.getLogger(CalcUnitApi.class);

    @Autowired
    private CalcUnitService calcUnitService;

    @ApiOperation("新增单位")
    @PostMapping("/add")
    public JSONObject addCalcUnit( HttpSession httpSession,@ApiParam(value = "单位名称", required = true) @RequestParam String unitName) {
        Long companyId= SecurityInfoGetter.getCompanyId();
        User user=SecurityInfoGetter.getUser();
        CalcUnit calcUnit = new CalcUnit();
        calcUnit.setUnitName(unitName);
        calcUnit.setCreateId(user.getUserId());
        calcUnit.setCreateName(user.getRealName());
        calcUnit.setCompanyId(companyId);
        if (calcUnitService.isUnitNameExist(calcUnit)) {
            throw new RuntimeException("单位名称已存在");
        }
        int result = calcUnitService.addCalcUnit(calcUnit);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "添加成功");
            return jsonObject;
        } else {
            throw new RuntimeException("添加失败");
        }
    }

    @ApiOperation("删除单位")
    @PostMapping("/delete")
    public JSONObject delCalcUnit(HttpSession httpSession,@ApiParam(value = "单位Id", required = true) @RequestParam Long unitId) {
        Long companyId= SecurityInfoGetter.getCompanyId();
        int result = calcUnitService.deleteCalcUnit(unitId,companyId);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "删除成功");
            return jsonObject;
        } else {
            throw new RuntimeException("删除失败");
        }
    }

    @ApiOperation("修改单位")
    @PostMapping("/modify")
    public JSONObject modifyCalcUnit(HttpSession httpSession,
                                     @ApiParam(value = "单位Id", required = true) @RequestParam Long unitId,
                                     @ApiParam(value = "单位名称", required = true) @RequestParam String unitName) {
        Long companyId= SecurityInfoGetter.getCompanyId();
        CalcUnit calcUnit = new CalcUnit();
        calcUnit.setUnitId(unitId);
        calcUnit.setUnitName(unitName);
        calcUnit.setCompanyId(companyId);
        if (calcUnitService.isUnitNameExist(calcUnit)) {
            throw new RuntimeException("单位名称已存在");
        }
        int result = calcUnitService.modifyByUnitIdAndCompanyId(calcUnit);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "修改成功");
            return jsonObject;
        } else {
            throw new RuntimeException("修改失败");
        }
    }

    @ApiOperation("查询单位")
    @GetMapping("/query")
    public CalcUnit queryCalcUnit(HttpSession httpSession,@ApiParam(value = "单位Id", required = true) @RequestParam Long unitId) {
        Long companyId= SecurityInfoGetter.getCompanyId();
        CalcUnit calcUnit=calcUnitService.queryCalcUnit(unitId,companyId);
        if(calcUnit==null){
            calcUnit=new CalcUnit();
        }
        return calcUnit;
    }

    @ApiOperation("查询单位")
    @GetMapping("/list")
    public PageBaseDto<List<CalcUnit>> queryCalcUnitList(HttpSession httpSession) {
        Long companyId= SecurityInfoGetter.getCompanyId();
        PageInfo pageInfo=new PageInfo();
        pageInfo.setPageSize(0);
        pageInfo.setPageNum(1);
        PageInfo<List<CalcUnit>> listPageInfo=calcUnitService.queryCalcUnitByCompanyId(companyId,pageInfo);
        return new PageBaseDto(listPageInfo.getList(),listPageInfo.getTotal());
    }
}
