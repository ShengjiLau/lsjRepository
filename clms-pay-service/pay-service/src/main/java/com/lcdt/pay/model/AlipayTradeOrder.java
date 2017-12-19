package com.lcdt.pay.model;

public class AlipayTradeOrder {

    private String tradeNo;

    private String productCode;

    private String totalAmount;

    private String subject;

    private String body;

    private String  passbackparams;

    private String extendParams;

    public String alipayBizContent(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"out_trade_no\":");
        sb.append("\"").append(tradeNo).append("\",");
        sb.append("\"product_code\":");
        sb.append("\"").append(productCode).append("\",");
        sb.append("\"total_amount\":");
        sb.append("\"").append(totalAmount).append("\",");
        sb.append("\"body\":");
        sb.append("\"").append(body).append("\",");
        sb.append("\"passback_params\":");
        sb.append("\"").append(passbackparams).append("\",");
        sb.append("\"extend_params\":");
        sb.append("\"").append(extendParams).append("\"");
        sb.append("}");
        return sb.toString();
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPassbackparams() {
        return passbackparams;
    }

    public void setPassbackparams(String passbackparams) {
        this.passbackparams = passbackparams;
    }

    public String getExtendParams() {
        return extendParams;
    }

    public void setExtendParams(String extendParams) {
        this.extendParams = extendParams;
    }
}
