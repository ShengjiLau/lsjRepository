package com.lcdt.userinfo.model;

public class Dictionary {
    private Integer dictionaryId;

    private String dictionaryKey;

    private String dictionaryValue;

    private String dictionaryCode;

    private String dictionaryType;

    private String dictionaryStatus;

    private String dictionaryCodeValue;

    public Integer getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(Integer dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public String getDictionaryKey() {
        return dictionaryKey;
    }

    public void setDictionaryKey(String dictionaryKey) {
        this.dictionaryKey = dictionaryKey == null ? null : dictionaryKey.trim();
    }

    public String getDictionaryValue() {
        return dictionaryValue;
    }

    public void setDictionaryValue(String dictionaryValue) {
        this.dictionaryValue = dictionaryValue == null ? null : dictionaryValue.trim();
    }

    public String getDictionaryCode() {
        return dictionaryCode;
    }

    public void setDictionaryCode(String dictionaryCode) {
        this.dictionaryCode = dictionaryCode == null ? null : dictionaryCode.trim();
    }

    public String getDictionaryType() {
        return dictionaryType;
    }

    public void setDictionaryType(String dictionaryType) {
        this.dictionaryType = dictionaryType == null ? null : dictionaryType.trim();
    }

    public String getDictionaryStatus() {
        return dictionaryStatus;
    }

    public void setDictionaryStatus(String dictionaryStatus) {
        this.dictionaryStatus = dictionaryStatus == null ? null : dictionaryStatus.trim();
    }

    public String getDictionaryCodeValue() {
        return dictionaryCodeValue;
    }

    public void setDictionaryCodeValue(String dictionaryCodeValue) {
        this.dictionaryCodeValue = dictionaryCodeValue;
    }
}