package com.lcdt.items.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.items.model.ConversionRel;
import com.lcdt.items.service.ConversionRelService;
import com.lcdt.items.web.dto.ConversionRelDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by lyqishan on 2017/11/24
 */

@Api(description = "多单位管理api")
@RestController
@RequestMapping("/items/conversionrel")
public class ConversionRelApi {
    Logger log = LoggerFactory.getLogger(ConversionRelApi.class);

    @Autowired
    private ConversionRelService conversionRelService;

    @ApiOperation("修改多单位")
    @PostMapping("/modify")
    public ConversionRel addConversionRel(HttpSession httpSession, ConversionRelDto conversionRelDto){
        ConversionRel conversionRel=new ConversionRel();
        conversionRel.setConverId(conversionRelDto.getConverId());

        conversionRel.setUnitId(conversionRelDto.getUnitId());
        conversionRel.setUnitName(conversionRelDto.getUnitName());

        conversionRel.setUnitId1(conversionRelDto.getUnitId1());
        conversionRel.setUnitName1(conversionRelDto.getUnitName1());
        conversionRel.setData1(conversionRelDto.getData1());

        conversionRel.setUnitId2(conversionRelDto.getUnitId2());
        conversionRel.setUnitName2(conversionRelDto.getUnitName2());
        conversionRel.setData2(conversionRelDto.getData2());

        conversionRel.setCompanyId(8L);

        int result=conversionRelService.modifyConversionRel(conversionRel);
        if(result>0){
            return conversionRel;
        }else{
            throw new RuntimeException("修改失败");
        }
    }

    @ApiOperation("查询多单位")
    @GetMapping("/query")
    public ConversionRel queryConversionRel(HttpSession httpSession, @ApiParam(value = "多单位id", required = true) @RequestParam Long converId){
        ConversionRel conversionRel=conversionRelService.queryConversionRel(converId);
        if(conversionRel!=null){
            return conversionRel;
        }else{
            throw new RuntimeException("查询失败");
        }
    }
}
