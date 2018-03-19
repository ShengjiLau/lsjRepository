package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dto.DriverWaybillParamsDto;

import java.util.Map;

/**
 * Created by lyqishan on 2018/3/19
 */

public interface WaybillRpcService {
    /**
     * 查询司机运单列表
     * @param dto
     * @return
     */
    PageInfo queryDriverWaybillList(DriverWaybillParamsDto dto);

    /**
     * 根据司机id更新运单状态
     * @param dto
     * @return
     */
    int modifyWaybillStatusByDriver(DriverWaybillParamsDto dto);

    /**
     * 根据司机id更新运单上传回单
     * @param dto
     * @return
     */
    int modifyWaybillReceiptByDriver(DriverWaybillParamsDto dto);
}
