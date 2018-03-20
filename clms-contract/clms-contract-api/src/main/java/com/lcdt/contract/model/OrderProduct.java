package com.lcdt.contract.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class OrderProduct implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_product.op_id
     *
     * @mbggenerated
     */
	@ApiModelProperty(value="商品id")
    private Long opId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_product.order_id
     *
     * @mbggenerated
     */
	@ApiModelProperty(value="所属订单id")
    private Long orderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_product.name
     *
     * @mbggenerated
     */
    @NotBlank
    @ApiModelProperty(value="商品名称")
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_product.code
     *
     * @mbggenerated
     */
    @NotBlank
    @ApiModelProperty(value="商品编号")
    private String code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_product.sku
     *
     * @mbggenerated
     */
    @NotBlank
    @ApiModelProperty(value="商品单位")
    private String sku;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_product.num
     *
     * @mbggenerated
     */
    @NotBlank
    @ApiModelProperty(value="商品数量")
    private Integer num;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_product.price
     *
     * @mbggenerated
     */
    @NotBlank
    @ApiModelProperty(value="商品单价")
    private BigDecimal price;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_product.total
     *
     * @mbggenerated
     */
    @NotBlank
    @ApiModelProperty(value="总金额")
    private BigDecimal total;

    @NotBlank
    @ApiModelProperty(value="商品规格")
    private String spec;
    
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_order_product
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 11561561L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order_product.op_id
     *
     * @return the value of t_order_product.op_id
     *
     * @mbggenerated
     */
    public Long getOpId() {
        return opId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order_product.op_id
     *
     * @param opId the value for t_order_product.op_id
     *
     * @mbggenerated
     */
    public void setOpId(Long opId) {
        this.opId = opId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order_product.order_id
     *
     * @return the value of t_order_product.order_id
     *
     * @mbggenerated
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order_product.order_id
     *
     * @param orderId the value for t_order_product.order_id
     *
     * @mbggenerated
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order_product.name
     *
     * @return the value of t_order_product.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order_product.name
     *
     * @param name the value for t_order_product.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order_product.code
     *
     * @return the value of t_order_product.code
     *
     * @mbggenerated
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order_product.code
     *
     * @param code the value for t_order_product.code
     *
     * @mbggenerated
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order_product.sku
     *
     * @return the value of t_order_product.sku
     *
     * @mbggenerated
     */
    public String getSku() {
        return sku;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order_product.sku
     *
     * @param sku the value for t_order_product.sku
     *
     * @mbggenerated
     */
    public void setSku(String sku) {
        this.sku = sku == null ? null : sku.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order_product.num
     *
     * @return the value of t_order_product.num
     *
     * @mbggenerated
     */
    public Integer getNum() {
        return num;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order_product.num
     *
     * @param num the value for t_order_product.num
     *
     * @mbggenerated
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order_product.price
     *
     * @return the value of t_order_product.price
     *
     * @mbggenerated
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order_product.price
     *
     * @param price the value for t_order_product.price
     *
     * @mbggenerated
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order_product.total
     *
     * @return the value of t_order_product.total
     *
     * @mbggenerated
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order_product.total
     *
     * @param total the value for t_order_product.total
     *
     * @mbggenerated
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}






}