package com.lcdt.warehouse.dto;

import com.fasterxml.jackson.annotation.JsonValue;


/**
 * Created by yangbinq on 2018/5/9.
 * Description：出库计划状态
 */
public enum OutWhPlanStatusEnum implements IEnum {
    watting(10,"待发布"),
    publish(20,"配仓中"),
    isWarehouse(30,"已配仓"),
    completed(40,"完成"),
    cancel(50,"取消");

    private int value;
    private String desc;

    OutWhPlanStatusEnum(final int value, final String desc) {
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
