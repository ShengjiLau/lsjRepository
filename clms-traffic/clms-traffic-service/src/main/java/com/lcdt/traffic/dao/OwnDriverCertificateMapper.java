package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.OwnDriverCertificate;
import java.util.List;

public interface OwnDriverCertificateMapper {
    int deleteByPrimaryKey(Long odcId);

    int insert(OwnDriverCertificate record);

    OwnDriverCertificate selectByPrimaryKey(Long odcId);

    List<OwnDriverCertificate> selectAll();

    int updateByPrimaryKey(OwnDriverCertificate record);
    /**
     * 批量插入
     * @param ownDriverCertificateList
     * @return
     */
    int insertBatch(List<OwnDriverCertificate> ownDriverCertificateList);

    /**
     * 批量更新
     * @param ownDriverCertificateList
     * @return
     */
    int updateBatch(List<OwnDriverCertificate> ownDriverCertificateList);

    /**
     * 删除证件（实为更新isDeleted字段）
     * @param ownDriver
     * @return
     */
    int deleteByUpdate(OwnDriverCertificate ownDriver);
}