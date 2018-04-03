package com.lcdt.pay.wechatpay;

import java.io.Serializable;

public class UnifiedorderBean implements Serializable{


	private String appid;//公众号
	private String mch_id;//商户号
	private String device_info;//终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
	private String nonce_str;//随机字符串，不长于32位。推荐随机数生成算法
	private String sign;//签名，详见签名生成算法
	private String sign_type;//签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
	private String body;//商品简单描述，该字段须严格按照规范传递
	private String out_trade_no;//商户系统内部的订单号,32个字符内、可包含字母
	private String fee_type;//符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见
	private Integer total_fee;//订单总金额，单位为分，
	private String spbill_create_ip;//APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
	private String notify_url;//接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
	private String trade_type;//取值如下：JSAPI，NATIVE，APP
	private String product_id;//trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
	private String openid;//trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取
	private String attach;//附加数据
	
	private UnifiedorderBean() {
		
	}


	public static UnifiedorderBean defaultOrderBean(String orderId,Integer total_fee,String ip,String notifyUrl){
		UnifiedorderBean unifiedorderBean = new UnifiedorderBean("web", "大驼队clms账户充值", orderId, total_fee, ip, "NATIVE",notifyUrl);
		try {
			String sign = Signature.getSign(unifiedorderBean);
			unifiedorderBean.setSign(sign);
			return unifiedorderBean;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}



	public UnifiedorderBean(String device_info,
			String body,String out_trade_no,Integer total_fee,String spbill_create_ip,
			String trade_type,String notifyUrl){
		this.appid = Configure.getAppid();
		this.mch_id = Configure.getMchid();
		this.device_info = device_info;
		this.nonce_str = RandomStringGenerator.getRandomStringByLength(32);
		this.body = body;
		this.out_trade_no = out_trade_no;
		this.total_fee = total_fee;
		this.spbill_create_ip = spbill_create_ip;
		this.notify_url = notifyUrl;
		this.trade_type = trade_type;
		//this.openid = out_trade_no;
		
	}
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mchId) {
		mch_id = mchId;
	}
	public String getDevice_info() {
		return device_info;
	}
	public void setDevice_info(String deviceInfo) {
		device_info = deviceInfo;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonceStr) {
		nonce_str = nonceStr;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String signType) {
		sign_type = signType;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String outTradeNo) {
		out_trade_no = outTradeNo;
	}
	public String getFee_type() {
		return fee_type;
	}
	public void setFee_type(String feeType) {
		fee_type = feeType;
	}
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}
	public void setSpbill_create_ip(String spbillCreateIp) {
		spbill_create_ip = spbillCreateIp;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notifyUrl) {
		notify_url = notifyUrl;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String tradeType) {
		trade_type = tradeType;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String productId) {
		product_id = productId;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public Integer getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(Integer totalFee) {
		total_fee = totalFee;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	
	
}
