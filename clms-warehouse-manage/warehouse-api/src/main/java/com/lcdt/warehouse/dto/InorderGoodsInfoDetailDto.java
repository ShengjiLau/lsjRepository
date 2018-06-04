package com.lcdt.warehouse.dto;

import java.util.List;

/**
 * Created by lyqishan on 2018/6/4
 */

public class InorderGoodsInfoDetailDto {
    private Long inorderId;
    private Long inplanGoodsId;
    private Long goodsId;
    private String goodsName;
    /**
     * 商品分类
     */
    private String goodsClassify;
    private String goodsSpec;
    private String goodsCode;
    private String goodsBarcode;
    /**
     * 库存单价
     */
    private Float goodsPrice;
    /**
     * 商品批次
     */
    private String goodsBatch;
    /**
     * 最小单位
     */
    private String minUnit;
    /**
     * 单位
     */
    private String unit;
    /**
     * 换算关系
     */
    private Integer unitData;

    /**
     * 应收数量
     */
    private Float receivalbeAmount;

    private List<InorderGoodsInfoLocationDto> locationInfo;

}
