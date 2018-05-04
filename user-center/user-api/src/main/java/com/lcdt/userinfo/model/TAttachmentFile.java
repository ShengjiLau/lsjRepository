package com.lcdt.userinfo.model;

public class TAttachmentFile {
    private Integer tAttachmentFileId;

    private String tAttachmentFileName;

    private Integer tAttachmentFileType;

    private Integer tAttachmentFileOrder;

    private String tAttachmentFileCode;

    public Integer gettAttachmentFileId() {
        return tAttachmentFileId;
    }

    public void settAttachmentFileId(Integer tAttachmentFileId) {
        this.tAttachmentFileId = tAttachmentFileId;
    }

    public String gettAttachmentFileName() {
        return tAttachmentFileName;
    }

    public void settAttachmentFileName(String tAttachmentFileName) {
        this.tAttachmentFileName = tAttachmentFileName == null ? null : tAttachmentFileName.trim();
    }

    public Integer gettAttachmentFileType() {
        return tAttachmentFileType;
    }

    public void settAttachmentFileType(Integer tAttachmentFileType) {
        this.tAttachmentFileType = tAttachmentFileType;
    }

    public Integer gettAttachmentFileOrder() {
        return tAttachmentFileOrder;
    }

    public void settAttachmentFileOrder(Integer tAttachmentFileOrder) {
        this.tAttachmentFileOrder = tAttachmentFileOrder;
    }

    public String gettAttachmentFileCode() {
        return tAttachmentFileCode;
    }

    public void settAttachmentFileCode(String tAttachmentFileCode) {
        this.tAttachmentFileCode = tAttachmentFileCode == null ? null : tAttachmentFileCode.trim();
    }
}