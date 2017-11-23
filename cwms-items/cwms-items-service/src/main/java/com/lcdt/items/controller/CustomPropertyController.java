package com.lcdt.items.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.items.model.CustomProperty;
import com.lcdt.items.service.CustomPropertyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.event.ObjectChangeListener;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private CustomPropertyService customPropertyService;

    @ApiOperation(value = "获取企业下所有的自定义属性", notes = "获取企业下所有的自定义属性")
    @GetMapping("/list")
    public ResponseEntity<Map<String,Object>> getCustomProperty(HttpSession httpSession) {
        logger.info("customPropertyService------------------", customPropertyService.getClass().getMethods().toString());
        Long companyId = 8L;  //TODO 从session获取companyId
        List<CustomProperty> customPropertyList = customPropertyService.customPropertyList(companyId);
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("status",1);
        map.put("data",customPropertyList);
        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
    }

    @ApiOperation(value = "更新自定义属性", notes = "更新对应企业下的自定义属性")
    @PostMapping("/update")
    public ResponseEntity<Map<String,Object>> updateCoustomProperty(CustomProperty customProperty, HttpSession httpSession) {
        Map<String,Object> map = new HashMap<String, Object>();
        try {
            Long companyId = 8L;  //TODO 从session获取companyId
            customProperty.setCompanyId(companyId);
            customPropertyService.updateByCustomId(customProperty);
            map.put("status",1);
            map.put("message","更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",0);
            map.put("message","更新失败");
        }
        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
    }

    @ApiOperation(value = "删除自定义属性", notes = "根据主键删除对应自定义属性")
    @PostMapping("/delete")
    public ResponseEntity<Map<String,Object>> delCustomProperty(Long customId) {
        Map<String,Object> map = new HashMap<String, Object>();
        try {
            customPropertyService.delByCustomId(customId);
            map.put("status",1);
            map.put("message","删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",0);
            map.put("message","删除失败");
        }
        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
    }

    @ApiOperation(value = "新增自定义属性", notes = "新增自定义属性")
    @PostMapping("/add")
    public ResponseEntity<Map<String,Object>> addCustomProperty(CustomProperty customProperty, HttpSession httpSession) {
        Map<String,Object> map = new HashMap<String, Object>();
        try {
            Long companyId = 8L;  //TODO 从session获取companyId
            customProperty.setCompanyId(companyId);
            customPropertyService.insertCustomProperty(customProperty);
            map.put("status",1);
            map.put("message","新增成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",0);
            map.put("message","新增失败");
        }
        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
    }
}
