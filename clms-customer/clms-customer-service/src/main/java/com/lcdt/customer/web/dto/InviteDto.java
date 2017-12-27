package com.lcdt.customer.web.dto;

import com.lcdt.converter.ResponseData;

public class InviteDto implements ResponseData {

    String inviteContent;

    Long inviteId;

    public String getInviteContent() {
        return inviteContent;
    }

    public void setInviteContent(String inviteContent) {
        this.inviteContent = inviteContent;
    }

    public Long getInviteId() {
        return inviteId;
    }

    public void setInviteId(Long inviteId) {
        this.inviteId = inviteId;
    }
}
