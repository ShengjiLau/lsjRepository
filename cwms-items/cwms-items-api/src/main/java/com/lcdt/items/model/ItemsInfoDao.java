package com.lcdt.items.model;

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
}
