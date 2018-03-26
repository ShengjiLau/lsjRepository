package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dto.*;
import com.lcdt.traffic.model.Waybill;

import java.util.Map;

/**
 * Created by lyqishan on 2018/3/19
 */

public interface WaybillRpcService {

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
    int modifyOwnWaybillStatus(WaybillModifyStatusDto dto);

    /**
     * 传运单的状态和运单以逗号分隔开的运单id，批量修改客户的运单状态
     * @param dto
     * @return
     */
    int modifyCustomerWaybillStatus(WaybillModifyStatusDto dto);

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
}
