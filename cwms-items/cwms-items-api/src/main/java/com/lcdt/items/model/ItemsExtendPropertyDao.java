package com.lcdt.items.model;

/**
 * 商品扩展属性扩展实体（用来解决查询更新可能的重复条件，比如更新name字段，同样name又作为了一个条件 ）
 */
public class ItemsExtendPropertyDao extends ItemsExtendProperty{

    private String titleExtend;

    public String getTitleExtend() {
        return titleExtend;
    }

    public void setTitleExtend(String titleExtend) {
        this.titleExtend = titleExtend;
    }
}