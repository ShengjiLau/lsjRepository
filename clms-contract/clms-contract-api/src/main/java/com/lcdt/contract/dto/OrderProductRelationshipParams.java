package com.lcdt.contract.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lyqishan on 2018/8/14
 */

public class OrderProductRelationshipParams {

    @ApiModelProperty(value="关系id")
    private Long relationshipId;
    @ApiModelProperty(value="销售单商品id",required = true)
    private Long opId;
    @ApiModelProperty(value="商品管理商品id")
    private Long goodsId;
    @ApiModelProperty(value="创建人",hidden = true)
    private String createName;
    @ApiModelProperty(value="更新人",hidden = true)
    private String updateName;
    @ApiModelProperty(value="企业",hidden = true)
    private Long companyId;

    public Long getRelationshipId() {
        return relationshipId;
    }

    public OrderProductRelationshipParams setRelationshipId(Long relationshipId) {
        this.relationshipId = relationshipId;
        return this;
    }

    public Long getOpId() {
        return opId;
    }

    public OrderProductRelationshipParams setOpId(Long opId) {
        this.opId = opId;
        return this;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public OrderProductRelationshipParams setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
        return this;
    }

    public String getCreateName() {
        return createName;
    }

    public OrderProductRelationshipParams setCreateName(String createName) {
        this.createName = createName;
        return this;
    }

    public String getUpdateName() {
        return updateName;
    }

    public OrderProductRelationshipParams setUpdateName(String updateName) {
        this.updateName = updateName;
        return this;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public OrderProductRelationshipParams setCompanyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }
}
