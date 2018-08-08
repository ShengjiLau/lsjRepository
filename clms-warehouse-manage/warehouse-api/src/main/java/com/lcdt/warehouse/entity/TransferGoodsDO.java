package com.lcdt.warehouse.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("转换单商品Model")
@Data
public class TransferGoodsDO implements Serializable {
	@ApiModelProperty("转换单商品id")
    private Long goodsId;
	@ApiModelProperty("源商品id")
    private Long originGoodsId;
	@ApiModelProperty("库存id")
    private Long inventoryId;
	@ApiModelProperty("转换数量")
    private BigDecimal transferNum;
	@ApiModelProperty("备注")
    private String remark;
	@ApiModelProperty("转换单主表id")
    private Long transferId;
	@ApiModelProperty("库位id")
    private Long whLocId;
	@ApiModelProperty("库位编码")
    private String whLocCode;
	@ApiModelProperty("商品批次")
    private String goodsBatch;
	@ApiModelProperty("0-原料；1-生成")
    private Byte isMaterial;
	@ApiModelProperty("商品名称")
    private String goodsName;
	@ApiModelProperty("商品编码")
    private String goodsCode;
	@ApiModelProperty("商品条形码")
    private String goodsBarcode;
	@ApiModelProperty("商品规格")
    private String goodsSpec;
	@ApiModelProperty("商品单位")
    private String goodsUnit;

    private static final long serialVersionUID = 1154156156156L;

   
}