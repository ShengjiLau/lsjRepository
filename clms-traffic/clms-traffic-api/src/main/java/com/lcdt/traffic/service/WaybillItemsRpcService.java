package com.lcdt.traffic.service;

/**
 * Created by lyqishan on 2018/6/6
 */

public interface WaybillItemsRpcService {
    /**
     * 删除运单详细
     * @param companyId
     * @param waybillItemsId
     * @return
     */
    int deleteWaybillItems(Long companyId,Long waybillItemsId);
}
