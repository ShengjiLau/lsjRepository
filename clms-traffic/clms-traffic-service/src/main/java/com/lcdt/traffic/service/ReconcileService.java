package com.lcdt.traffic.service;



import java.util.List;

import com.lcdt.traffic.web.dto.ReconcileListDto;

/**
 * @author Sheng-ji Lau
 * @date 2018年4月9日下午4:02:29
 * @version
 */
public interface ReconcileService {
	
	public int insertReconcileBatch(ReconcileListDto reconcileListDto);
	
	public int setCancelOk(List<Long> reconcileIdList);
	

}
