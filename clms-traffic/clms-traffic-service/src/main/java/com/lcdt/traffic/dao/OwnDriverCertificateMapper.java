package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.OwnDriverCertificate;
import java.util.List;

public interface OwnDriverCertificateMapper {
    int deleteByPrimaryKey(Long dgId);

    int insert(OwnDriverCertificate record);

    OwnDriverCertificate selectByPrimaryKey(Long dgId);

    List<OwnDriverCertificate> selectAll();

    int updateByPrimaryKey(OwnDriverCertificate record);
}