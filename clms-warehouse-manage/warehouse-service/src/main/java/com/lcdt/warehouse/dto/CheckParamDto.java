package com.lcdt.warehouse.dto;

import com.lcdt.warehouse.entity.Check;

import java.util.Date;

/**
 * Created by Administrator on 2018/5/17.
 */
public class CheckParamDto extends  Check{
    /**
     * 创建时间
     */
    private String createDateFrom;


    private String createDateTo;

    /**
     * 完成时间
     */
    private String completeDateFrom;

    private String completeDateTo;
    /**
     * 货物信息
     */
    private String goodsInfo;
    private int pageNo;

    private int pageSize;



    public String getCreateDateFrom() {
        return createDateFrom;
    }

    public void setCreateDateFrom(String createDateFrom) {
        this.createDateFrom = createDateFrom;
    }

    public String getCreateDateTo() {
        return createDateTo;
    }

    public void setCreateDateTo(String createDateTo) {
        this.createDateTo = createDateTo;
    }

    public String getCompleteDateFrom() {
        return completeDateFrom;
    }

    public void setCompleteDateFrom(String completeDateFrom) {
        this.completeDateFrom = completeDateFrom;
    }

    public String getCompleteDateTo() {
        return completeDateTo;
    }

    public void setCompleteDateTo(String completeDateTo) {
        this.completeDateTo = completeDateTo;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }
}
