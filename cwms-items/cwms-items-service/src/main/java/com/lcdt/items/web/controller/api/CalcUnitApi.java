package com.lcdt.items.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.converter.ArrayListResponseWrapper;
import com.lcdt.items.model.CalcUnit;
import com.lcdt.items.service.CalcUnitService;
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

@Api("单位管理api")
@RestController
@RequestMapping("/items/calcunit")
public class CalcUnitApi {
    Logger log = LoggerFactory.getLogger(CalcUnitApi.class);

    @Autowired
    private CalcUnitService calcUnitService;

    @ApiOperation("新增单位")
    @PostMapping("/add")
    public JSONObject addCalcUnit( HttpSession httpSession,@ApiParam(value = "单位名称", required = true) @RequestParam String unitName) {
        CalcUnit calcUnit = new CalcUnit();
        calcUnit.setUnitName(unitName);
        calcUnit.setCompanyId(8L);
        if (calcUnitService.isUnitNameExist(calcUnit)) {
            throw new RuntimeException("单位名称已存在");
        }
        int result = calcUnitService.addCalcUnit(calcUnit);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", "1");
            jsonObject.put("message", "添加成功");
            return jsonObject;
        } else {
            throw new RuntimeException("添加失败");
        }
    }

    @ApiOperation("删除单位")
    @PostMapping("/delete")
    public JSONObject delCalcUnit(HttpSession httpSession,@ApiParam(value = "单位Id", required = true) @RequestParam Long unitId) {
        int result = calcUnitService.deleteCalcUnit(unitId);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", "1");
            jsonObject.put("message", "删除成功");
            return jsonObject;
        } else {
            throw new RuntimeException("删除失败");
        }
    }

    @ApiOperation("修改单位")
    @PostMapping("/update")
    public JSONObject modifyCalcUnit(HttpSession httpSession,
                                     @ApiParam(value = "单位Id", required = true) @RequestParam Long unitId,
                                     @ApiParam(value = "单位名称", required = true) @RequestParam String unitName) {
        CalcUnit calcUnit = new CalcUnit();
        calcUnit.setUnitId(unitId);
        calcUnit.setUnitName(unitName);
        calcUnit.setCompanyId(8L);
        if (calcUnitService.isUnitNameExist(calcUnit)) {
            throw new RuntimeException("单位名称已存在");
        }
        int result = calcUnitService.modifyCalcUnitByPrimaryKey(calcUnit);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", "1");
            jsonObject.put("message", "修改成功");
            return jsonObject;
        } else {
            throw new RuntimeException("修改失败");
        }
    }

    @ApiOperation("查询单位")
    @GetMapping("/query")
    public CalcUnit queryCalcUnit(HttpSession httpSession,@ApiParam(value = "单位Id", required = true) @RequestParam Long unitId) {
        Long companyId = 8L;
        return calcUnitService.queryCalcUnitByPrimaryKey(unitId);
    }

    @ApiOperation("查询单位")
    @GetMapping("/list")
    public List<CalcUnit> queryCalcUnitList(HttpSession httpSession) {
        Long companyId = 8L;
        List<CalcUnit> calcUnitList=new ArrayListResponseWrapper<CalcUnit>(calcUnitService.queryCalcUnitByCompanyId(companyId));
        return calcUnitList;
    }
}
