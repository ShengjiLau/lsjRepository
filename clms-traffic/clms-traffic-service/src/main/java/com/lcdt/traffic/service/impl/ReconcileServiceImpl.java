package com.lcdt.traffic.service.impl;




import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeanUtils;
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
	private FeeAccountMapper feeAccountMapper;
	
	/**
	 * 插入对账单
	 * 前端Api传入的参数为ReconcileListDto,包含多条ReconcileDto,
	 * ReconcileDto包含记账单id数组,业务组id数组,运单id数组.
	 * 此处的业务逻辑需要将这些id数组转化为字符串并赋值到Reconcile相对应的属性上.
	 * 在批量插入Reconcile后,还需要获取相应的Reconcile的主键id和reconcileCode并将其添加到对应的记账单中
	 */
	@Override
	@Transactional
	public int insertReconcileBatch(ReconcileListDto reconcileListDto) {
		
	List<ReconcileDto> reconcileDtoList=reconcileListDto.getReconcileList();
	List<Reconcile> reconcileLists =new ArrayList<Reconcile>();
	List<FeeAccount> feeAccountList=new ArrayList<FeeAccount>();
	//添加创建人id,创建人名字,公司id,将传入的记账单id数组,业务组id数组,运单id数组转化为字符串存入Reconcile
	for(ReconcileDto reconcileDto:reconcileDtoList) {
		Reconcile r= new Reconcile();
		BeanUtils.copyProperties(reconcileDto, r);		
		r.setCompanyId(SecurityInfoGetter.getCompanyId());
		r.setOperatorName(SecurityInfoGetter.getUser().getRealName());
		r.setOperatorId(SecurityInfoGetter.getUser().getUserId());
		r.setAccountId(convertLoToStr(reconcileDto.getAccountIds()));
		r.setWaybillId(convertLoToStr(reconcileDto.getWaybillIds()));
		reconcileLists.add(r);
	}
	int result=reconcileMapper.insertByBatch(reconcileLists);
	for(ReconcileDto reconcileDto:reconcileDtoList) {
		Reconcile reconcile =reconcileMapper.selectByPrimaryKey(reconcileDto.getReconcileId());
		Long[] acLongId=reconcileDto.getAccountIds();
		for(Long l:acLongId) {		
			FeeAccount fa = new FeeAccount();
			fa.setAccountId(l);
			fa.setReconcileId(reconcile.getReconcileId());
			fa.setReconcileCode(reconcile.getReconcileCode());
			feeAccountList.add(fa);
		}	
	}
	int i=feeAccountList.size();
	int j=feeAccountMapper.updateReconcileByBatch(feeAccountList);
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
	public int setCancelOk(Long[] reconcileIdList) {
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
		}
		int i=acclids.length;
		int j=feeAccountMapper.updateReconcileByBatch(feeAccountList);
		if(i==j) {
			return result;
		}else {
			return -1;
		}
		
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
