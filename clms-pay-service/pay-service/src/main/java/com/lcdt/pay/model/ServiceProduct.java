package com.lcdt.pay.model;

public class ServiceProduct {
    private Integer productId;

    private String productName;

    private Boolean productValid;

    private String serviceName;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Boolean getProductValid() {
        return productValid;
    }

    public void setProductValid(Boolean productValid) {
        this.productValid = productValid;
    }
}