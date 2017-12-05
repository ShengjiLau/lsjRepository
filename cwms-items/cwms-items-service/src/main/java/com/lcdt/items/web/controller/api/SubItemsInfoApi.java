package com.lcdt.items.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.converter.ArrayListResponseWrapper;
import com.lcdt.items.model.SubItemsInfoDao;
import com.lcdt.items.service.SubItemsInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by lyqishan on 2017/11/28
 */
@Api(description = "子商品信息api")
@RestController
@RequestMapping("/items/subitemsinfo")
public class SubItemsInfoApi {
    @Autowired
    private SubItemsInfoService subItemsInfoService;

    @ApiOperation("删除子商品")
    @PostMapping("/delete")
    public JSONObject deleteSubItemsInfo(HttpSession httpSession, @ApiParam(value = "子商品Id", required = true) @RequestParam Long subItemId){
        Long companyId= SecurityInfoGetter.getCompanyId();
        int result=subItemsInfoService.deleteSubItemsInfo(subItemId,companyId);
        if(result>0){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("code",0);
            jsonObject.put("message","删除成功");
            return jsonObject;
        }else {
            throw new RuntimeException("删除失败");
        }
    }

    @ApiOperation("查询子商品列表")
    @GetMapping("/list")
    public List<SubItemsInfoDao> querySubItemsInfo(HttpSession httpSession, @ApiParam(value = "子商品Id", required = true) @RequestParam Long itemId){
        Long companyId=SecurityInfoGetter.getCompanyId();
        List<SubItemsInfoDao> subItemsInfoList=new ArrayListResponseWrapper<SubItemsInfoDao>(subItemsInfoService.querySubAndSpecAndPropListByItemId(itemId,companyId));
        return subItemsInfoList;
    }

}
