package com.lcdt.items.model;

import java.util.List;

/**
 * Created by lyqishan on 2017/11/16
 */

public class ItemClassifyDao extends ItemClassify{

    //子分类
    private List<ItemClassify> list;

    public List<ItemClassify> getList() {
        return list;
    }

    public void setList(List<ItemClassify> list) {
        this.list = list;
    }
}
