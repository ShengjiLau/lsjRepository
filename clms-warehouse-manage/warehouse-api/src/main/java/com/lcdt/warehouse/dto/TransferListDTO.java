package com.lcdt.warehouse.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Sheng-ji Lau
 * @date 2018年8月2日
 * @version
 * @Description: TODO 
 */
@Setter
@Getter
@ApiModel("查询列表时参数DTO")
public class TransferListDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 115614586666L;
	
	@ApiModelProperty("所属公司id")
	private Long companyId;
	@ApiModelProperty("客户名称，可模糊")
	private String customName;
	@ApiModelProperty("消耗商品的名称/编码/条码")
	private String materialProduct;
	@ApiModelProperty("生成商品的名称/编码/条码")
	private String finishedProduct;
	@ApiModelProperty("每页条目数")
	private Integer pageSize;
	@ApiModelProperty("页码")
	private Integer pageNo;

	

}
