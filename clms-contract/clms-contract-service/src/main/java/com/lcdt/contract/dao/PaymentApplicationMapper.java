package com.lcdt.contract.dao;

import com.lcdt.contract.model.PaymentApplication;
import com.lcdt.contract.web.dto.PaymentApplicationDto;

import java.util.List;

public interface PaymentApplicationMapper {
    int deleteByPrimaryKey(Long paId);

    int insert(PaymentApplication record);

    int insertSelective(PaymentApplication record);

    PaymentApplication selectByPrimaryKey(Long paId);

    int updateByPrimaryKeySelective(PaymentApplication record);

    int updateByPrimaryKey(PaymentApplication record);

    /**
     * 根据条件查询付款单记录
     * @param paymentApplicationDto
     * @return
     */
    List<PaymentApplication> selectByCondition(PaymentApplicationDto paymentApplicationDto);

    /**
     * 更新审批状态
     * @param paId
     * @param companyId
     * @param status
     * @return
     */
    int updateApprovalStatus(Long paId,Long companyId,Short status);
}