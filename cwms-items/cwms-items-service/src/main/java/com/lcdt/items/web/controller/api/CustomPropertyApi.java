package com.lcdt.items.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.converter.ArrayListResponseWrapper;
import com.lcdt.converter.ResponseData;
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
public class CustomPropertyApi {

    Logger logger = LoggerFactory.getLogger(CustomPropertyApi.class);

    //@Reference
    @Autowired
    private CustomPropertyService customPropertyService;

    @ApiOperation(value = "获取企业下所有的自定义属性", notes = "获取企业下所有的自定义属性")
    @GetMapping("/list")
    public List<CustomProperty> getCustomProperty(HttpSession httpSession) {
        logger.info("customPropertyService------------------", customPropertyService.getClass().getMethods().toString());
        Long companyId = 8L;  //TODO 从session获取companyId
        List<CustomProperty> customPropertyList = customPropertyService.customPropertyList(companyId);
        ArrayListResponseWrapper arrayListResponseWrapper = new ArrayListResponseWrapper(customPropertyList);
        if(1==1){
            throw new RuntimeException("异常");
        }
        return arrayListResponseWrapper;
    }

    @ApiOperation(value = "更新自定义属性", notes = "更新对应企业下的自定义属性")
    @PostMapping("/update")
    public JSONObject updateCoustomProperty(CustomProperty customProperty, HttpSession httpSession) {
        Map<String,Object> map = new HashMap<String, Object>();
        Long companyId = 8L;  //TODO 从session获取companyId
        customProperty.setCompanyId(companyId);
        customPropertyService.updateByCustomId(customProperty);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code","0");
        jsonObject.put("message","更新成功");
        return jsonObject;
    }

    @ApiOperation(value = "删除自定义属性", notes = "根据主键删除对应自定义属性")
    @PostMapping("/delete")
    public ResponseEntity<Map<String,Object>> delCustomProperty(Long customId) {
        Map<String,Object> map = new HashMap<String, Object>();
        try {
            customPropertyService.delByCustomId(customId);
            map.put("code",1);
            map.put("message","删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",0);
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
            map.put("code",1);
            map.put("message","新增成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",0);
            map.put("message","新增失败");
        }
        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
    }
}
