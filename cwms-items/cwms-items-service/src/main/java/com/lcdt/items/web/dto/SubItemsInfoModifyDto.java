package com.lcdt.items.web.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lyqishan on 2017/11/28
 */

public class SubItemsInfoModifyDto extends SubItemsInfoAddDto{
    @ApiModelProperty(value = "子商品id", required = true)
    private Long subItemId;
}
