package com.lcdt.userinfo.model;

public class TAttachmentClassify {
    private Integer tAttachmentClassifyId;

    private String tAttachmentClassifyName;

    private Integer tAttachmentClassifyOrder;

    private String tAttachmentFileCode;

    private Integer tAttachmentClassifyMaxnum;

    private Integer tAttachmentFileType;

    public Integer gettAttachmentFileType() {
        return tAttachmentFileType;
    }

    public void settAttachmentFileType(Integer tAttachmentFileType) {
        this.tAttachmentFileType = tAttachmentFileType;
    }

    public Integer gettAttachmentClassifyId() {
        return tAttachmentClassifyId;
    }

    public void settAttachmentClassifyId(Integer tAttachmentClassifyId) {
        this.tAttachmentClassifyId = tAttachmentClassifyId;
    }

    public String gettAttachmentClassifyName() {
        return tAttachmentClassifyName;
    }

    public void settAttachmentClassifyName(String tAttachmentClassifyName) {
        this.tAttachmentClassifyName = tAttachmentClassifyName == null ? null : tAttachmentClassifyName.trim();
    }

    public Integer gettAttachmentClassifyOrder() {
        return tAttachmentClassifyOrder;
    }

    public void settAttachmentClassifyOrder(Integer tAttachmentClassifyOrder) {
        this.tAttachmentClassifyOrder = tAttachmentClassifyOrder;
    }

    public String gettAttachmentFileCode() {
        return tAttachmentFileCode;
    }

    public void settAttachmentFileCode(String tAttachmentFileCode) {
        this.tAttachmentFileCode = tAttachmentFileCode == null ? null : tAttachmentFileCode.trim();
    }

    public Integer gettAttachmentClassifyMaxnum() {
        return tAttachmentClassifyMaxnum;
    }

    public void settAttachmentClassifyMaxnum(Integer tAttachmentClassifyMaxnum) {
        this.tAttachmentClassifyMaxnum = tAttachmentClassifyMaxnum;
    }
}