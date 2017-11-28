package com.lcdt.items.web.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lyqishan on 2017/11/28
 */

public class ItemsInfoModifyDto extends ItemsInfoAddDto{
    @ApiModelProperty(value = "商品id", required = true)
    private Long itemId;
}
