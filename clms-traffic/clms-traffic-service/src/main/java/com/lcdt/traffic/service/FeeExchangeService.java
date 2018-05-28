package com.lcdt.traffic.service;



import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.model.FeeExchange;
import com.lcdt.traffic.web.dto.FeeExchangeDto;
import com.lcdt.traffic.web.dto.FeeExchangeListDto;

/**
 * @author Sheng-ji Lau
 * @date 2018年4月17日
 * @version
 * @Description: TODO 
 */
public interface FeeExchangeService {
	
	int insertFeeExchangeByBatch(FeeExchangeListDto feeExchangeListDto);
	
	PageInfo<FeeExchange> getFeeExchangeList(FeeExchangeDto feeExchangeDto);
	
	int updateSetCancelOk(String feeExchangeIds);
	
	FeeExchange selectFeeExchangeById(Long feeExchangeId);
	
	int querySumFeeExchangeByReconcileId(Long rreconcileId);

}
