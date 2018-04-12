package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.FeeAccount;
import com.lcdt.traffic.web.dto.FeeAccountDto;

import java.util.List;
import java.util.Map;

public interface FeeAccountMapper {
    int deleteByPrimaryKey(Long accountId);

    int insert(FeeAccount record);

    int insertSelective(FeeAccount record);

    FeeAccount selectByPrimaryKey(Long accountId);

    int updateByPrimaryKeySelective(FeeAccount record);

    int updateByPrimaryKey(FeeAccount record);

    List<FeeAccountDto> selectByWaybillId(FeeAccount record);

    List<FeeAccountDto> selectFlowByWaybillId(Map m);

    List<FeeAccountDto> selectFeeAccountDetail(Map m);

    List<FeeAccountDto> selectOwnByCondition(Map m);

    int auditByAccountIds(Map map);

    List reconcileByAccountIds(Map map);

    
    int updateReconcileByBatch(List<FeeAccount> feeAccountList);
    
    
    

    int insertBatch(List<FeeAccount> list);

    int updateBatch(List<FeeAccount> list);

}