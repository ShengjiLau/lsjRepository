package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.WaybillTransferRecord;

import java.util.List;

public interface WaybillTransferRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_waybill_transfer_record
     *
     * @mbg.generated Wed Dec 20 10:13:16 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_waybill_transfer_record
     *
     * @mbg.generated Wed Dec 20 10:13:16 CST 2017
     */
    int insert(WaybillTransferRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_waybill_transfer_record
     *
     * @mbg.generated Wed Dec 20 10:13:16 CST 2017
     */
    WaybillTransferRecord selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_waybill_transfer_record
     *
     * @mbg.generated Wed Dec 20 10:13:16 CST 2017
     */
    List<WaybillTransferRecord> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_waybill_transfer_record
     *
     * @mbg.generated Wed Dec 20 10:13:16 CST 2017
     */
    int updateByPrimaryKey(WaybillTransferRecord record);

    /**
     * 修改换车记录：客户运单需要传递参数（id,carrierCompanyId,和其它参数）;我的运单需要传递参数（id,companyId,和其它参数）
     * @param waybillTransferRecord
     * @return
     */
    int updateTranserRecordByIdAndXCompanyId(WaybillTransferRecord waybillTransferRecord);

    /**
     * 查询单条换车记录：客户运单需要传递参数（id,carrierCompanyId,和其它参数）;我的运单需要传递参数（id,companyId,和其它参数）
     * @param waybillTransferRecord
     * @return
     */
    WaybillTransferRecord selectTranserRecordByIdAndXCompanyId(WaybillTransferRecord waybillTransferRecord);

    /**
     * 查询换车记录列表：客户运单需要传递参数（id,carrierCompanyId,和其它参数）;我的运单需要传递参数（id,companyId,和其它参数）
     * @param waybillTransferRecord
     * @return
     */
    List<WaybillTransferRecord> selectTranserRecordByCondition(WaybillTransferRecord waybillTransferRecord);
}