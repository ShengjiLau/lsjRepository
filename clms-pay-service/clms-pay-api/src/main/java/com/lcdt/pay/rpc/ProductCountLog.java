package com.lcdt.pay.rpc;

public class ProductCountLog {
    private Long serviceCountLogId;

    private String logNo;

    private String logDes;

    private String serviceName;

    private Integer consumeNum;

    private Integer remainNum;

    private Integer logType;

    private Integer deduct;

    private Long companyId;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getServiceCountLogId() {
        return serviceCountLogId;
    }

    public void setServiceCountLogId(Long serviceCountLogId) {
        this.serviceCountLogId = serviceCountLogId;
    }

    public String getLogNo() {
        return logNo;
    }

    public void setLogNo(String logNo) {
        this.logNo = logNo == null ? null : logNo.trim();
    }

    public String getLogDes() {
        return logDes;
    }

    public void setLogDes(String logDes) {
        this.logDes = logDes == null ? null : logDes.trim();
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName == null ? null : serviceName.trim();
    }

    public Integer getConsumeNum() {
        return consumeNum;
    }

    public void setConsumeNum(Integer consumeNum) {
        this.consumeNum = consumeNum;
    }

    public Integer getRemainNum() {
        return remainNum;
    }

    public void setRemainNum(Integer remainNum) {
        this.remainNum = remainNum;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public Integer getDeduct() {
        return deduct;
    }

    public void setDeduct(Integer deduct) {
        this.deduct = deduct;
    }
}