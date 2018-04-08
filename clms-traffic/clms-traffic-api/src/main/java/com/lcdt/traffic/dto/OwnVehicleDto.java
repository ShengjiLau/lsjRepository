package com.lcdt.traffic.dto;

import com.lcdt.traffic.model.OwnVehicle;
import com.lcdt.traffic.model.OwnVehicleCertificate;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017-12-13
 */
public class OwnVehicleDto extends OwnVehicle {

    private List<OwnVehicleCertificate> ownVehicleCertificateList;

    private String vehicleNumOrPhone;

    private int pageNum;

    private int pageSize;

    public List<OwnVehicleCertificate> getOwnVehicleCertificateList() {
        return ownVehicleCertificateList;
    }

    public void setOwnVehicleCertificateList(List<OwnVehicleCertificate> ownVehicleCertificateList) {
        this.ownVehicleCertificateList = ownVehicleCertificateList;
    }

    public String getVehicleNumOrPhone() {
        return vehicleNumOrPhone;
    }

    public void setVehicleNumOrPhone(String vehicleNumOrPhone) {
        this.vehicleNumOrPhone = vehicleNumOrPhone;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
