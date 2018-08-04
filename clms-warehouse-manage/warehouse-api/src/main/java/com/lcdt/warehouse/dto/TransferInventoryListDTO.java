package com.lcdt.warehouse.dto;

import java.io.Serializable;
import java.util.List;

import com.lcdt.warehouse.entity.TransferGoodsDO;
import com.lcdt.warehouse.entity.TransferInventoryListDO;

import io.swagger.annotations.ApiModelProperty;
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
public class TransferInventoryListDTO extends TransferInventoryListDO implements Serializable{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1515611551L;
	
	@ApiModelProperty("库存转换单商品集合")
	private List<TransferGoodsDO> transferGoodsDOList;
	

}
