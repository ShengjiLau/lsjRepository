package com.lcdt.traffic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.dao.FeeExchangeMapper;
import com.lcdt.traffic.model.FeeExchange;
import com.lcdt.traffic.service.FeeExchangeService;
import com.lcdt.traffic.web.dto.FeeExchangeDto;


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
	
	
	
	@Override
	public int insertFeeExchangeByBatch(List<FeeExchange> feeExchangeList) {
		for(FeeExchange fe:feeExchangeList) {
			fe.setCompanyId(SecurityInfoGetter.getCompanyId());
			fe.setOperateId(SecurityInfoGetter.getUser().getUserId());
			fe.setOperateName(SecurityInfoGetter.getUser().getRealName());
			fe.setCancelOk((short) 0);//生成对账单时取消状态设置为0不取消
		}
		return feeExchangeMapper.insertByBatch(feeExchangeList);
	}


	/**
	 * 通过相应的条件查询收付款记录
	 */
	@Override
	public PageInfo<FeeExchange> getFeeExchangeList(FeeExchangeDto feeExchangeDto) {
		if(feeExchangeDto.getPageNo()<1) {
			feeExchangeDto.setPageNo(1);
		}
		if(feeExchangeDto.getPageSize()<0) {
			feeExchangeDto.setPageSize(0);
		}
		feeExchangeDto.setCompanyId(SecurityInfoGetter.getCompanyId());
		feeExchangeDto.setCancelOk((short) 0);
		PageHelper.startPage(feeExchangeDto.getPageNo(),feeExchangeDto.getPageSize());
		List<FeeExchange> feeExchangeList =feeExchangeMapper.getFeeExchangeListByCondition(feeExchangeDto);
		PageInfo<FeeExchange> page =new PageInfo<FeeExchange>(feeExchangeList);	
			
		return page;
	}


	@Override
	public int updateSetCancelOk(Long[] feeExchangeIds) {
		int j = feeExchangeIds.length;
		int i= feeExchangeMapper.updateCancelOkByBatch(feeExchangeIds);
		if(j==i) {
			return i;
		}
		 return -1;
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
