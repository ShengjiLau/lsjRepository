package com.lcdt.warehouse.dto;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by Administrator on 2018/6/15.
 */
public enum CheckDiffStatusEnum implements  IEnum{
    same(1,"待盘库"),
    different(2,"完成");

    private Integer value;
    private String desc;

    CheckDiffStatusEnum(final Integer value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @JsonValue
    public String getDesc(){
        return this.desc;
    }
}
