package com.lcdt.traffic.model;

import com.lcdt.converter.ResponseData;

import java.util.Date;

public class SnatchGoodsDetail implements java.io.Serializable,ResponseData {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_snatch_goods_detail.snatch_goods_detail_id
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    private Long snatchGoodsDetailId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_snatch_goods_detail.snatch_goods_id
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    private Long snatchGoodsId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_snatch_goods_detail.plan_detail_id
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    private Long planDetailId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_snatch_goods_detail.offer_price
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    private Float offerPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_snatch_goods_detail.offer_total
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    private Float offerTotal;

    public Float getLastOffer() {
        return lastOffer;
    }

    public void setLastOffer(Float lastOffer) {
        this.lastOffer = lastOffer;
    }

    private Float lastOffer;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_snatch_goods_detail.offer_remark
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    private String offerRemark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_snatch_goods_detail.create_id
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    private Long createId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_snatch_goods_detail.create_name
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    private String createName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_snatch_goods_detail.create_date
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    private Date createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_snatch_goods_detail.update_id
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    private Long updateId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_snatch_goods_detail.update_name
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    private String updateName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_snatch_goods_detail.update_time
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_snatch_goods_detail.is_deleted
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    private Short isDeleted;


    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    private Long amount; //重新报价数量

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_snatch_goods_detail.snatch_goods_detail_id
     *
     * @return the value of t_snatch_goods_detail.snatch_goods_detail_id
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public Long getSnatchGoodsDetailId() {
        return snatchGoodsDetailId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_snatch_goods_detail.snatch_goods_detail_id
     *
     * @param snatchGoodsDetailId the value for t_snatch_goods_detail.snatch_goods_detail_id
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public void setSnatchGoodsDetailId(Long snatchGoodsDetailId) {
        this.snatchGoodsDetailId = snatchGoodsDetailId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_snatch_goods_detail.snatch_goods_id
     *
     * @return the value of t_snatch_goods_detail.snatch_goods_id
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public Long getSnatchGoodsId() {
        return snatchGoodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_snatch_goods_detail.snatch_goods_id
     *
     * @param snatchGoodsId the value for t_snatch_goods_detail.snatch_goods_id
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public void setSnatchGoodsId(Long snatchGoodsId) {
        this.snatchGoodsId = snatchGoodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_snatch_goods_detail.plan_detail_id
     *
     * @return the value of t_snatch_goods_detail.plan_detail_id
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public Long getPlanDetailId() {
        return planDetailId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_snatch_goods_detail.plan_detail_id
     *
     * @param planDetailId the value for t_snatch_goods_detail.plan_detail_id
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public void setPlanDetailId(Long planDetailId) {
        this.planDetailId = planDetailId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_snatch_goods_detail.offer_price
     *
     * @return the value of t_snatch_goods_detail.offer_price
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public Float getOfferPrice() {
        return offerPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_snatch_goods_detail.offer_price
     *
     * @param offerPrice the value for t_snatch_goods_detail.offer_price
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public void setOfferPrice(Float offerPrice) {
        this.offerPrice = offerPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_snatch_goods_detail.offer_total
     *
     * @return the value of t_snatch_goods_detail.offer_total
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public Float getOfferTotal() {
        return offerTotal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_snatch_goods_detail.offer_total
     *
     * @param offerTotal the value for t_snatch_goods_detail.offer_total
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public void setOfferTotal(Float offerTotal) {
        this.offerTotal = offerTotal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_snatch_goods_detail.offer_remark
     *
     * @return the value of t_snatch_goods_detail.offer_remark
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public String getOfferRemark() {
        return offerRemark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_snatch_goods_detail.offer_remark
     *
     * @param offerRemark the value for t_snatch_goods_detail.offer_remark
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public void setOfferRemark(String offerRemark) {
        this.offerRemark = offerRemark == null ? null : offerRemark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_snatch_goods_detail.create_id
     *
     * @return the value of t_snatch_goods_detail.create_id
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public Long getCreateId() {
        return createId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_snatch_goods_detail.create_id
     *
     * @param createId the value for t_snatch_goods_detail.create_id
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_snatch_goods_detail.create_name
     *
     * @return the value of t_snatch_goods_detail.create_name
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public String getCreateName() {
        return createName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_snatch_goods_detail.create_name
     *
     * @param createName the value for t_snatch_goods_detail.create_name
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_snatch_goods_detail.create_date
     *
     * @return the value of t_snatch_goods_detail.create_date
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_snatch_goods_detail.create_date
     *
     * @param createDate the value for t_snatch_goods_detail.create_date
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_snatch_goods_detail.update_id
     *
     * @return the value of t_snatch_goods_detail.update_id
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public Long getUpdateId() {
        return updateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_snatch_goods_detail.update_id
     *
     * @param updateId the value for t_snatch_goods_detail.update_id
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_snatch_goods_detail.update_name
     *
     * @return the value of t_snatch_goods_detail.update_name
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public String getUpdateName() {
        return updateName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_snatch_goods_detail.update_name
     *
     * @param updateName the value for t_snatch_goods_detail.update_name
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public void setUpdateName(String updateName) {
        this.updateName = updateName == null ? null : updateName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_snatch_goods_detail.update_time
     *
     * @return the value of t_snatch_goods_detail.update_time
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_snatch_goods_detail.update_time
     *
     * @param updateTime the value for t_snatch_goods_detail.update_time
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_snatch_goods_detail.is_deleted
     *
     * @return the value of t_snatch_goods_detail.is_deleted
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_snatch_goods_detail.is_deleted
     *
     * @param isDeleted the value for t_snatch_goods_detail.is_deleted
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }
}