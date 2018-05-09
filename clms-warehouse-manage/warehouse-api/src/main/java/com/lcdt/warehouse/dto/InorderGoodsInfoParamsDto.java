package com.lcdt.warehouse.dto;

/**
 * Created by lyqishan on 2018/5/9
 */

public class InorderGoodsInfoParamsDto {

    //GoodsInfo
    private Long goodsId;
    private String goodsName;
    /**
     * 商品分类
     */
    private String goodsClassify;
    private String goodsSpec;
    private String goodsCode;
    private String goodsBarcode;
    /**
     * 库存单价
     */
    private Integer goodsPrice;
    /**
     * 商品批次
     */
    private String goodsBatch;
    private Long subItemId;

    /**
     * 最小单位
     */
    private String minUnit;
    /**
     * 多单位json串
     */
    private String multiUnit;

    private Long relationId;

    //InorderGoodsInfo
    private Long inorderId;
    /**
     * 单位
     */
    private String unit;
    /**
     * 换算关系
     */
    private int unitData;

    /**
     * 应收数量
     */
    private Float receivalbeAmount;
    /**
     * 批次
     */
    private String batch;
    /**
     * 入库数量
     */
    private Float inHouseAmount;
    /**
     * 入库价
     */
    private Integer inHousePrice;
    /**
     * 货损
     */
    private Integer damage;
    /**
     * 备注
     */
    private String remark;
    /**
     * 库位id
     */
    private Long strogeLocationId;
    /**
     * 库位编码
     */
    private String strogeLocationCode;


    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsClassify() {
        return goodsClassify;
    }

    public void setGoodsClassify(String goodsClassify) {
        this.goodsClassify = goodsClassify;
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsBarcode() {
        return goodsBarcode;
    }

    public void setGoodsBarcode(String goodsBarcode) {
        this.goodsBarcode = goodsBarcode;
    }

    public Integer getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Integer goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsBatch() {
        return goodsBatch;
    }

    public void setGoodsBatch(String goodsBatch) {
        this.goodsBatch = goodsBatch;
    }

    public Long getSubItemId() {
        return subItemId;
    }

    public void setSubItemId(Long subItemId) {
        this.subItemId = subItemId;
    }

    public String getMinUnit() {
        return minUnit;
    }

    public void setMinUnit(String minUnit) {
        this.minUnit = minUnit;
    }

    public String getMultiUnit() {
        return multiUnit;
    }

    public void setMultiUnit(String multiUnit) {
        this.multiUnit = multiUnit;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public Long getInorderId() {
        return inorderId;
    }

    public void setInorderId(Long inorderId) {
        this.inorderId = inorderId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getUnitData() {
        return unitData;
    }

    public void setUnitData(int unitData) {
        this.unitData = unitData;
    }

    public Float getReceivalbeAmount() {
        return receivalbeAmount;
    }

    public void setReceivalbeAmount(Float receivalbeAmount) {
        this.receivalbeAmount = receivalbeAmount;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Float getInHouseAmount() {
        return inHouseAmount;
    }

    public void setInHouseAmount(Float inHouseAmount) {
        this.inHouseAmount = inHouseAmount;
    }

    public Integer getInHousePrice() {
        return inHousePrice;
    }

    public void setInHousePrice(Integer inHousePrice) {
        this.inHousePrice = inHousePrice;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getStrogeLocationId() {
        return strogeLocationId;
    }

    public void setStrogeLocationId(Long strogeLocationId) {
        this.strogeLocationId = strogeLocationId;
    }

    public String getStrogeLocationCode() {
        return strogeLocationCode;
    }

    public void setStrogeLocationCode(String strogeLocationCode) {
        this.strogeLocationCode = strogeLocationCode;
    }
}
