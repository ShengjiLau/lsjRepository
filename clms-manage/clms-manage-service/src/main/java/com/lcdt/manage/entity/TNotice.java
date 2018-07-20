package com.lcdt.manage.entity;

import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author code generate
 * @since 2018-07-12
 */
public class TNotice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "notice_id", type = IdType.AUTO)
    private Long noticeId;
    private Long categoryId;
    /**
     * 标题
     */
    private String noticeTitle;
    /**
     * 封面图片
     */
    private String coverImageUrl;
    /**
     * 署名
     */
    private String publisher;
    /**
     * 正文
     */
    private String noticeContent;
    /**
     * 摘要
     */
    private String noticeSummary;
    /**
     * 发布时间
     */
    private String publishTime;
    /**
     * 创建时间
     */
    private Date createTime;
    private Long createUserId;
    private String createUserName;
    private  Integer isTop;

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getNoticeSummary() {
        return noticeSummary;
    }

    public void setNoticeSummary(String noticeSummary) {
        this.noticeSummary = noticeSummary;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    @Override
    public String toString() {
        return "TNotice{" +
        ", noticeId=" + noticeId +
        ", categoryId=" + categoryId +
        ", noticeTitle=" + noticeTitle +
        ", coverImageUrl=" + coverImageUrl +
        ", publisher=" + publisher +
        ", noticeContent=" + noticeContent +
        ", noticeSummary=" + noticeSummary +
        ", publishTime=" + publishTime +
        ", createTime=" + createTime +
        ", createUserId=" + createUserId +
        ", createUserName=" + createUserName +
                "}"+
                ", isTop="+isTop;
    }
}
