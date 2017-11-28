package com.lcdt.items.model;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017-11-13
 */
public class ItemsInfoDao extends ItemsInfo {
    private String classifyIds;   //商品分类id串，英文逗号分隔

    public String getClassifyIds() {
        return classifyIds;
    }

    public void setClassifyIds(String classifyIds) {
        this.classifyIds = classifyIds;
    }

    List<SubItemsInfoDao> subItemsInfoDaoList;

    List<CustomValue> customValueList;

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
}
