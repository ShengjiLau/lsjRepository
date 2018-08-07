package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dto.*;
import com.lcdt.traffic.model.Waybill;
import com.lcdt.traffic.model.WaybillDao;

import java.util.Map;

/**
 * Created by lyqishan on 2018/3/19
 */

public interface WaybillRpcService {


    /**
     * 新增运单
     *
     * @param waybillDto
     * @return
     */
    Waybill addWaybill(WaybillDto waybillDto);


    /**
     * 修改我的运单
     *
     * @param waybillDto
     * @return
     */
    int modifyOwnWaybill(WaybillModifyParamsDto waybillDto);

    /**
     * 修改客户运单
     *
     * @param waybillDto
     * @return
     */
    int modifyCustomerWaybill(WaybillModifyParamsDto waybillDto);

    /**
     * 我的运单调量
     * @param waybillDto
     * @return
     */
    int modifyOwnQuantity(WaybillModifyParamsDto waybillDto);

    /**
     * 客户运单调量
     * @param waybillDto
     * @return
     */
    int modifyCustomerQuantity(WaybillModifyParamsDto waybillDto);

    /**
     * 查询获取我的运单列表
     * @param dto
     * @return
     */
    PageInfo queryOwnWaybillList(WaybillOwnListParamsDto dto);

    /**
     * 查询获取客户运单列表
     * @param dto
     * @return
     */
    PageInfo queryCustomerWaybillList(WaybillCustListParamsDto dto);


    /**
     * 传运单的状态和运单以逗号分隔开的运单id，批量修改我的运单状态
     * @param dto
     * @return
     */
    Waybill modifyOwnWaybillStatus(WaybillModifyStatusDto dto);

    /**
     * 传运单的状态和运单以逗号分隔开的运单id，批量修改客户的运单状态
     * @param dto
     * @return
     */
    Waybill modifyCustomerWaybillStatus(WaybillModifyStatusDto dto);

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
     * 我的运单 上传回单
     * @param dto
     * @return
     */
    Waybill modifyOwnWaybillReceipt(WaybillModifyReceiptDto dto);

    /**
     * 客户运单  上传回单
     * @param dto
     * @return
     */
    Waybill modifyCustomerWaybillReceipt(WaybillModifyReceiptDto dto);

    /**
     * 根据计划id更新运单状态（参数：waybillStatus，updateId，updateName，waybillPlanId，companyId）
     * @param map
     * @return
     */
    int modifyOwnWaybillStatusByWaybillPlanId(Map map);

    /**
     * 查询司机运单列表
     * @param dto
     * @return
     */
    PageInfo queryDriverWaybillList(DriverWaybillListParsmsDto dto);

    /**
     * 根据司机id更新运单状态
     * @param dto
     * @return
     */
    Waybill modifyWaybillStatusByDriver(DriverWaybillParamsDto dto);

    /**
     * 根据司机id更新运单上传回单
     * @param dto
     * @return
     */
    Waybill modifyWaybillReceiptByDriver(DriverWaybillParamsDto dto);


    /**
     * 我的运单增加路由
     * @param params
     * @return
     */
    int addOwnWaybillRoute(WaybillRouteAddParams params);

    /**
     * 客户运单增加路由
     * @param params
     * @return
     */
    int addCustomerWaybillRoute(WaybillRouteAddParams params);
}
