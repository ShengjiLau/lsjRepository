package com.lcdt.pay.model;

public class ServiceProductPackage {
    private Integer packageId;

    private Integer productId;

    private String packageDes;

    private Integer productNum;

    private String packagePrice;

    private String packageType;

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getPackageDes() {
        return packageDes;
    }

    public void setPackageDes(String packageDes) {
        this.packageDes = packageDes == null ? null : packageDes.trim();
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public String getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(String packagePrice) {
        this.packagePrice = packagePrice == null ? null : packagePrice.trim();
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }
}