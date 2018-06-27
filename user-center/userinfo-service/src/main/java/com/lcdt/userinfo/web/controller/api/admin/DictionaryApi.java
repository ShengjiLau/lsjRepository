package com.lcdt.userinfo.web.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.dto.DictionaryQueryDto;
import com.lcdt.userinfo.model.Dictionary;
import com.lcdt.userinfo.service.DictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/6/19.
 */
@Api(value = "字典api",description = "字典api")
@RestController
@RequestMapping("/dictionary")
public class DictionaryApi {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    DictionaryService dictionaryService;

    @ApiOperation("字典列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public JSONObject dictionaryList(@RequestBody  DictionaryQueryDto dto){
        JSONObject jo = new JSONObject();
        try {
            jo.put("code",0);
            if(dto.getPageNo() != null){
                PageInfo<Dictionary> pageInfo = PageHelper.startPage(dto.getPageNo(),dto.getPageSize()).doSelectPageInfo(()->dictionaryService.selectAll(dto));
                jo.put("data",pageInfo);
            }else{
                jo.put("data",dictionaryService.selectAll(dto));
            }

        }catch(Exception e){
            jo.put("code",-1);
            jo.put("message","查询列表出错");
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return jo;
    }

    @ApiOperation("字典添加")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public JSONObject dictionaryAdd(Dictionary dic){
        JSONObject jo = new JSONObject();
        try {
            boolean result = false;
            if(dic.getDictionaryId() != null){
                result = dictionaryService.updateByPrimaryKey(dic)>0;
            }else{
                result = dictionaryService.insert(dic)>0;
            }
            if(result){
                jo.put("code",0);
            }else{
                jo.put("code",-1);
                jo.put("message","操作失败！");
            }
        }catch(Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return jo;
    }

    @ApiOperation("字典删除")
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public JSONObject dictionaryDel(Integer dictionaryId){
        JSONObject jo = new JSONObject();
        try {
            boolean result = false;
            result = dictionaryService.deleteByPrimaryKey(dictionaryId)>0;
            if(result){
                jo.put("code",0);
            }else{
                jo.put("code",-1);
                jo.put("message","操作失败！");
            }
        }catch(Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return jo;
    }
}
