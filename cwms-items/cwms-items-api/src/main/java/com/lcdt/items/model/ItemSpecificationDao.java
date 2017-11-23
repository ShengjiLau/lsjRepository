package com.lcdt.items.model;

import com.lcdt.items.dto.ItemSpecKeyDto;

import java.util.List;

/**
 * Created by lyqishan on 2017/11/22
 */

public class ItemSpecificationDao extends ItemSpecKey{
    List<ItemSpecValue> itemSpecValueList;

    public List<ItemSpecValue> getItemSpecValueList() {
        return itemSpecValueList;
    }

    public void setItemSpecValueList(List<ItemSpecValue> itemSpecValueList) {
        this.itemSpecValueList = itemSpecValueList;
    }
}
