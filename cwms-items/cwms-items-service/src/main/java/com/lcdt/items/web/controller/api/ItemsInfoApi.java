package com.lcdt.items.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.items.dto.ItemsInfoDto;
import com.lcdt.items.model.ItemsInfoDao;
import com.lcdt.items.service.ItemsInfoService;
import com.lcdt.items.web.dto.PageBaseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * Created by lyqishan on 2017/11/24
 */
@Api("商品信息api")
@RestController
@RequestMapping("/items/itemsinfo")
public class ItemsInfoApi {

    Logger log = LoggerFactory.getLogger(ItemsInfoApi.class);

    @Autowired
    private ItemsInfoService itemsInfoService;

    @ApiOperation("新增商品")
    @PostMapping("/add")
    public JSONObject addItemInfo(ItemsInfoDto itemsInfoDto, HttpSession httpSession) {
        int result = itemsInfoService.addItemsInfo(itemsInfoDto);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", '1');
            jsonObject.put("message", "添加成功");
            return jsonObject;
        } else {
            throw new RuntimeException("添加失败");
        }
    }

    @ApiOperation(value = "商品列表", notes = "获取商品列表") //add by liuh
    @GetMapping("/list")
    public PageBaseDto<List<ItemsInfoDao>> getItemInfoList(@Validated ItemsInfoDao itemsInfoDao, PageInfo pageInfo, HttpSession httpSession) {
        Long companyId = 8L; //TODO 后面从session中获取
        itemsInfoDao.setCompanyId(companyId);
        PageInfo<List<ItemsInfoDao>> listPageInfo = itemsInfoService.queryItemsByCondition(itemsInfoDao,pageInfo);
        return new PageBaseDto(listPageInfo.getList(),listPageInfo.getTotal());
    }

}
