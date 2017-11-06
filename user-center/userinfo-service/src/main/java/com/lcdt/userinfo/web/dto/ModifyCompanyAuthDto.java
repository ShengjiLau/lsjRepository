package com.lcdt.userinfo.web.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by ss on 2017/11/6.
 */
public class ModifyCompanyAuthDto {

	@ApiModelProperty("身份证正面")
	private String identityFront;

	@ApiModelProperty("身份证反面")
	private String identityBack;

	private String threeCertificates;

	private String yingyezhizhao;

	private String zuzhijigou;

	private String shuiwudengji;

	private String yinghangkaihu;

	private String yingyunxuke;

	public String getIdentityFront() {
		return identityFront;
	}

	public void setIdentityFront(String identityFront) {
		this.identityFront = identityFront;
	}

	public String getIdentityBack() {
		return identityBack;
	}

	public void setIdentityBack(String identityBack) {
		this.identityBack = identityBack;
	}

	public String getThreeCertificates() {
		return threeCertificates;
	}

	public void setThreeCertificates(String threeCertificates) {
		this.threeCertificates = threeCertificates;
	}

	public String getYingyezhizhao() {
		return yingyezhizhao;
	}

	public void setYingyezhizhao(String yingyezhizhao) {
		this.yingyezhizhao = yingyezhizhao;
	}

	public String getZuzhijigou() {
		return zuzhijigou;
	}

	public void setZuzhijigou(String zuzhijigou) {
		this.zuzhijigou = zuzhijigou;
	}

	public String getShuiwudengji() {
		return shuiwudengji;
	}

	public void setShuiwudengji(String shuiwudengji) {
		this.shuiwudengji = shuiwudengji;
	}

	public String getYinghangkaihu() {
		return yinghangkaihu;
	}

	public void setYinghangkaihu(String yinghangkaihu) {
		this.yinghangkaihu = yinghangkaihu;
	}

	public String getYingyunxuke() {
		return yingyunxuke;
	}

	public void setYingyunxuke(String yingyunxuke) {
		this.yingyunxuke = yingyunxuke;
	}
}
