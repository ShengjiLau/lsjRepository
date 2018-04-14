package com.lcdt.traffic.dto;

import com.lcdt.traffic.model.OwnDriver;
import com.lcdt.traffic.model.OwnDriverCertificate;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017-12-21
 */
public class OwnDriverDto extends OwnDriver {

    private List<OwnDriverCertificate> ownDriverCertificateList;

    private int pageNum;

    private int pageSize;

    public List<OwnDriverCertificate> getOwnDriverCertificateList() {
        return ownDriverCertificateList;
    }

    public void setOwnDriverCertificateList(List<OwnDriverCertificate> ownDriverCertificateList) {
        this.ownDriverCertificateList = ownDriverCertificateList;
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
