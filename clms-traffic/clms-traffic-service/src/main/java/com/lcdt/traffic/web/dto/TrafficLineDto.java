package com.lcdt.traffic.web.dto;

import java.util.Date;

/**
 * Created by Administrator on 2018/8/13.
 */
public class TrafficLineDto {
    private Long lineId;
    private String lineName;
    private String lineStartProvince;
    private String lineStartCity;
    private String lineEndProvince;
    private String lineEndCity;
    private Integer lineStatus;
    private Long companyId;

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getLineStartProvince() {
        return lineStartProvince;
    }

    public void setLineStartProvince(String lineStartProvince) {
        this.lineStartProvince = lineStartProvince;
    }

    public String getLineStartCity() {
        return lineStartCity;
    }

    public void setLineStartCity(String lineStartCity) {
        this.lineStartCity = lineStartCity;
    }

    public String getLineEndProvince() {
        return lineEndProvince;
    }

    public void setLineEndProvince(String lineEndProvince) {
        this.lineEndProvince = lineEndProvince;
    }

    public String getLineEndCity() {
        return lineEndCity;
    }

    public void setLineEndCity(String lineEndCity) {
        this.lineEndCity = lineEndCity;
    }

    public Integer getLineStatus() {
        return lineStatus;
    }

    public void setLineStatus(Integer lineStatus) {
        this.lineStatus = lineStatus;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
