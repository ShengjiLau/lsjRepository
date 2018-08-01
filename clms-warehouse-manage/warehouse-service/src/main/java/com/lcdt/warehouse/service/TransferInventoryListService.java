package com.lcdt.warehouse.service;

import java.util.List;

import com.lcdt.warehouse.dto.TransferInventoryListDTO;

public interface TransferInventoryListService {
	
	int insertTransferInventoryList(TransferInventoryListDTO transferInventoryListDTO);
	
	int completeTransferInventoryList(TransferInventoryListDTO transferInventoryListDTO);
	
	List<TransferInventoryListDTO> getTransferInventoryListDTOList(TransferInventoryListDTO transferInventoryListDTO);
	
	TransferInventoryListDTO getTransferInventoryListDTODetail(Long transferInventoryListId);
	
	
	
	
	
	
	

}
