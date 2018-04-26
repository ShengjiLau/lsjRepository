package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.FeeAccount;
import com.lcdt.traffic.web.dto.FeeAccountDto;
import com.lcdt.traffic.web.dto.ReconcileDto;

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

    List<FeeAccountDto> selectByCondition(Map m);

    FeeAccountDto selectByConditionFeeTotal(Map m);

    int auditByAccountIds(Map map);

    List feeAccountGroupByReceivPayName(Map map);
   
    /**
     * 此方法用于生成对账单批量修改FeeAccount中对账单的id和code
     * @param feeAccountList
     * @return
     */
    int updateReconcileByBatch(List<FeeAccount> feeAccountList);
    
    int insertBatch(List<FeeAccount> list);

    int updateBatch(List<FeeAccount> list);

    /**
     * 取消对账修改记账单对账信息
     * @param String feeAccountIds
     * @return
     */
    int updateReconcileWhenCancel(Long[] feeAccountIds);
    /**
     * 对账和取消对账修改记账单对账信息
     * @param dto
     * @return
     */
    int updateReconcileCodeAndId(ReconcileDto dto);
    
    
    List<FeeAccount> selectFeeAccountListByReconcileId(Long[] accountIds);

}