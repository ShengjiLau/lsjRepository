package com.lcdt.traffic.web.dto;

import java.io.Serializable;
import java.util.List;

import com.lcdt.traffic.model.Payee;
import com.lcdt.traffic.model.Reconcile;

/**
 * @author Sheng-ji Lau
 * @date 2018年4月9日下午4:02:18
 * @version
 */
public class ReconcileDto extends Reconcile implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4584651511515L;
	
	
	private List<Payee> payeeList;
	
	private String beginTime;
	
	private String endTime;
	
	private String payerName;

	
	public List<Payee> getPayeeList() {
		return payeeList;
	}


	public void setPayeeList(List<Payee> payeeList) {
		this.payeeList = payeeList;
	}


	public String getBeginTime() {
		return beginTime;
	}


	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	public String getPayerName() {
		return payerName;
	}


	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
