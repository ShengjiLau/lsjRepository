package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.model.WaybillDao;
import com.lcdt.traffic.web.dto.WaybillCustListParamsDto;
import com.lcdt.traffic.web.dto.WaybillDto;
import com.lcdt.traffic.web.dto.WaybillOwnListParamsDto;

import java.util.Map;

/**
 * Created by lyqishan on 2017/12/20
 */

public interface WaybillService {
    /**
     * 新增运单
     *
     * @param waybillDto
     * @return
     */
    int addWaybill(WaybillDto waybillDto);

    /**
     * 运单删除
     *
     * @param waybillId
     * @param companyId
     * @return
     */
    int deleteWaybill(Long waybillId, Long companyId);

    /**
     * 修改我的运单
     *
     * @param waybillDto
     * @return
     */
    int modifyOwnWaybill(WaybillDto waybillDto);

    /**
     * 修改客户运单
     *
     * @param waybillDto
     * @return
     */
    int modifyCustomerWaybill(WaybillDto waybillDto);
    /**
     * 我的运单查询
     *
     * @param waybillId
     * @param companyId
     * @return
     */
    WaybillDao queryOwnWaybill(Long waybillId, Long companyId);

    /**
     * 客户运单查询
     * @param waybillId
     * @param carrierCompanyId
     * @return
     */
    WaybillDao queryCustomerWaybill(Long waybillId, Long carrierCompanyId);

    /**
     * 查询获取我的运单列表
     *
     * @param dto
     * @param pageInfo
     * @return
     */
    PageInfo queryOwnWaybillList(WaybillOwnListParamsDto dto, PageInfo pageInfo);

    /**
     * 查询获取客户运单列表
     *
     * @param dto
     * @param pageInfo
     * @return
     */
    PageInfo queryCustomerWaybillList(WaybillCustListParamsDto dto, PageInfo pageInfo);

    /**
     * 传运单的状态和运单以逗号分隔开的运单id，批量修改我的运单状态
     * @param map
     * @return
     */
    int modifyOwnWaybillStatus(Map map);

    /**
     * 传运单的状态和运单以逗号分隔开的运单id，批量修改客户的运单状态
     * @param map
     * @return
     */
    int modifyCustomerWaybillStatus(Map map);


}
