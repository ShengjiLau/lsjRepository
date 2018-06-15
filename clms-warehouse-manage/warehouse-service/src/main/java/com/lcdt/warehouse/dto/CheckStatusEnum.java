package com.lcdt.warehouse.dto;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by Administrator on 2018/6/15.
 */
public enum  CheckStatusEnum implements IEnum {
    watting(1,"待盘库"),
    completed(5,"完成"),
    cancel(9,"取消");

    private Integer value;
    private String desc;

    CheckStatusEnum(final Integer value, final String desc) {
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
