package com.lcdt.traffic.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.dao.FeeExchangeMapper;
import com.lcdt.traffic.dao.ReconcileMapper;
import com.lcdt.traffic.model.FeeExchange;
import com.lcdt.traffic.model.Reconcile;
import com.lcdt.traffic.service.FeeExchangeService;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.traffic.web.dto.FeeExchangeDto;
import com.lcdt.traffic.web.dto.FeeExchangeListDto;
import com.lcdt.traffic.web.dto.PageBaseDto;


/**
 * @author Sheng-ji Lau
 * @date 2018年4月17日
 * @version
 * @Description: TODO 
 */
@Service
public class FeeExchangeImpl implements FeeExchangeService {

	@Autowired
	private FeeExchangeMapper feeExchangeMapper;
	
	@Autowired
	private ReconcileMapper reconcileMapper;
	
	@Override
	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, timeout = 30, rollbackForClassName = {"RuntimeException","Exception"})
	public int insertFeeExchangeByBatch(FeeExchangeListDto feeExchangeListDto) {
		Reconcile reconcile = reconcileMapper.selectByPrimaryKey(feeExchangeListDto.getReconcileId());	
		for(FeeExchange fe : feeExchangeListDto.getFeeExchangeList()) {
			fe.setReconcileCode(reconcile.getReconcileCode());
			fe.setReconcileId(feeExchangeListDto.getReconcileId());
			fe.setType(feeExchangeListDto.getType());
			fe.setCompanyId(SecurityInfoGetter.getCompanyId());
			fe.setOperateId(SecurityInfoGetter.getUser().getUserId());
			fe.setOperateName(SecurityInfoGetter.getUser().getRealName());
			fe.setCancelOk(ConstantVO.NORMAL_STATUS);//生成对账单时取消状态设置为0不取消
			fe.setCreateTime(new Date());
		}
		return feeExchangeMapper.insertByBatch(feeExchangeListDto.getFeeExchangeList());
	}


	/**
	 * 通过相应的条件查询收付款记录
	 */
	@Override
	@Transactional(readOnly = true)
	public PageBaseDto<FeeExchange> getFeeExchangeList(FeeExchangeDto feeExchangeDto) {
		if(feeExchangeDto.getPageNo() < 1) {
			feeExchangeDto.setPageNo(ConstantVO.PAGE_NUM);
		}
		if(feeExchangeDto.getPageSize() < 0) {
			feeExchangeDto.setPageSize(ConstantVO.PAGE_SIZE);
		}
		feeExchangeDto.setCompanyId(SecurityInfoGetter.getCompanyId());
		feeExchangeDto.setCancelOk(ConstantVO.NO_CANCEL);
		PageHelper.startPage(feeExchangeDto.getPageNo(), feeExchangeDto.getPageSize());
		List<FeeExchange> feeExchangeList = feeExchangeMapper.getFeeExchangeListByCondition(feeExchangeDto);
		PageInfo<FeeExchange> page = new PageInfo<FeeExchange>(feeExchangeList);	
		PageBaseDto<FeeExchange> pageBaseDto = new PageBaseDto<FeeExchange>();
		pageBaseDto.setTotal(page.getTotal());
		pageBaseDto.setList(page.getList());
			
		return pageBaseDto;
	}


	@Override
	public int updateSetCancelOk(String feeExchangeIds) {
		String[] ss = feeExchangeIds.split(",");		
		int j = ss.length;
		Long [] Ids = new Long[ss.length];
		for(int i = 0; i < ss.length; i++) {
			Ids[i] = Long.valueOf(ss[i]);
		}
		int i = feeExchangeMapper.updateCancelOkByBatch(Ids);
		if(j == i) {
			return i;
		}
		 return ConstantVO.EXCEPTION_VALUE;
	}


	@Override
	public FeeExchange selectFeeExchangeById(Long feeExchangeId) {		
		return feeExchangeMapper.selectByPrimaryKey(feeExchangeId);
	}


	@Override
	public int querySumFeeExchangeByReconcileId(Long reconcileId) {
		return feeExchangeMapper.selectCountFeeExchangeByReconcileId(reconcileId);
	}
}
