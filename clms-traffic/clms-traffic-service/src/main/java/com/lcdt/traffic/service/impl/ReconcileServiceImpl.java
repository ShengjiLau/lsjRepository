package com.lcdt.traffic.service.impl;





import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.dao.FeeAccountMapper;
import com.lcdt.traffic.dao.ReconcileMapper;
import com.lcdt.traffic.model.FeeAccount;
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
	public int setCancelOk(String reconcileIdList) {
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		int result = reconcileMapper.cancelByBatch(reconcileIdList);
		List<Reconcile> reconcileList= reconcileMapper.getReconcileListByPk(reconcileIdList);
		Long[] acclids=null;
		for(Reconcile reconcile:reconcileList) {
			String s=reconcile.getAccountId();
			String[] ss=s.split(",");
			Long[] accIds=new Long[ss.length];
			for(int i=0;i<ss.length;i++) {
				accIds[i]=Long.parseLong(ss[i]);
			}
			acclids=(Long[]) ArrayUtils.addAll(acclids,accIds);
		}
		List<FeeAccount> feeAccountList =new ArrayList<FeeAccount>();
		for(Long l:acclids) {
			FeeAccount fa= new FeeAccount();
			fa.setAccountId(l);
			fa.setReconcileId(null);
			fa.setReconcileCode(null);
			feeAccountList.add(fa);
		}
		int i=acclids.length;int j;
		if(0!=feeAccountList.size()) {
			 j=feeAccountMapper.updateReconcileByBatch(feeAccountList);
		}else {
			 j=0;
		}
			return result;
		
		
	}
	
	
	
	
	/**
	 * 查询对账单列表
	 */
	@Override
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
