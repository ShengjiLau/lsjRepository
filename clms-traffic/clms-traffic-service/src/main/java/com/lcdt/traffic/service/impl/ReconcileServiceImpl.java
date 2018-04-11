package com.lcdt.traffic.service.impl;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.dao.ReconcileMapper;
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
	
	/**
	 * 插入对账单
	 */
	@Override
	public int insertReconcileBatch(ReconcileListDto reconcileListDto) {
		
	List<Reconcile> reconcileDtoList=reconcileListDto.getReconcileList();
	
//	reconcileDtoList.forEach(x->x.setCompanyId(SecurityInfoGetter.getCompanyId()));
//	reconcileDtoList.forEach(x->x.setOperatorName(SecurityInfoGetter.getUser().getRealName()));
//	reconcileDtoList.forEach(x->x.setOperatorId(SecurityInfoGetter.getUser().getUserId()));
	//添加创建人id,创建人名字,公司id
	for(Reconcile reconcile:reconcileDtoList) {
		reconcile.setCompanyId(SecurityInfoGetter.getCompanyId());
		reconcile.setOperatorName(SecurityInfoGetter.getUser().getRealName());
		reconcile.setOperatorId(SecurityInfoGetter.getUser().getUserId());
	}
	int result=reconcileMapper.insertByBatch(reconcileDtoList);
		
	return result;	
	}

	
	
	
	/**
	 * 取消对账单
	 */
	@Override
	public int setCancelOk(Long[] reconcileIdList) {
		
		return reconcileMapper.cancelByBatch(reconcileIdList);
	}
	
	
	/**
	 * 查询对账单列表
	 */
	public PageInfo<Reconcile> getReconcileList(ReconcileDto reconcileDto){
		if(reconcileDto.getPageNum()<1) {
			reconcileDto.setPageNum(1);
		}
		if(reconcileDto.getPageSize()<0) {
			reconcileDto.setPageSize(0);
		}
		reconcileDto.setCompanyId(SecurityInfoGetter.getCompanyId());
		PageHelper.startPage(reconcileDto.getPageNum(),reconcileDto.getPageSize());
		List<Reconcile> reconcileList= reconcileMapper.getReconcileList(reconcileDto);
		PageInfo<Reconcile> page =new PageInfo<Reconcile>(reconcileList);
		return page;
	}
	
	
	
	
	
	
	
	
	

}
