package com.lcdt.contract.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.contract.model.PaymentApplication;
import com.lcdt.contract.web.dto.PaymentApplicationDto;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2018-05-07
 */
public interface PaymentApplictionService {

    /**
     * 根据条件获取付款申请列表
     * @param paymentApplicationDto
     * @param pageInfo
     * @return
     */
    PageInfo<List<PaymentApplication>> paymentApplictionList(PaymentApplicationDto paymentApplicationDto, PageInfo pageInfo);

    /**
     * 新增付款申请记录
     * @param paymentApplicationDto
     * @return
     */
    int addPaymentAppliction(PaymentApplicationDto paymentApplicationDto);
}
