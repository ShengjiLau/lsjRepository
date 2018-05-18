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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.lcdt.warehouse.dto.PageBaseDto;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Administrator on 2018/5/15.
 */
@Controller
@RequestMapping("/check")
public class CheckController {

    Logger logger = LoggerFactory.getLogger(CheckController.class);
    @Autowired
    private CheckService checkService;
    @ApiOperation("盘库列表")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public PageBaseDto checkList(@RequestBody CheckParamDto checkDto){
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名


        List<CheckListDto> checkList = checkService.selectList(checkDto);
        PageBaseDto pageBaseDto = new PageBaseDto(checkList,checkList.size());
        return pageBaseDto;
    }
    @ApiOperation("根据id读盘库记录")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public JSONObject findCheckById(@RequestParam Integer checkId){

        return null;
    }

    @ApiOperation("取消盘库")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public JSONObject cancelCheck(){

        return null;
    }
    @ApiOperation("保存盘库单和明细")
    @RequestMapping(value = "/",method = RequestMethod.POST)
    public JSONObject saveCheckAndItems(){
        return null;
    }
    @ApiOperation("保存并完成盘库")
    @RequestMapping(value = "/",method = RequestMethod.POST)
    public JSONObject updateCheckAndComplete(){
        return null;
    }



}
