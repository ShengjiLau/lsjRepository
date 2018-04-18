package com.lcdt.traffic.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.model.FeeExchange;
import com.lcdt.traffic.web.dto.FeeExchangeDto;

/**
 * @author Sheng-ji Lau
 * @date 2018年4月17日
 * @version
 * @Description: TODO 
 */
public interface FeeExchangeService {
	
	int insertFeeExchangeByBatch(List<FeeExchange> feeExchangeList);
	
	PageInfo<FeeExchange> getFeeExchangeList(FeeExchangeDto feeExchangeDto);
	
	int updateSetCancelOk(Long [] feeExchangeIds);
	
	FeeExchange selectFeeExchangeById(Long feeExchangeId);
	
	int querySumFeeExchangeByReconcileId(Long rreconcileId);

}
