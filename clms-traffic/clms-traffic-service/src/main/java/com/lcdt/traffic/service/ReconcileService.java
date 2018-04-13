package com.lcdt.traffic.service;



import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.model.Reconcile;
import com.lcdt.traffic.web.dto.ReconcileDto;
import com.lcdt.traffic.web.dto.ReconcileListDto;

/**
 * @author Sheng-ji Lau
 * @date 2018年4月9日下午4:02:29
 * @version
 */
public interface ReconcileService {
	
	public int insertReconcileBatch(ReconcileListDto reconcileListDto);
	
	public int setCancelOk(Long[] reconcileIdList);
	
	PageInfo<Reconcile> getReconcileList(ReconcileDto reconcileDto);
	
	Reconcile selectReconcileByPk(Long pk);
	

}
