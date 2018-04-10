package com.lcdt.traffic.service.impl;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcdt.traffic.dao.PayeeMapper;
import com.lcdt.traffic.dao.ReconcileMapper;
import com.lcdt.traffic.model.Payee;
import com.lcdt.traffic.model.Reconcile;
import com.lcdt.traffic.service.ReconcileService;
import com.lcdt.traffic.web.dto.ReconcileDto;
import com.lcdt.traffic.web.dto.ReconcileListDto;

/**
 * @author Sheng-ji Lau
 * @date 2018年4月9日下午4:02:24
 * @version
 */
@Service
public class ReconcileServiceImpl implements ReconcileService {
	
	@Autowired
	private ReconcileMapper reconcileMapper;
	
	@Autowired
	private PayeeMapper payeeMapper;

	@Override
	public int insertReconcileBatch(ReconcileListDto reconcileListDto) {
		
	List<ReconcileDto> reconcileDtoList=reconcileListDto.getReconcileList();
//	List<Reconcile> reconcileList =new ArrayList<Reconcile>();
	List<Payee> payeeLists =new ArrayList<Payee>();
//		for(ReconcileDto reconcileDto:reconcileDtoList) {
//			Reconcile reconcile =new Reconcile();
//			BeanUtils.copyProperties(reconcileDto, reconcile);
//			reconcileList.add(reconcile);		
//		}	
		int result=reconcileMapper.insertByBatch(reconcileDtoList);
		int i=0;
		for(ReconcileDto reconcileDto:reconcileDtoList) {
			List<Payee> payeeList =reconcileDto.getPayeeList();
			for(Payee payee :payeeList) {
				payee.setReconcileId(reconcileDto.getReconcileId());
			}
			payeeLists.addAll(payeeList);
		}
		i+=payeeLists.size();
		int j=payeeMapper.insertPayeeByBatch(payeeLists);
		if(i==j) {
			return result;
		}else {
			return -1;
		}
		
		
	}

	@Override
	public int setCancelOk(List<Long> reconcileIdList) {
		
		return reconcileMapper.cancelByBatch(reconcileIdList);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
