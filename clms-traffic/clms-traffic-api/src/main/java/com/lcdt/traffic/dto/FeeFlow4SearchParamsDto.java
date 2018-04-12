package com.lcdt.traffic.dto;

import com.lcdt.converter.ResponseData;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by yangbinq on 2018/4/12.
 * Description:应收、应付列表查询参数
 */
public class FeeFlow4SearchParamsDto implements java.io.Serializable {


    @ApiModelProperty(value = "流水类型-0应收、1-应付")
    private Short isReceivable;


    public Short getIsReceivable() {
        return isReceivable;
    }

    public void setIsReceivable(Short isReceivable) {
        this.isReceivable = isReceivable;
    }
}
