package com.lcdt.warehouse.controller;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.model.User;
import com.lcdt.warehouse.dto.*;
import com.lcdt.warehouse.entity.TCheck;
import com.lcdt.warehouse.service.CheckService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/15.
 */
@RestController
@RequestMapping("/checkapi")
public class CheckController {

    Logger logger = LoggerFactory.getLogger(CheckController.class);
    @Autowired
    private CheckService checkService;

    @ApiOperation("盘库列表")
    @RequestMapping(value = "/checkList", method = RequestMethod.GET)
    public PageBaseDto checkList(CheckParamDto checkDto) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名

        checkDto.setCompanyId(companyId);

        List<CheckListDto> checkList = checkService.selectList(checkDto);
        PageBaseDto pageBaseDto = new PageBaseDto(checkList, checkList.size());
        return pageBaseDto;
    }

    @ApiOperation("根据id读盘库记录")
    @RequestMapping(value = "/findCheckById", method = RequestMethod.GET)
    public JSONObject findCheckById(@RequestParam Integer checkId) {

        return null;
    }

    @ApiOperation("取消盘库")
    @RequestMapping(value = "/cancelCheck/{checkId}", method = RequestMethod.PATCH)
    public JSONObject cancelCheck(@PathVariable Long checkId) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名
        CheckParamDto checkDto = new CheckParamDto();
        checkDto.setCompanyId(companyId);
        checkDto.setCheckId(checkId);
        checkDto.setUpdateDate(new Date());
        checkDto.setUpdateId(userId);
        checkDto.setUpdateName(userName);
        int result = checkService.cancelCheck(checkDto);

        JSONObject jsonObject = new JSONObject();
        String message = "";
        int code = -1;
        if (result > 0) {
            code = 0;
            message = "取消成功！";
        } else {
            message = "取消失败，请重试！";
        }
        jsonObject.put("message", message);
        jsonObject.put("code", code);
        return jsonObject;
    }

    @ApiOperation("保存盘库单和明细")
    @RequestMapping(value = "/saveCheckAndItems", method = RequestMethod.POST)
    public JSONObject saveCheckAndItems(@Validated CheckSaveDto checkSaveDto, BindingResult bindingResult) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名
        TCheck check = new TCheck();
        BeanUtils.copyProperties(checkSaveDto, check);
        check.setCheckStatus(1);
        check.setIsDeleted(0);
        check.setCreateDate(new Date());
        check.setCreateId(userId);
        check.setCompanyId(companyId);
        JSONObject jsonObject = new JSONObject();
        List<Map<String, Object>> items = checkSaveDto.getItems();

        boolean result = checkService.insertCheckAndItems(check, items);

        String message = "";
        int code = -1;
        if (result) {
            code = 0;
            message = "保存成功！";
        } else {
            message = "保存失败，请重试！";
        }
        jsonObject.put("message", message);
        jsonObject.put("code", code);
        return jsonObject;
    }

    @ApiOperation("保存并完成盘库")
    @RequestMapping(value = "/updateCheckAndComplete", method = RequestMethod.POST)
    public JSONObject updateCheckAndComplete() {
        return null;
    }


}
