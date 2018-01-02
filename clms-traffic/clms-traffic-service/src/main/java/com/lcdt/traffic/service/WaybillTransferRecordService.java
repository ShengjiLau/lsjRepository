package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.model.WaybillTransferRecord;
import com.lcdt.traffic.web.dto.WaybillTransferRecordDto;

/**
 * Created by lyqishan on 2017/12/28
 */

public interface WaybillTransferRecordService {
    /**
     * 新增换车记录
     * @param dto
     * @return
     */
    int addWaybillTransferRecord(WaybillTransferRecordDto dto);

    /**
     * 删除换车记录
     * @param id
     * @param companyId
     * @return
     */
    int deleteWaybillTransferRecord(Long id,Long companyId);

    /**
     * 修改换车记录：客户运单需要传递参数（id,carrierCompanyId,和其它参数）;我的运单需要传递参数（id,companyId,和其它参数）
     * @param dto
     * @return
     */
    int modifyWaybillTransferRecord(WaybillTransferRecordDto dto);

    /**
     * 查询换车记录：客户运单需要传递参数（id,carrierCompanyId,和其它参数）;我的运单需要传递参数（id,companyId,和其它参数）
     * @param dto
     * @return
     */
    WaybillTransferRecord queryWaybillTransferRecord(WaybillTransferRecordDto dto);

    /**
     * 查询的车记录列表：客户运单需要传递参数（id,carrierCompanyId,和其它参数）;我的运单需要传递参数（id,companyId,和其它参数）
     * @param dto
     * @param pageInfo
     * @return
     */
    PageInfo queryWaybillTransferRecordList(WaybillTransferRecordDto dto,PageInfo pageInfo);
}
