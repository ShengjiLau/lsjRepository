package com.lcdt.warehouse.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.model.User;
import com.lcdt.warehouse.dto.*;
import com.lcdt.warehouse.entity.TCheck;
import com.lcdt.warehouse.service.CheckService;
import com.lcdt.warehouse.utils.DateUtils;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_check_search')")
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_check_search') or hasAuthority('wh_check_complete')")
    public JSONObject findCheckById(@RequestParam Long checkId) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名
        CheckParamDto checkDto = new CheckParamDto();
        checkDto.setCompanyId(companyId);
        checkDto.setCheckId(checkId);
        List<CheckListDto> checkList = checkService.selectList(checkDto);
        JSONObject jsonObject = new JSONObject();

        if (CollectionUtils.isNotEmpty(checkList)){
            CheckListDto dto  = checkList.get(0);
            dto.setCompleteName(userName);
            jsonObject.put("data", dto);
        }
//        jsonObject.put("userName",userName);
        jsonObject.put("code", 0);
        jsonObject.put("message", "读取成功！");
        return jsonObject;
    }

    @ApiOperation("取消盘库")
    @RequestMapping(value = "/cancelCheck/{checkId}", method = RequestMethod.PATCH)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_check_cancel')")
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
        checkDto.setCheckStatus((Integer) CheckStatusEnum.cancel.getValue());
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_check_create')")
    public JSONObject saveCheckAndItems(@Validated CheckSaveDto checkSaveDto, BindingResult bindingResult) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名
        TCheck check = new TCheck();
        BeanUtils.copyProperties(checkSaveDto, check);
        check.setCheckStatus((Integer) CheckStatusEnum.watting.getValue());
        check.setIsDeleted(0);
        check.setCreateDate(new Date());
        check.setCreateId(userId);
        check.setCreateName(userName);
        check.setCompanyId(companyId);
        List<Map<String, Object>> items = checkSaveDto.getItems();
        System.out.println("check.getWarehouseId:"+check.getWarehouseId());
        System.out.println("check.getWarehouseName:"+check.getWarehouseName());
        boolean result = checkService.insertCheckAndItems(check, items);

        JSONObject jsonObject = new JSONObject();
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_check_complete')")
    public JSONObject updateCheckAndComplete(@Validated CheckSaveDto checkSaveDto) {
        JSONObject jsonObject = new JSONObject();
        String message = "";
        boolean result = true;
        int code = -1;
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名

        TCheck check = new TCheck();
        BeanUtils.copyProperties(checkSaveDto,check);
        check.setCompleteDate(DateUtils.string2Date_safe(checkSaveDto.getCompleteDate(),"yyyy-MM-dd"));
        check.setCheckStatus((Integer) CheckStatusEnum.completed.getValue());
        check.setUpdateDate(new Date());
        check.setUpdateName(userName);
        check.setUpdateId(userId);

        List<Map<String, Object>> items = checkSaveDto.getItems();
        try {
            checkService.completeCheckAndItems(check, items);
        }catch (RuntimeException e){
            e.printStackTrace();
            message = e.getLocalizedMessage();
            jsonObject.put("message", message);
            jsonObject.put("code", code);
            return jsonObject;
        }

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


    @ApiOperation("首页待盘库数量")
    @RequestMapping(value = "/findWaittingChecks", method = RequestMethod.GET)
    public JSONObject findWaittingChecks() {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名
        CheckParamDto checkDto = new CheckParamDto();
        checkDto.setCompanyId(companyId);
        checkDto.setCheckStatus((Integer) CheckStatusEnum.watting.getValue());
        int num = checkService.selectWattingNum(checkDto);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("checkNums", num);

//        jsonObject.put("userName",userName);
        jsonObject.put("code", 0);
        jsonObject.put("message", "读取成功！");
        return jsonObject;
    }

}
