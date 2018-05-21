package com.lcdt.warehouse.dto;

import java.util.List;

import com.lcdt.warehouse.entity.ShiftGoodsDO;
import com.lcdt.warehouse.entity.ShiftInventoryListDO;

/**
 * @author Sheng-ji Lau
 * @date 2018年5月18日
 * @version
 * @Description: TODO 
 */
public class ShiftInventoryListDTO extends ShiftInventoryListDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 110151561515L;
	
	private List<ShiftGoodsListDTO> shiftGoodsListDTOList;

	public List<ShiftGoodsListDTO> getShiftGoodsListDTOList() {
		return shiftGoodsListDTOList;
	}

	public void setShiftGoodsListDTOList(List<ShiftGoodsListDTO> shiftGoodsListDTOList) {
		this.shiftGoodsListDTOList = shiftGoodsListDTOList;
	}
	
	
	
	
	
	

}
