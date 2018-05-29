package com.lcdt.contract.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.contract.model.PaymentApplication;
import com.lcdt.contract.web.dto.PaymentApplicationDto;

import java.util.List;
import java.util.Map;

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


    /**
     * 根据orderIds数组批量获取对应的产品信息
     * @param orderIds
     * @return
     */
    List<Map<Long,String>> orderProductInfo(String[] orderIds);

    /**
     * 获取开票记录详情
     * @param paId
     * @return
     */
    PaymentApplicationDto paymentApplictionDetail(Long paId);

    /**
     * 确认付款
     * @param paymentApplication
     * @return
     */
    int confirmPayment(PaymentApplication paymentApplication);
}
