package com.lcdt.userinfo.dto;

import com.lcdt.userinfo.model.Dictionary;

/**
 * Created by Administrator on 2018/6/19.
 */
public class DictionaryQueryDto {


    private Dictionary dictionary;

    private Integer pageNo;

    private Integer pageSize;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }
}
