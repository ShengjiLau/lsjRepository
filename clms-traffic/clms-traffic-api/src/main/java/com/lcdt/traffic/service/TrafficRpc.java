package com.lcdt.traffic.service;

import com.lcdt.traffic.dto.WaybillParamsDto;
import com.lcdt.traffic.model.OwnDriver;
import com.lcdt.traffic.model.OwnVehicle;
import com.lcdt.traffic.model.WaybillPlan;
import com.lcdt.userinfo.model.UserCompRel;

import java.util.Map;

/**
 * Created by yangbinq on 2018/1/12.
 */
public interface TrafficRpc {

    void waybillPositionTimer(Map map);

    /**
     * 新增车辆
     * @param ownVehicle
     * @return
     */
    OwnVehicle addVehicle(OwnVehicle ownVehicle);

    /**
     * 新增司机
     * @param ownDriver
     * @return
     */
    OwnDriver addDriver(OwnDriver ownDriver);



    /***
     * 采购单生成运计划
     * @param waybillParamsDto
     *
     * @param flag (1--采购单生成运输计划; 2--销售单生成运输计划)
     * @return
     */
    WaybillPlan purchase4Plan(WaybillParamsDto waybillParamsDto,int flag);




    /**
     * 通过计划流水号查询DO
     * @param serialNo
     * @return
     */
    WaybillPlan getWaybillPlanBySerialNo(String serialNo);





}
