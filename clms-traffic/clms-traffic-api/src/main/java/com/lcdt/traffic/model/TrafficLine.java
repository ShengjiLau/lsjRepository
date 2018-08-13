package com.lcdt.traffic.model;


import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author code generate
 * @since 2018-08-09
 */
public class TrafficLine implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long lineId;
    private String lineName;
    private String lineStartProvince;
    private String lineStartCity;
    private String lineEndProvince;
    private String lineEndCity;
    /**
     * 0-启用，9:-禁用
     */
    private Integer lineStatus;
    private Long companyId;
    private Long createId;
    private String createName;
    private Date createDate;


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

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Line{" +
        ", lineId=" + lineId +
        ", lineName=" + lineName +
        ", lineStart=" + lineStartProvince +", lineStart=" + lineStartCity +
        ", lineEnd=" + lineEndProvince +", lineEnd=" + lineEndCity +
        ", lineStatus=" + lineStatus +
        ", companyId=" + companyId +
        ", createId=" + createId +
        ", createName=" + createName +
        ", createDate=" + createDate +
        "}";
    }
}
