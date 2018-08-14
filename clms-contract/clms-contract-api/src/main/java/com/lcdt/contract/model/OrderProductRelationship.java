package com.lcdt.contract.model;

import java.io.Serializable;
import java.util.Date;

public class OrderProductRelationship implements Serializable {
    /**
     * 关系id
     */
    private Long relationshipId;
    /**
     * 销售单商品id
     */
    private Long opId;
    /**
     * 商品管理商品id
     */
    private Long goodsId;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 创建人
     */
    private String createName;
    /**
     * 更新时间
     */
    private Date updateDate;
    /**
     * 更新人
     */
    private String updateName;
    /**
     * 企业id
     */
    private Long companyId;

    public Long getRelationshipId() {
        return relationshipId;
    }

    public OrderProductRelationship setRelationshipId(Long relationshipId) {
        this.relationshipId = relationshipId;
        return this;
    }

    public Long getOpId() {
        return opId;
    }

    public OrderProductRelationship setOpId(Long opId) {
        this.opId = opId;
        return this;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public OrderProductRelationship setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public OrderProductRelationship setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public String getCreateName() {
        return createName;
    }

    public OrderProductRelationship setCreateName(String createName) {
        this.createName = createName;
        return this;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public OrderProductRelationship setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public String getUpdateName() {
        return updateName;
    }

    public OrderProductRelationship setUpdateName(String updateName) {
        this.updateName = updateName;
        return this;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public OrderProductRelationship setCompanyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }
}