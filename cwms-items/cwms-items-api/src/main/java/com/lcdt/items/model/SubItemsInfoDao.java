package com.lcdt.items.model;

import java.util.List;

/**
 * Created by lyqishan on 2017/11/28
 */

public class SubItemsInfoDao extends SubItemsInfo {
    List<CustomValue> customValueList;
    List<ItemSpecKeyValue> itemSpecKeyValueList;
    public List<CustomValue> getCustomValueList() {
        return customValueList;
    }

    public void setCustomValueList(List<CustomValue> customValueList) {
        this.customValueList = customValueList;
    }

    public List<ItemSpecKeyValue> getItemSpecKeyValueList() {
        return itemSpecKeyValueList;
    }

    public void setItemSpecKeyValueList(List<ItemSpecKeyValue> itemSpecKeyValueList) {
        this.itemSpecKeyValueList = itemSpecKeyValueList;
    }
}
