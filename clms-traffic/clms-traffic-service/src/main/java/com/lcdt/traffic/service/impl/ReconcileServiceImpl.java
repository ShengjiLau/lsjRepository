package com.lcdt.traffic.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.dao.FeeAccountMapper;
import com.lcdt.traffic.dao.FeeExchangeMapper;
import com.lcdt.traffic.dao.ReconcileMapper;
import com.lcdt.traffic.model.FeeAccount;
import com.lcdt.traffic.model.FeeExchange;
import com.lcdt.traffic.model.Reconcile;
import com.lcdt.traffic.service.ReconcileService;
import com.lcdt.traffic.util.ConvertStringAndLong;
import com.lcdt.traffic.web.dto.ReconcileDto;




/**
 * @author Sheng-ji Lau
 * @date 2018年4月9日下午4:02:24
 * @version
 */
@Service
public class ReconcileServiceImpl implements ReconcileService {
	
	Logger logger = LoggerFactory.getLogger(ReconcileServiceImpl.class);
	
	@Autowired
	private ReconcileMapper reconcileMapper;
	
	@Autowired
	private FeeAccountMapper feeAccountMapper;
	
	@Autowired
	private FeeExchangeMapper feeExchangeMapper;
	
	/**
	 * 插入对账单
	 * 先将对账单的集合执行批量插入操作
	 * 再获取到插入后数据库生成的对账单ReconcileId和ReconcileCode
	 * 将相应的Reconcile的主键id和reconcileCode并将其添加到对应的记账单中
	 */
	@Override
	@Transactional
	public int insertReconcileBatch(List<Reconcile> reconcileList) {
		
	    List<FeeAccount> feeAccountList = new ArrayList<FeeAccount>();
	    for(Reconcile fa:reconcileList) {
		//添加对账单的所属公司id 操作人id 操作人姓名 默认状态
		    fa.setCompanyId(SecurityInfoGetter.getCompanyId());
		    fa.setOperatorId(SecurityInfoGetter.getUser().getUserId());
		    fa.setOperatorName(SecurityInfoGetter.getUser().getRealName());
		    fa.setCancelOk((short) 0);//生成对账单时取消状态设置为0不取消
	    }
	    //批量插入
	    int result = reconcileMapper.insertByBatch(reconcileList);
	
	    for(Reconcile reconcile:reconcileList) {
		    Reconcile rec = reconcileMapper.selectByPrimaryKey(reconcile.getReconcileId());
		    //得到记账单id
		    Long[] acLongId = ConvertStringAndLong.convertStrToLong(rec.getAccountId());
		    for(Long l:acLongId) {
			//创建一个记账单 依次放入每条记账单id以及对应的对账单id和对账单号
			    FeeAccount fa = new FeeAccount();
			    fa.setAccountId(l);
			    fa.setReconcileId(rec.getReconcileId());
			    fa.setReconcileCode(rec.getReconcileCode());
			    feeAccountList.add(fa);
		    }	
	    }
	    int i = feeAccountList.size();
	    logger.debug("应添加记账单ReconcileId数量:"+feeAccountList.size());
	    //批量修改记账单对应的对账单id 和 对账单单号
	    int j = feeAccountMapper.updateReconcileByBatch(feeAccountList);
	    logger.debug("实际添加记账单ReconcileId数量:"+j);
	    logger.debug("插入对账单数量为:"+result);
	    //一下判断既可判断对应的记账单是否全部被修改,又可判断是否存在对应的记账单id
	    if (i == j) {
	    	return result;	
	    }else {
	    	return -1;
	    }
		}

	/**
	 * 取消对账单
	 * 批量取消对账单时也要批量修改相关记账单中的对账单id和reconcileCode,将其设置为空
	 */
	@Override
	@Transactional
	public Map<Integer, String> setCancelOk(String reconcileIds) {
		StringBuilder sb1 = new StringBuilder();
		Map<Integer, String> map = new HashMap<Integer, String>();
		StringBuilder sb2 = new StringBuilder();
		StringBuilder sb3 = new StringBuilder();
		
		Long[] reconcileIdArray = ConvertStringAndLong.convertStrToLong(reconcileIds);
		int q1 = 0;
		int q2 = 0;
		int w1 = 0;
		int w2 = 0;
		for(int i = 0;i < reconcileIdArray.length;i++) {
			int a = feeExchangeMapper.selectCountFeeExchangeByReconcileId(reconcileIdArray[i]);
			if (a > 0) {
				//将已存在收付款记录的对账单id拼接成字符串
				sb1.append(reconcileIdArray[i]);sb1.append(",");
			}else {
				q2 ++;
				//将不存在收付款记录的对账单id拼接成字符串,dao层执行update set in操作
				sb2.append(reconcileIdArray[i]);sb2.append(",");
			}
		}
			
		if(q2>0) {
			q1 = reconcileMapper.cancelByBatch(ConvertStringAndLong.convertStrToLong(sb2.toString()));
			//获取不存在收付款记录的对账单信息列表
			List<Reconcile> reconcileList = reconcileMapper.getReconcileListByPk(ConvertStringAndLong.convertStrToLong(sb2.toString()));
			for(Reconcile reconcile:reconcileList) {
				//得到所有需要修改的被取消的对账当单对应的记账单进行拼接
				sb3.append(reconcile.getAccountId());sb3.append(",");
				if (reconcile.getAccountId().length()>1) {
					w1 += reconcile.getAccountId().length()/2+1;
				}else {
					w1 += reconcile.getAccountId().length();
				}
			}
		}
				
		if (sb1.length()>0) {
			//去掉字符串末尾的","
		String str2 = sb1.substring(0,sb1.length()-1);
			map.put(1,str2);
		}
		
		if (sb3.length()>0) {
			w2=feeAccountMapper.updateReconcileWhenCancel(ConvertStringAndLong.convertStrToLong(sb3.toString()));
		}
		logger.debug("不存在收付款记录的对账单数量为:"+q2);
		logger.debug("应该修改的记账单数量为:"+w1);

		if (q1 > 0) {
			map.put(2,"取消成功");
		}
		
		
		return map;	
	}
	
	
	
	
	/**
	 * 查询对账单列表
	 * 查询到符合条件的对账单列表时还需要查询对账单下的收付款信息
	 */
	@Override
	public PageInfo<ReconcileDto> getReconcileList(ReconcileDto reconcileDto){
		if (reconcileDto.getPageNum() < 1) {
			reconcileDto.setPageNum(1);
		}
		if (reconcileDto.getPageSize() < 0) {
			reconcileDto.setPageSize(0);
		}
		StringBuilder sbd = new StringBuilder();
		reconcileDto.setCompanyId(SecurityInfoGetter.getCompanyId());
		PageHelper.startPage(reconcileDto.getPageNum(),reconcileDto.getPageSize());
		List<ReconcileDto> reconcileList = reconcileMapper.getReconcileList(reconcileDto);
		if (null != reconcileList && reconcileList.size() > 0) {
			for(ReconcileDto rd:reconcileList) {
				sbd.append(rd.getReconcileId());sbd.append(",");
			}	
		}else {
			PageInfo<ReconcileDto> page = new PageInfo<ReconcileDto>(reconcileList);
			return page;
		}
		
		
		List<FeeExchange> feeExchangelist = new ArrayList<FeeExchange>();
		//批量获取一篮子对账单下的一篮子收付款记录
		if (sbd.length() > 0) {
			Long[] reconcileIds = ConvertStringAndLong.convertStrToLong(sbd.toString());
			feeExchangelist = feeExchangeMapper.getFeeExchangesByBatch(reconcileIds);
		}
		if (null != feeExchangelist && feeExchangelist.size() > 0) {
		for(ReconcileDto rdto:reconcileList) {
			double j = 0.0d;
			int i = 0;
			for(FeeExchange fe:feeExchangelist) {
				if (rdto.getReconcileId().longValue() == fe.getReconcileId().longValue()) {
					i++;
					j += fe.getTransportationExpenses();
					j += fe.getOtherExpenses();
				}
			}
			rdto.setSumAmount(j);
			rdto.setSumAmountNum(i);
		}
		}
		PageInfo<ReconcileDto> page = new PageInfo<ReconcileDto>(reconcileList);
		return page;
	}
	
	
	
	@Override
	public ReconcileDto selectReconcileByPk(Long pk) {
		
		ReconcileDto reconcileDto = reconcileMapper.selectReconcileByPrimaryKey(pk);
		//获取对账单下的收付款记录List
		List<FeeExchange> feeExchangeList = feeExchangeMapper.getFeeExchangeListByReconcileId(pk);
		if (null != feeExchangeList) {
			reconcileDto.setFeeExchangeList(feeExchangeList);	
		}	
		
		return reconcileDto;
	}
	
	
	
	
	
	



	
	
	

}
