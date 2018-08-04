package com.lcdt.warehouse.service;

import java.util.List;

import com.lcdt.warehouse.dto.PageBaseDto;
import com.lcdt.warehouse.dto.TransferInventoryListDTO;
import com.lcdt.warehouse.dto.TransferListDTO;
import com.lcdt.warehouse.entity.TransferInventoryListDO;

public interface TransferInventoryListService {
	
	int insertTransferInventoryList(TransferInventoryListDTO transferInventoryListDTO);
	
	int completeTransferInventoryList(TransferInventoryListDTO transferInventoryListDTO);
	
	PageBaseDto<TransferInventoryListDTO> getTransferInventoryListDTOList(TransferListDTO transferListDTO);
	
	TransferInventoryListDTO getTransferInventoryListDTODetail(Long transferInventoryListId);
	
	int updateTransferStatus(Long transferInventoryListId);
	
	
	
	
	

}
