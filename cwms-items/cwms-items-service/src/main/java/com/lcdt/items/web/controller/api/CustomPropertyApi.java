package com.lcdt.items.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.converter.ArrayListResponseWrapper;
import com.lcdt.items.model.CustomProperty;
import com.lcdt.items.service.CustomPropertyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017-11-08
 */
@Api(description = "商品自定义属性api")
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
        return arrayListResponseWrapper;
    }

    @ApiOperation(value = "更新自定义属性", notes = "更新对应企业下的自定义属性")
    @PostMapping("/modfiy")
    public JSONObject modCoustomProperty(@Validated CustomProperty customProperty, BindingResult bindingResult, HttpSession httpSession) {
        Long companyId = 8L;  //TODO 从session获取companyId
        customProperty.setCompanyId(companyId);
        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()) {
            jsonObject.put("code", "-1");
            jsonObject.put("message", bindingResult.getFieldError().getDefaultMessage());
            return jsonObject;
        }
        customPropertyService.updateByCustomId(customProperty);
        jsonObject.put("code", "0");
        jsonObject.put("message", "更新成功");
        return jsonObject;
    }

    @ApiOperation(value = "删除自定义属性", notes = "根据主键删除对应自定义属性")
    @PostMapping("/delete")
    public JSONObject delCustomProperty(Long customId) {
        JSONObject jsonObject = new JSONObject();
        customPropertyService.delByCustomId(customId);
        jsonObject.put("code", 1);
        jsonObject.put("message", "删除成功");
        return jsonObject;
    }

    @ApiOperation(value = "新增自定义属性", notes = "新增自定义属性")
    @PostMapping("/add")
    public JSONObject addCustomProperty(@Validated CustomProperty customProperty, BindingResult bindingResult, HttpSession httpSession) {
        Long companyId = 8L;  //TODO 从session获取companyId
        customProperty.setCompanyId(companyId);
        JSONObject jsonObject = new JSONObject();

        if (bindingResult.hasErrors()) {
            jsonObject.put("code", "-1");
            jsonObject.put("message", bindingResult.getFieldError().getDefaultMessage());
            return jsonObject;
        }
        customPropertyService.insertCustomProperty(customProperty);
        jsonObject.put("code", "0");
        jsonObject.put("message", "新增成功");
        return jsonObject;
    }
}
