package com.lcdt.goods.dto;

/**
 * Created by ybq on 2017/8/31.
 */
public class GoodsFeildsTemplateDto {

    private Long goodFieldsId;
    private String templateName;
    private Long companyId;
    private String templateContent;

    public Long getGoodFieldsId() {
        return goodFieldsId;
    }

    public void setGoodFieldsId(Long goodFieldsId) {
        this.goodFieldsId = goodFieldsId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }
}
