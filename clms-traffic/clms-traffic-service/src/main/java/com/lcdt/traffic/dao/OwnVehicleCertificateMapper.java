package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.OwnVehicleCertificate;
import java.util.List;

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
}