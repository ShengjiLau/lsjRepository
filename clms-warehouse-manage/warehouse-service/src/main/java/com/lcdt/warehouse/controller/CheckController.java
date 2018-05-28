package com.lcdt.warehouse.controller;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.warehouse.dto.CheckListDto;
import com.lcdt.warehouse.dto.CheckParamDto;
import com.lcdt.warehouse.service.CheckService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.lcdt.warehouse.dto.PageBaseDto;

import java.util.List;

/**
 * Created by Administrator on 2018/5/15.
 */
@RestController
@RequestMapping("/check")
public class CheckController {

    Logger logger = LoggerFactory.getLogger(CheckController.class);
    @Autowired
    private CheckService checkService;
    @ApiOperation("盘库列表")
    @RequestMapping(value = "/checkList",method = RequestMethod.GET)
    public PageBaseDto checkList(CheckParamDto checkDto){
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名

        checkDto.setCompanyId(companyId);

        List<CheckListDto> checkList = checkService.selectList(checkDto);
        PageBaseDto pageBaseDto = new PageBaseDto(checkList,checkList.size());
        return pageBaseDto;
    }
    @ApiOperation("根据id读盘库记录")
    @RequestMapping(value = "/findCheckById",method = RequestMethod.GET)
    public JSONObject findCheckById(@RequestParam Integer checkId){

        return null;
    }

    @ApiOperation("取消盘库")
    @RequestMapping(value = "/cancelCheck",method = RequestMethod.GET)
    public String cancelCheck(Long checkId){
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名
        CheckParamDto checkDto = new CheckParamDto();
        checkDto.setCompanyId(companyId);
        checkDto.setCheckId(checkId);
        int result =  checkService.cancelCheck(checkDto);
        System.out.println("更新结果"+result);

        JSONObject jsonObject = new JSONObject();
        String message = "";
        int code = -1;
        if (result>0) {
            code = 0;
        } else {
            message = "操作失败，请重试！";
        }
        jsonObject.put("message",message);
        jsonObject.put("code",code);
        return jsonObject.toString();
    }
    @ApiOperation("保存盘库单和明细")
    @RequestMapping(value = "/saveCheckAndItems",method = RequestMethod.POST)
    public JSONObject saveCheckAndItems(){
        return null;
    }
    @ApiOperation("保存并完成盘库")
    @RequestMapping(value = "/updateCheckAndComplete",method = RequestMethod.POST)
    public JSONObject updateCheckAndComplete(){
        return null;
    }



}
