package com.lcdt.pay.model;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.HashMap;
import java.util.Map;

public class AlipayTradeOrder {

    @JSONField(name = "out_trade_no")
    private String tradeNo;

    @JSONField(name = "product_code")
    private String productCode;


    @JSONField(name = "total_amount")
    private String totalAmount;


    @JSONField(name = "subject")
    private String subject;


    @JSONField(name = "body")
    private String body;

    @JSONField(name = "passback_params")
    private String  passbackparams;


    @JSONField(name = "extend_params")
    private HashMap<String,String> extendParams;


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

    public Map<String, String> getExtendParams() {
        return extendParams;
    }

    public void setExtendParams(HashMap<String,String> extendParams) {
        this.extendParams = extendParams;
    }
}
