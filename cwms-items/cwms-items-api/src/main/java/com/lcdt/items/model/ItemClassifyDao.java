package com.lcdt.items.model;

import java.util.List;

/**
 * Created by lyqishan on 2017/11/16
 */

public class ItemClassifyDao extends ItemClassify{

    //子分类
    private List<ItemClassify> childClassifyList;

    public List<ItemClassify> getChildClassifyList() {
        return childClassifyList;
    }

    public void setChildClassifyList(List<ItemClassify> childClassifyList) {
        this.childClassifyList = childClassifyList;
    }
}
