package com.lcdt.items.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.converter.ArrayListResponseWrapper;
import com.lcdt.items.dto.ItemSpecKeyDto;
import com.lcdt.items.model.ItemSpecificationDao;
import com.lcdt.items.service.ItemSpecificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017-11-23
 */
@Api(description = "商品自定义规格")
@RestController
@RequestMapping("/item/specification")
public class ItemSpecificationApi {

    @Autowired
    private ItemSpecificationService itemSpecificationService;

    @ApiOperation(value = "获取自定义规格", notes = "获取自定义规格")
    @GetMapping("/list")
    public List<ItemSpecificationDao> getItemspecificationList(HttpSession httpSession) {
        Long companyId = 1L; //TODO 后面从session中获取
        List<ItemSpecificationDao> itemSpecificationDaoList = itemSpecificationService.querySpecification(companyId);
        ArrayListResponseWrapper arrayListResponseWrapper = new ArrayListResponseWrapper(itemSpecificationDaoList);
        return arrayListResponseWrapper;
    }

    @ApiOperation(value = "新增自定义规格", notes = "新增自定义规格")
    @PostMapping("/add")
    public JSONObject addItemspecification(@Validated ItemSpecKeyDto itemSpecKeyDto, BindingResult bindingResult, HttpSession httpSession) {
        //TODO 此处得校验暂时无法校验内部包含的list里得对象属性，后续可以通过自定义验证来解决（时间问题暂时搁置）
        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()) {
            jsonObject.put("code", -1);
            jsonObject.put("message", bindingResult.getFieldError().getDefaultMessage());
            return jsonObject;
        }
        Long companyId = 1L; //TODO 后面从session中获取
        itemSpecKeyDto.setCompanyId(companyId);
        itemSpecificationService.addSpecification(itemSpecKeyDto);
        jsonObject.put("code", 0);
        jsonObject.put("message", "新增成功");
        return jsonObject;
    }

    @ApiOperation(value = "编辑自定义规格", notes = "编辑自定义规格")
    @PostMapping("/modify")
    public JSONObject modItemspecification(@Validated @RequestBody ItemSpecKeyDto itemSpecKeyDto, BindingResult bindingResult, HttpSession httpSession) {
        //TODO 此处得校验暂时无法校验内部包含的list里得对象属性，后续可以通过自定义验证来解决（时间问题暂时搁置）
        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()) {
            jsonObject.put("code", -1);
            jsonObject.put("message", bindingResult.getFieldError().getDefaultMessage());
            return jsonObject;
        }
        Long companyId = 1L; //TODO 后面从session中获取
        itemSpecKeyDto.setCompanyId(companyId);
        itemSpecificationService.modifySpecification(itemSpecKeyDto);
        jsonObject.put("code", 0);
        jsonObject.put("message", "修改成功");
        return jsonObject;
    }

    @ApiOperation(value = "删除自定义规格", notes = "删除自定义规格")
    @PostMapping("/delete")
    public JSONObject delItemspecification(@RequestParam(value = "spkId",defaultValue = "0")  Long spkId, HttpSession httpSession) {
        JSONObject jsonObject = new JSONObject();
        if (spkId==0) {
            jsonObject.put("code", -1);
            jsonObject.put("message", "spkId不能为空");
            return jsonObject;
        }
//        Long companyId = 1L; // 后面从session中获取
//        itemSpecKeyDto.setCompanyId(companyId);
        int rows = itemSpecificationService.deleteSpecificationKeyAndValueBySpkId(spkId);
        if(rows>0){
            jsonObject.put("code", 0);
            jsonObject.put("message", "删除成功");
        }else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "删除失败");
        }
        return jsonObject;
    }

}
