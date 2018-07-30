package com.lcdt.contract.notify;

import com.lcdt.notify.model.BaseAttachment;

/**
 * @AUTHOR liuh
 * @DATE 2018-06-28
 */
public class ContractAttachment extends BaseAttachment {

    /**
     * 员工
     */
    private String employee;

    /**
     * 采购合同标题
     */
    private String purConTittle;

    /**
     * 采购合同流水号
     */
    private String purConSerialNum;

    /**
     * 采购单流水号
     */
    private String purOrderSerialNum;

    /**
     * 付款单流水号
     */
    private String paymentSerialNum;

    /**
     * 销售合同标题
     */
    private String saleConTittle;

    /**
     * 销售合同流水号
     */
    private String saleConSerialNum;

    /**
     * 销售单流水号
     */
    private String saleOrderSerialNum;

    /**
     * 收款单流水号
     */
    private String saleRecSerialNum;

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getPurConTittle() {
        return purConTittle;
    }

    public void setPurConTittle(String purConTittle) {
        this.purConTittle = purConTittle;
    }

    public String getPurConSerialNum() {
        return purConSerialNum;
    }

    public void setPurConSerialNum(String purConSerialNum) {
        this.purConSerialNum = purConSerialNum;
    }

    public String getPurOrderSerialNum() {
        return purOrderSerialNum;
    }

    public void setPurOrderSerialNum(String purOrderSerialNum) {
        this.purOrderSerialNum = purOrderSerialNum;
    }

    public String getPaymentSerialNum() {
        return paymentSerialNum;
    }

    public void setPaymentSerialNum(String paymentSerialNum) {
        this.paymentSerialNum = paymentSerialNum;
    }

    public String getSaleConTittle() {
        return saleConTittle;
    }

    public void setSaleConTittle(String saleConTittle) {
        this.saleConTittle = saleConTittle;
    }

    public String getSaleConSerialNum() {
        return saleConSerialNum;
    }

    public void setSaleConSerialNum(String saleConSerialNum) {
        this.saleConSerialNum = saleConSerialNum;
    }

    public String getSaleOrderSerialNum() {
        return saleOrderSerialNum;
    }

    public void setSaleOrderSerialNum(String saleOrderSerialNum) {
        this.saleOrderSerialNum = saleOrderSerialNum;
    }

    public String getSaleRecSerialNum() {
        return saleRecSerialNum;
    }

    public void setSaleRecSerialNum(String saleRecSerialNum) {
        this.saleRecSerialNum = saleRecSerialNum;
    }
}
