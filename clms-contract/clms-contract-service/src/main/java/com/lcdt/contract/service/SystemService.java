package com.lcdt.contract.service;

/**
 * @AUTHOR liuh
 * @DATE 2018-07-05
 */
public interface SystemService {

    /**
     * 清理企业下的所有合同/订单/收款单数据
     * @param companyId
     * @return
     */
    int clearContractData(Long companyId);
}
