package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.OwnVehicleCertificate;
import java.util.List;
import java.util.Map;

public interface OwnVehicleCertificateMapper {
    int deleteByPrimaryKey(Long dgId);

    int insert(OwnVehicleCertificate record);

    OwnVehicleCertificate selectByPrimaryKey(Long dgId);

    List<OwnVehicleCertificate> selectAll();

    int updateByPrimaryKey(OwnVehicleCertificate record);

    /**
     * 批量插入
     * @param ownVehicleCertificateList
     * @return
     */
    int insertBatch(List<OwnVehicleCertificate> ownVehicleCertificateList);

    /**
     * 批量更新
     * @param ownVehicleCertificateList
     * @return
     */
    int updateBatch(List<OwnVehicleCertificate> ownVehicleCertificateList);

    /**
     * 删除证件（实为更新isDeleted字段）
     * @param ownVehicle
     * @return
     */
    int deleteByUpdate(OwnVehicleCertificate ownVehicle);

    /**
     * 根据我的车辆id查询该车辆下的证件ocvId
     * @param OwnVehicleId
     * @return
     */
    List<Long> selectOvcIdByOwnVehicleId(Long OwnVehicleId);

    /**
     * 根据主键批量删除
     * @param map
     * @return
     */
    int deleteByBatch(Map<String, Object> map);

}