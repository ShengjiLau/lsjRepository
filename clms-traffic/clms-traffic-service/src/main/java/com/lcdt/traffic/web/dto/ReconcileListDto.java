package com.lcdt.traffic.web.dto;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.lcdt.traffic.model.Reconcile;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Sheng-ji Lau
 * @date 2018年4月9日上午10:24:07
 * @version
 */
public class ReconcileListDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -749151515156L;

	@ApiModelProperty("批量对账单")
	@NotEmpty(message="请添加至少一条对账单信息")
	private List<Reconcile> reconcileList;

	public List<Reconcile> getReconcileList() {
		return reconcileList;
	}

	public void setReconcileList(List<Reconcile> reconcileList) {
		this.reconcileList = reconcileList;
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
