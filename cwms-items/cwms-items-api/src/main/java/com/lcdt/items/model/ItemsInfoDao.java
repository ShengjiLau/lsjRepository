package com.lcdt.items.model;

/**
 * @AUTHOR liuh
 * @DATE 2017-11-13
 */
public class ItemsInfoDao extends ItemsInfo {
    private String classifys;   //商品分类id串，英文逗号分隔

    public String getClassifys() {
        return classifys;
    }

    public void setClassifys(String classifys) {
        this.classifys = classifys;
    }
}
