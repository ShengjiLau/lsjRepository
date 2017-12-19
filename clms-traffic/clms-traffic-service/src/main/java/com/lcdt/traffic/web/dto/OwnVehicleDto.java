package com.lcdt.traffic.web.dto;

import com.lcdt.traffic.model.OwnVehicle;
import com.lcdt.traffic.model.OwnVehicleCertificate;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017-12-13
 */
public class OwnVehicleDto extends OwnVehicle {

    private List<OwnVehicleCertificate> ownVehicleCertificateList;

    public List<OwnVehicleCertificate> getOwnVehicleCertificateList() {
        return ownVehicleCertificateList;
    }

    public void setOwnVehicleCertificateList(List<OwnVehicleCertificate> ownVehicleCertificateList) {
        this.ownVehicleCertificateList = ownVehicleCertificateList;
    }


}
