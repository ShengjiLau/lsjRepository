package com.lcdt.traffic.service;



import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.model.Reconcile;
import com.lcdt.traffic.web.dto.ReconcileDto;

/**
 * @author Sheng-ji Lau
 * @date 2018年4月9日下午4:02:29
 * @version
 */
public interface ReconcileService {
	
	public int insertReconcileBatch(List<Reconcile> reconcileList);
	
	public Map<Integer,String> setCancelOk(String reconcileIdList);
	
	PageInfo<ReconcileDto> getReconcileList(ReconcileDto reconcileDto);
	
	ReconcileDto selectReconcileByPk(Long pk);
	

}
