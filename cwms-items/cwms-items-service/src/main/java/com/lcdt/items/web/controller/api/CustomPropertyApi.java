package com.lcdt.items.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.converter.ArrayListResponseWrapper;
import com.lcdt.items.model.CustomProperty;
import com.lcdt.items.service.CustomPropertyService;
import com.lcdt.items.web.dto.PageBaseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('item_customproperty_list')")
    public PageBaseDto<List<CustomProperty>> getCustomProperty(HttpSession httpSession) {
        logger.info("customPropertyService------------------", customPropertyService.getClass().getMethods().toString());
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        List<CustomProperty> customPropertyList = customPropertyService.customPropertyList(companyId);
        return new PageBaseDto(customPropertyList,customPropertyList.size());
    }

    @ApiOperation(value = "更新自定义属性", notes = "更新对应企业下的自定义属性")
    @PostMapping("/modfiy")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('item_customproperty_edit')")
    public JSONObject modCoustomProperty(@Validated CustomProperty customProperty, BindingResult bindingResult, HttpSession httpSession) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        customProperty.setCompanyId(companyId);
        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()) {
            jsonObject.put("code", -1);
            jsonObject.put("message", bindingResult.getFieldError().getDefaultMessage());
            return jsonObject;
        }
        customPropertyService.updateByCustomId(customProperty);
        jsonObject.put("code", 0);
        jsonObject.put("message", "更新成功");
        return jsonObject;
    }

    @ApiOperation(value = "删除自定义属性", notes = "根据主键删除对应自定义属性")
    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('item_customproperty_remove')")
    public JSONObject delCustomProperty(Long customId) {
        JSONObject jsonObject = new JSONObject();
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        customPropertyService.delByCustomId(customId,companyId);
        jsonObject.put("code", 0);
        jsonObject.put("message", "删除成功");
        return jsonObject;
    }

    @ApiOperation(value = "新增自定义属性", notes = "新增自定义属性")
    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('item_customproperty_add')")
    public JSONObject addCustomProperty(@Validated CustomProperty customProperty, BindingResult bindingResult, HttpSession httpSession) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId();
        String userName = SecurityInfoGetter.getUser().getRealName();
        customProperty.setCompanyId(companyId);
        customProperty.setCreateId(userId);
        customProperty.setCreateName(userName);
        JSONObject jsonObject = new JSONObject();

        if (bindingResult.hasErrors()) {
            jsonObject.put("code", -1);
            jsonObject.put("message", bindingResult.getFieldError().getDefaultMessage());
            return jsonObject;
        }
        customPropertyService.insertCustomProperty(customProperty);
        jsonObject.put("code", 0);
        jsonObject.put("message", "新增成功");
        return jsonObject;
    }
}
