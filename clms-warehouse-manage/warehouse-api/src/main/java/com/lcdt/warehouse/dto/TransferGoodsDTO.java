package com.lcdt.warehouse.dto;

import com.lcdt.warehouse.entity.TransferGoodsDO;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Sheng-ji Lau
 * @date 2018年8月1日
 * @version
 * @Description: TODO 
 */
@Setter
@Getter
public class TransferGoodsDTO extends TransferGoodsDO{

	private String goodsBatch;
	
	private Long whLocId;
	
	private String whLocCode;
}
