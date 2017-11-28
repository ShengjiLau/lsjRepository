package com.lcdt.items.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.items.dto.ItemsInfoDto;
import com.lcdt.items.model.*;
import com.lcdt.items.service.ItemsInfoService;
import com.lcdt.items.web.dto.ItemsInfoAddDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
    public JSONObject addItemInfo(ItemsInfoAddDto itemsInfoAddDto, HttpSession httpSession) {
        Long companyId = 8L;
        ItemsInfoDao itemsInfoDao = new ItemsInfoDao();
        itemsInfoDao.setSubject(itemsInfoAddDto.getSubject());
        itemsInfoDao.setClassifyName(itemsInfoAddDto.getClassifyName());
        itemsInfoDao.setClassifyId(itemsInfoAddDto.getClassifyId());
        itemsInfoDao.setUnitId(itemsInfoAddDto.getUnitId());
        itemsInfoDao.setUnitName(itemsInfoAddDto.getUnitName());
        itemsInfoDao.setIntroduction(itemsInfoAddDto.getIntroduction());
        itemsInfoDao.setTradeType(itemsInfoAddDto.getTradeType());
        itemsInfoDao.setItemType(itemsInfoAddDto.getItemType());
        itemsInfoDao.setCompanyId(companyId);

        //主商品自定义属性
        if (itemsInfoAddDto.getCustomValueAddDtoList() != null) {
            List<CustomValue> customValueList = new ArrayList<CustomValue>();
            for (int i = 0; i < itemsInfoAddDto.getCustomValueAddDtoList().size(); i++) {
                CustomValue customValue = new CustomValue();
                customValue.setPropertyValue(itemsInfoAddDto.getCustomValueAddDtoList().get(i).getPropertyValue());
                customValue.setPropertyName(itemsInfoAddDto.getCustomValueAddDtoList().get(i).getPropertyName());
                customValue.setCompanyId(companyId);
                customValueList.add(customValue);
            }
            itemsInfoDao.setCustomValueList(customValueList);
        }
        //子商品
        if (itemsInfoAddDto.getSubItemsInfoAddDtoList() != null) {
            List<SubItemsInfoDao> subItemsInfoDaoList = new ArrayList<SubItemsInfoDao>();
            for (int i = 0; i < itemsInfoAddDto.getSubItemsInfoAddDtoList().size(); i++) {
                SubItemsInfoDao subItemsInfoDao = new SubItemsInfoDao();
                subItemsInfoDao.setCode(itemsInfoAddDto.getSubItemsInfoAddDtoList().get(i).getCode());
                subItemsInfoDao.setBarCode(itemsInfoAddDto.getSubItemsInfoAddDtoList().get(i).getBarCode());
                subItemsInfoDao.setPurchasePrice(itemsInfoAddDto.getSubItemsInfoAddDtoList().get(i).getPurchasePrice());
                subItemsInfoDao.setWholesalePrice(itemsInfoAddDto.getSubItemsInfoAddDtoList().get(i).getWholesalePrice());
                subItemsInfoDao.setRetailPrice(itemsInfoAddDto.getSubItemsInfoAddDtoList().get(i).getRetailPrice());
                subItemsInfoDao.setCompanyId(companyId);
                //子商品自定义属性
                if (itemsInfoAddDto.getSubItemsInfoAddDtoList().get(i).getCustomValueAddDtoList() != null) {
                    List<CustomValue> subCustomValueList = new ArrayList<CustomValue>();
                    for (int j = 0; j < itemsInfoAddDto.getSubItemsInfoAddDtoList().get(i).getCustomValueAddDtoList().size(); j++) {
                        CustomValue customValue = new CustomValue();
                        customValue.setPropertyValue(itemsInfoAddDto.getSubItemsInfoAddDtoList().get(i).getCustomValueAddDtoList().get(j).getPropertyValue());
                        customValue.setPropertyName(itemsInfoAddDto.getSubItemsInfoAddDtoList().get(i).getCustomValueAddDtoList().get(j).getPropertyName());
                        customValue.setCompanyId(companyId);
                        subCustomValueList.add(customValue);

                    }
                    subItemsInfoDao.setCustomValueList(subCustomValueList);
                }
                //子商品规格
                if (itemsInfoAddDto.getSubItemsInfoAddDtoList().get(i).getItemSpecKeyValueAddDtoList() != null) {
                    List<ItemSpecKeyValue> itemSpecKeyValueList = new ArrayList<ItemSpecKeyValue>();
                    for (int j = 0; j < itemsInfoAddDto.getSubItemsInfoAddDtoList().get(i).getItemSpecKeyValueAddDtoList().size(); j++) {
                        ItemSpecKeyValue itemSpecKeyValue = new ItemSpecKeyValue();
                        itemSpecKeyValue.setSpValue(itemsInfoAddDto.getSubItemsInfoAddDtoList().get(i).getItemSpecKeyValueAddDtoList().get(j).getSpValue());
                        itemSpecKeyValue.setSpName(itemsInfoAddDto.getSubItemsInfoAddDtoList().get(i).getItemSpecKeyValueAddDtoList().get(j).getSpName());
                        itemSpecKeyValue.setCompanyId(companyId);
                        itemSpecKeyValueList.add(itemSpecKeyValue);
                    }
                    subItemsInfoDao.setItemSpecKeyValueList(itemSpecKeyValueList);
                }
                subItemsInfoDaoList.add(subItemsInfoDao);
            }
            itemsInfoDao.setSubItemsInfoDaoList(subItemsInfoDaoList);
        }
        int result = itemsInfoService.addItemsInfo(itemsInfoDao);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", '0');
            jsonObject.put("message", "添加成功");
            return jsonObject;
        } else {
            throw new RuntimeException("添加失败");
        }
    }

    @ApiOperation("删除商品")
    @PostMapping("/delete")
    public JSONObject deleteItemsInfo(HttpSession httpSession, @ApiParam(value = "商品Id", required = true) @RequestParam Long itemId) {
        int result = itemsInfoService.deleteItemsInfo(itemId);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", "0");
            jsonObject.put("message", "删除成功");
            return jsonObject;

        } else {
            throw new RuntimeException("删除失败");
        }
    }

}
