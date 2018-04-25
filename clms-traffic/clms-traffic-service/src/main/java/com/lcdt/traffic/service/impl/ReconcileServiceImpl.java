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
		
	List<FeeAccount> feeAccountList=new ArrayList<FeeAccount>();
	for(Reconcile fa:reconcileList) {
		//添加对账单的所属公司id 操作人id 操作人姓名 默认状态
		fa.setCompanyId(SecurityInfoGetter.getCompanyId());
		fa.setOperatorId(SecurityInfoGetter.getUser().getUserId());
		fa.setOperatorName(SecurityInfoGetter.getUser().getRealName());
		fa.setCancelOk((short) 0);//生成对账单时取消状态设置为0不取消
	}
	//批量插入
	int result=reconcileMapper.insertByBatch(reconcileList);
	
	for(Reconcile reconcile:reconcileList) {
		Reconcile rec =reconcileMapper.selectByPrimaryKey(reconcile.getReconcileId());
		//得到记账单id
		String str=rec.getAccountId();
		String[] ss=str.split(",");	
		Long[] acLongId=new Long[ss.length];
		for(int i=0;i<ss.length;i++) {
			acLongId[i]=Long.valueOf(ss[i]);
		}
		for(Long l:acLongId) {
			//创建一个记账单 依次放入每条记账单id以及对应的对账单id和对账单号
			FeeAccount fa = new FeeAccount();
			fa.setAccountId(l);
			fa.setReconcileId(rec.getReconcileId());
			fa.setReconcileCode(rec.getReconcileCode());
			feeAccountList.add(fa);
		}	
	}
	int i=feeAccountList.size();
	logger.debug("应添加记账单ReconcileId数量:"+feeAccountList.size());
	//批量修改记账单对应的对账单id 和 对账单单号
	int j=feeAccountMapper.updateReconcileByBatch(feeAccountList);
	logger.debug("实际添加记账单ReconcileId数量:"+j);
	logger.debug("插入对账单数量为:"+result);
	//一下判断既可判断对应的记账单是否全部被修改,又可判断是否存在对应的记账单id
	if(i==j) {
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
		StringBuilder sb = new StringBuilder();
		Map<Integer, String> map =new HashMap<Integer, String>();
		StringBuilder sbs = new StringBuilder();
		StringBuilder sb3 = new StringBuilder();
		String [] ss=reconcileIds.split(",");
		Long[] reconcileIdArray =new Long[ss.length];	
		for(int i=0;i<ss.length;i++) {
			reconcileIdArray[i]=Long.valueOf(ss[i]);
		}
		int q1=0;
		int q2=0;
		int w1=0;
		int w2=0;
		for(int i=0;i<reconcileIdArray.length;i++) {
			int a=feeExchangeMapper.selectCountFeeExchangeByReconcileId(reconcileIdArray[i]);
			if(a>0) {
				//将已存在收付款记录的对账单id拼接成字符串
				sb.append(reconcileIdArray[i]);sb.append(",");
			}else {
				q2++;
				//将不存在收付款记录的对账单id拼接成字符串,dao层执行update set in操作
				sbs.append(reconcileIdArray[i]);sbs.append(",");
			}
		}
			
		if(q2>0) {
			String str1 =sbs.substring(0,sbs.length()-1);
			q1=reconcileMapper.cancelByBatch(str1);
			//获取不存在收付款记录的对账单信息列表
			List<Reconcile> reconcileList=reconcileMapper.getReconcileListByPk(str1);
			for(Reconcile reconcile:reconcileList) {
				//得到所有需要修改的被取消的对账当单对应的记账单进行拼接
				sb3.append(reconcile.getAccountId());sb3.append(",");
				if(reconcile.getAccountId().length()>1) {
					w1+=reconcile.getAccountId().length()/2+1;
				}else {
					w1+=reconcile.getAccountId().length();
				}
			}
		}
				
		if(sb.length()>0) {
			//去掉字符串末尾的","
		String str2=sb.substring(0,sb.length()-1);
			map.put(1,str2);
		}
		
		if(sb3.length()>0) {
			String str3 =sb3.substring(0,sb3.length()-1);
			w2=feeAccountMapper.updateReconcileWhenCancel(str3);
		}
		logger.debug("不存在收付款记录的对账单数量为:"+q2);
		logger.debug("应该修改的记账单数量为:"+w1);

		if(q1>0) {
			map.put(2,"取消成功");
		}
		
		
		return map;	
	}
	
	
	
	
	/**
	 * 查询对账单列表
	 */
	@Override
	public PageInfo<ReconcileDto> getReconcileList(ReconcileDto reconcileDto){
		if(reconcileDto.getPageNum()<1) {
			reconcileDto.setPageNum(1);
		}
		if(reconcileDto.getPageSize()<0) {
			reconcileDto.setPageSize(0);
		}
		reconcileDto.setCompanyId(SecurityInfoGetter.getCompanyId());
		PageHelper.startPage(reconcileDto.getPageNum(),reconcileDto.getPageSize());
		List<ReconcileDto> reconcileList= reconcileMapper.getReconcileList(reconcileDto);
		if(null!=reconcileList&&reconcileList.size()>0) {
			for(ReconcileDto rd:reconcileList) {
				double j=0.0d;
				List<FeeExchange> feeExchangelist= feeExchangeMapper.getFeeExchangeListByReconcileId(rd.getReconcileId());
				if(null!=feeExchangelist&&feeExchangelist.size()>0) {
					for(FeeExchange fe:feeExchangelist) {
						j+=fe.getAccountAmount();
					}
				}
				rd.setSumAmountNum(feeExchangelist.size());
				rd.setSumAmount(j);
			}
		}
		
		PageInfo<ReconcileDto> page =new PageInfo<ReconcileDto>(reconcileList);
		return page;
	}
	
	
	
	@Override
	public Reconcile selectReconcileByPk(Long pk) {
		
		return reconcileMapper.selectByPrimaryKey(pk);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 这个方法用于将Long类型数组转化为规定格式的String字符串
	 * @param longId
	 * @return
	 */
	public String convertLoToStr(Long[] longId) {
		StringBuilder sbd =new StringBuilder();
		for(Long l:longId) {
			sbd.append(l);sbd.append(",");
		}
		sbd.substring(0,sbd.length()-1);
		
		return sbd.toString();
	}





	
	
	

}
