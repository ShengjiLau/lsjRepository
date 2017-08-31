package com.lcdt.goods.dto;

import java.util.List;

/**
 * Created by ybq on 2017/8/31.
 */
public class GoodsDto implements  java.io.Serializable {

    private Long goodsId;
    private String goodsCode;
    private String goodsName;
    private Double price;
    private String spec;
    private Long companyId;

    private List<GoodsSkuDto>  goodsSkuDtoList;


    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public List<GoodsSkuDto> getGoodsSkuDtoList() {
        return goodsSkuDtoList;
    }

    public void setGoodsSkuDtoList(List<GoodsSkuDto> goodsSkuDtoList) {
        this.goodsSkuDtoList = goodsSkuDtoList;
    }
}
