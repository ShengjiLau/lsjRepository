package com.lcdt.login.ticket;

import java.io.Serializable;

/**
 * Created by ss on 2017/9/15.
 */
public class TicketBean implements Serializable{

	private Long createTime; //创建时间

	private Long validateTime; //有效时间

	private String clientIp;//客户端ip

	private Long userId;//登陆用户id

	private Long companyId; //用户选择公司id


	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getValidateTime() {
		return validateTime;
	}

	public void setValidateTime(Long validateTime) {
		this.validateTime = validateTime;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
