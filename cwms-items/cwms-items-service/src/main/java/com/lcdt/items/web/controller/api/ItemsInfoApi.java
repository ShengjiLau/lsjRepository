package com.lcdt.items.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.items.model.*;
import com.lcdt.items.model.ItemsInfoDao;
import com.lcdt.items.service.ItemsInfoService;
import com.lcdt.items.web.dto.ItemsInfoDto;
import com.lcdt.items.web.dto.PageBaseDto;
import com.lcdt.userinfo.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.ArrayList;


/**
 * Created by lyqishan on 2017/11/24
 */
@Api(description = "商品信息api")
@RestController
@RequestMapping("/items/itemsinfo")
public class ItemsInfoApi {

    Logger log = LoggerFactory.getLogger(ItemsInfoApi.class);

    @Autowired
    private ItemsInfoService itemsInfoService;

    @ApiOperation("新增商品")
    @PostMapping("/add")
    public JSONObject addItemInfo(@RequestBody ItemsInfoDto itemsInfoDto, HttpSession httpSession) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User user=SecurityInfoGetter.getUser();
        int result = itemsInfoService.addItemsInfo(parseItemsInfoDao(itemsInfoDto,companyId,user));
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "添加成功");
            return jsonObject;
        } else {
            throw new RuntimeException("添加失败");
        }
    }

    @ApiOperation(value = "商品列表", notes = "获取商品列表") //add by liuh
    @GetMapping("/list")
    public PageBaseDto<List<ItemsInfoDao>> getItemInfoList(@Validated ItemsInfoDao itemsInfoDao, PageInfo pageInfo, HttpSession httpSession) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //TODO 后面从session中获取
        itemsInfoDao.setCompanyId(companyId);
        PageInfo<List<ItemsInfoDao>> listPageInfo = itemsInfoService.queryItemsByCondition(itemsInfoDao,pageInfo);
        return new PageBaseDto(listPageInfo.getList(),listPageInfo.getTotal());
    }

    @ApiOperation("删除商品")
    @PostMapping("/delete")
    public JSONObject deleteItemsInfo(HttpSession httpSession, @ApiParam(value = "商品Id", required = true) @RequestParam Long itemId) {
        Long companyId=SecurityInfoGetter.getCompanyId();
        int result = itemsInfoService.deleteItemsInfo(itemId,companyId);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "删除成功");
            return jsonObject;

        } else {
            throw new RuntimeException("删除失败");
        }
    }

    @ApiOperation("查询商品")
    @GetMapping("/queryitemsinfo")
    public ItemsInfo queryItemsInfoDetails(HttpSession httpSession, @ApiParam(value = "商品Id", required = true) @RequestParam Long itemId){
        Long companyId=SecurityInfoGetter.getCompanyId();
        ItemsInfoDao itemsInfoDao=null;
        itemsInfoDao=itemsInfoService.queryIetmsInfoDetails(itemId,companyId);
        if(itemsInfoDao==null){
            itemsInfoDao=new ItemsInfoDao();
        }
        return itemsInfoDao;
    }

    @ApiOperation("修改商品")
    @PostMapping("/modify")
    public JSONObject modifyItemsInfo(HttpSession httpSession,@RequestBody ItemsInfoDto itemsInfoDto){
        Long companyId = SecurityInfoGetter.getCompanyId();
        User user=SecurityInfoGetter.getUser();
        int result = itemsInfoService.modifyItemsInfo(parseItemsInfoDao(itemsInfoDto,companyId,user));
        JSONObject jsonObject=new JSONObject();
        if(result>0){
            jsonObject.put("code",0);
            jsonObject.put("message","修改成功");
            return jsonObject;
        }else{
            throw new RuntimeException("修改失败");

        }
    }
    @ApiOperation(value = "商品列表", notes = "获取商品列表返回所有信息")
    @GetMapping("/itemsinfolist")
    public PageBaseDto<List<ItemsInfoDao>> queryItemInfoList(@Validated ItemsInfoDao itemsInfoDao, PageInfo pageInfo, HttpSession httpSession) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        itemsInfoDao.setCompanyId(companyId);
        PageInfo<List<ItemsInfoDao>> listPageInfo = itemsInfoService.queryItemsByItemsInfo(itemsInfoDao,pageInfo);
        return new PageBaseDto(listPageInfo.getList(),listPageInfo.getTotal());
    }
    /**
     * 私有方法，前端dto转换成dao
     * @param itemsInfoDto
     * @param companyId
     * @return
     */
    private ItemsInfoDao parseItemsInfoDao(ItemsInfoDto itemsInfoDto, Long companyId, User user){
        ItemsInfoDao itemsInfoDao = new ItemsInfoDao();
        itemsInfoDao.setItemId(itemsInfoDto.getItemId());
        itemsInfoDao.setSubject(itemsInfoDto.getSubject());
        itemsInfoDao.setClassifyName(itemsInfoDto.getClassifyName());
        itemsInfoDao.setClassifyId(itemsInfoDto.getClassifyId());
        itemsInfoDao.setUnitId(itemsInfoDto.getUnitId());
        itemsInfoDao.setUnitName(itemsInfoDto.getUnitName());
        itemsInfoDao.setIntroduction(itemsInfoDto.getIntroduction());
        itemsInfoDao.setTradeType(itemsInfoDto.getTradeType());
        itemsInfoDao.setItemType(itemsInfoDto.getItemType());
        itemsInfoDao.setImage1(itemsInfoDto.getImage1());
        itemsInfoDao.setImage2(itemsInfoDto.getImage2());
        itemsInfoDao.setImage3(itemsInfoDto.getImage3());
        itemsInfoDao.setImage4(itemsInfoDto.getImage4());
        itemsInfoDao.setImage5(itemsInfoDto.getImage5());
        itemsInfoDao.setCompanyId(companyId);
        itemsInfoDao.setCreateId(user.getUserId());
        itemsInfoDao.setCreateName(user.getRealName());
        //多单位
        if(itemsInfoDto.getConversionRelDto()!=null){
            ConversionRel conversionRel=new ConversionRel();
            conversionRel.setConverId(itemsInfoDto.getConverId());
            conversionRel.setUnitId(itemsInfoDto.getConversionRelDto().getUnitId());
            conversionRel.setUnitName(itemsInfoDto.getConversionRelDto().getUnitName());
            conversionRel.setUnitId1(itemsInfoDto.getConversionRelDto().getUnitId1());
            conversionRel.setUnitName1(itemsInfoDto.getConversionRelDto().getUnitName1());
            conversionRel.setData1(itemsInfoDto.getConversionRelDto().getData1());
            conversionRel.setUnitId2(itemsInfoDto.getConversionRelDto().getUnitId2());
            conversionRel.setUnitName2(itemsInfoDto.getConversionRelDto().getUnitName2());
            conversionRel.setData2(itemsInfoDto.getConversionRelDto().getData2());
            conversionRel.setCreateId(user.getUserId());
            conversionRel.setCreateName(user.getRealName());
            conversionRel.setCompanyId(companyId);
            itemsInfoDao.setConversionRel(conversionRel);
        }
        //主商品自定义属性
        if (itemsInfoDto.getCustomValueList() != null) {
            List<CustomValue> customValueList = new ArrayList<CustomValue>();
            for (int i = 0; i < itemsInfoDto.getCustomValueList().size(); i++) {
                CustomValue customValue = new CustomValue();
                customValue.setVid(itemsInfoDto.getCustomValueList().get(i).getVid());
                customValue.setPropertyValue(itemsInfoDto.getCustomValueList().get(i).getPropertyValue());
                customValue.setPropertyName(itemsInfoDto.getCustomValueList().get(i).getPropertyName());
                customValue.setItemId(itemsInfoDto.getCustomValueList().get(i).getItemId());
                customValue.setCompanyId(companyId);
                customValue.setCreateId(user.getUserId());
                customValue.setCreateName(user.getRealName());
                customValueList.add(customValue);
            }
            itemsInfoDao.setCustomValueList(customValueList);
        }
        //子商品
        if (itemsInfoDto.getSubItemsInfoDtoList() != null) {
            List<SubItemsInfoDao> subItemsInfoDaoList = new ArrayList<SubItemsInfoDao>();
            for (int i = 0; i < itemsInfoDto.getSubItemsInfoDtoList().size(); i++) {
                SubItemsInfoDao subItemsInfoDao = new SubItemsInfoDao();
                subItemsInfoDao.setSubItemId(itemsInfoDto.getSubItemsInfoDtoList().get(i).getSubItemId());
                subItemsInfoDao.setItemId(itemsInfoDto.getSubItemsInfoDtoList().get(i).getItemId());
                subItemsInfoDao.setImage(itemsInfoDto.getSubItemsInfoDtoList().get(i).getImage());
                subItemsInfoDao.setCode(itemsInfoDto.getSubItemsInfoDtoList().get(i).getCode());
                subItemsInfoDao.setBarCode(itemsInfoDto.getSubItemsInfoDtoList().get(i).getBarCode());
                subItemsInfoDao.setPurchasePrice(itemsInfoDto.getSubItemsInfoDtoList().get(i).getPurchasePrice());
                subItemsInfoDao.setWholesalePrice(itemsInfoDto.getSubItemsInfoDtoList().get(i).getWholesalePrice());
                subItemsInfoDao.setRetailPrice(itemsInfoDto.getSubItemsInfoDtoList().get(i).getRetailPrice());
                subItemsInfoDao.setCompanyId(companyId);
                subItemsInfoDao.setCreateId(user.getUserId());
                subItemsInfoDao.setCreateName(user.getRealName());
                //子商品自定义属性
                if (itemsInfoDto.getSubItemsInfoDtoList().get(i).getCustomValueList() != null) {
                    List<CustomValue> subCustomValueList = new ArrayList<CustomValue>();
                    for (int j = 0; j < itemsInfoDto.getSubItemsInfoDtoList().get(i).getCustomValueList().size(); j++) {
                        CustomValue customValue = new CustomValue();
                        customValue.setVid(itemsInfoDto.getSubItemsInfoDtoList().get(i).getCustomValueList().get(j).getVid());
                        customValue.setPropertyValue(itemsInfoDto.getSubItemsInfoDtoList().get(i).getCustomValueList().get(j).getPropertyValue());
                        customValue.setPropertyName(itemsInfoDto.getSubItemsInfoDtoList().get(i).getCustomValueList().get(j).getPropertyName());
                        customValue.setSubItemId(itemsInfoDto.getSubItemsInfoDtoList().get(i).getCustomValueList().get(j).getSubItemId());
                        customValue.setCompanyId(companyId);
                        customValue.setCreateId(user.getUserId());
                        customValue.setCreateName(user.getRealName());
                        subCustomValueList.add(customValue);

                    }
                    subItemsInfoDao.setCustomValueList(subCustomValueList);
                }
                //子商品规格
                if (itemsInfoDto.getSubItemsInfoDtoList().get(i).getItemSpecKeyValueDtoList() != null) {
                    List<ItemSpecKeyValue> itemSpecKeyValueList = new ArrayList<ItemSpecKeyValue>();
                    for (int j = 0; j < itemsInfoDto.getSubItemsInfoDtoList().get(i).getItemSpecKeyValueDtoList().size(); j++) {
                        ItemSpecKeyValue itemSpecKeyValue = new ItemSpecKeyValue();
                        itemSpecKeyValue.setSpvId(itemsInfoDto.getSubItemsInfoDtoList().get(i).getItemSpecKeyValueDtoList().get(j).getSpvId());
                        itemSpecKeyValue.setSpValue(itemsInfoDto.getSubItemsInfoDtoList().get(i).getItemSpecKeyValueDtoList().get(j).getSpValue());
                        itemSpecKeyValue.setSpName(itemsInfoDto.getSubItemsInfoDtoList().get(i).getItemSpecKeyValueDtoList().get(j).getSpName());
                        itemSpecKeyValue.setCompanyId(companyId);
                        itemSpecKeyValue.setCreateId(user.getUserId());
                        itemSpecKeyValue.setCreateName(user.getRealName());
                        itemSpecKeyValueList.add(itemSpecKeyValue);
                    }
                    subItemsInfoDao.setItemSpecKeyValueList(itemSpecKeyValueList);
                }
                subItemsInfoDaoList.add(subItemsInfoDao);
            }
            itemsInfoDao.setSubItemsInfoDaoList(subItemsInfoDaoList);
        }
        return itemsInfoDao;
    }
}
