package com.lcdt.items.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.items.model.CustomProperty;
import com.lcdt.items.service.CustomPropertyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017-11-08
 */
@Api("商品自定义属性api")
@RestController
@RequestMapping("/items/customproperty")
public class CustomPropertyController {

    Logger logger = LoggerFactory.getLogger(CustomPropertyController.class);

    //@Reference
    @Autowired
    CustomPropertyService customPropertyService;

    @ApiOperation(value = "获取企业下所有的自定义属性", notes = "获取企业下所有的自定义属性")
    @GetMapping("/list")
    public List<CustomProperty> getCustomProperty(HttpSession httpSession) {
        logger.info("customPropertyService------------------", customPropertyService.getClass().getMethods().toString());
        Long companyId = 8L;  //从session获取companyId
        return customPropertyService.customPropertyList(companyId);
    }

    @ApiOperation(value = "更新自定义属性", notes = "更新对应企业下的自定义属性")
    @PostMapping("/update")
    public String updateCoustomProperty(CustomProperty customProperty, HttpSession httpSession) {
        Long companyId = 8L;  //从session获取companyId
        customProperty.setCompanyId(companyId);
        customPropertyService.updateByCustomId(customProperty);
        return "success";
    }

    @ApiOperation(value = "删除自定义属性", notes = "根据主键删除对应自定义属性")
    @PostMapping("/delete")
    public String delCustomProperty(Long customId) {
        customPropertyService.delByCustomId(customId);
        return "success";
    }

    @ApiOperation(value = "新增自定义属性", notes = "新增自定义属性")
    @PostMapping("/add")
    public String addCustomProperty(CustomProperty customProperty, HttpSession httpSession) {
        Long companyId = 8L;  //从session获取companyId
        customProperty.setCompanyId(companyId);
        customPropertyService.insertCustomProperty(customProperty);
        return "success";
    }
}
