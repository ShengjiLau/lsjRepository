package com.lcdt.web.auth;

/**
 * Created by ss on 2017/8/10.
 */
public class CaptchaAndTime {

	private String captcha;
	private long createTime;

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
}
