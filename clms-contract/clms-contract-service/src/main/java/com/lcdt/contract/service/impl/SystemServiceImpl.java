package com.lcdt.contract.service.impl;

import com.lcdt.contract.dao.*;
import com.lcdt.contract.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @AUTHOR liuh
 * @DATE 2018-07-05
 */
@Service
@Transactional
public class SystemServiceImpl implements SystemService {

    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private ContractProductMapper contractProductMapper;
    @Autowired
    private ContractApprovalMapper contractApprovalMapper;
    @Autowired
    private ContractLogMapper contractLogMapper;

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderProductMapper orderProductMapper;
    @Autowired
    private OrderApprovalMapper orderApprovalMapper;

    @Autowired
    private PaymentApplicationMapper paymentApplicationMapper;
    @Autowired
    private PaApprovalMapper paApprovalMapper;

    @Autowired
    private ApprovalProcessMapper approvalProcessMapper;

    @Override
    public int clearContractData(Long companyId) {
        int rows = 0;
        /**删除合同相关数据 先删除子表数据，在删除主表*/
        rows += contractProductMapper.deleteByCompanyId(companyId);
        rows += contractApprovalMapper.deleteByCompanyId(companyId);
        rows += contractLogMapper.deleteByCompanyId(companyId);
        rows += contractMapper.deleteByCompanyId(companyId);

        /**删除订单相关数据 先删除子表数据，在删除主表*/
        rows += orderProductMapper.deleteByCompanyId(companyId);
        rows += orderApprovalMapper.deleteByCompanyId(companyId);
        rows += orderMapper.deleteByCompanyId(companyId);

        /**删除付款单相关数据 先删除子表数据，在删除主表*/
        rows += paApprovalMapper.deleteByCompanyId(companyId);
        rows += paymentApplicationMapper.deleteByCompanyId(companyId);

        /**删除审批流程数据*/
        rows += approvalProcessMapper.deleteByCompanyId(companyId);
        return rows;
    }
}
