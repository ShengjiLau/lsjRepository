package com.lcdt.items.model;

import com.lcdt.converter.ResponseData;

import java.io.Serializable;
import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017-11-13
 */
public class ItemsInfoDao extends ItemsInfo{
    private String classifyIds;   //商品分类id串，英文逗号分隔

    private List<SubItemsInfoDao> subItemsInfoDaoList;

    private List<CustomValue> customValueList;

    private ConversionRel conversionRel;

    private List<ItemClassify> itemClassifyList;

    public String getClassifyIds() {
        return classifyIds;
    }

    public void setClassifyIds(String classifyIds) {
        this.classifyIds = classifyIds;
    }

    public List<SubItemsInfoDao> getSubItemsInfoDaoList() {
        return subItemsInfoDaoList;
    }

    public void setSubItemsInfoDaoList(List<SubItemsInfoDao> subItemsInfoDaoList) {
        this.subItemsInfoDaoList = subItemsInfoDaoList;
    }

    public List<CustomValue> getCustomValueList() {
        return customValueList;
    }

    public void setCustomValueList(List<CustomValue> customValueList) {
        this.customValueList = customValueList;
    }

    public ConversionRel getConversionRel() {
        return conversionRel;
    }

    public void setConversionRel(ConversionRel conversionRel) {
        this.conversionRel = conversionRel;
    }

    public List<ItemClassify> getItemClassifyList() {
        return itemClassifyList;
    }

    public void setItemClassifyList(List<ItemClassify> itemClassifyList) {
        this.itemClassifyList = itemClassifyList;
    }
}
