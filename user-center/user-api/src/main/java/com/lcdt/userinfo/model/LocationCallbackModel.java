package com.lcdt.userinfo.model;

/**
 * @AUTHOR liuh
 * @DATE 2017-12-27
 */
public class LocationCallbackModel {

    /**手机号*/
    private String mobileno;

    /**
     *  action=open	开通定位,指收到用户的回复同意定位的短信通知
     *  action=reject	移动用户开通定位时,如果用户回复了非y的短信内容，会推送此状态
     *  action=close	关闭定位
     *  action=black	当前手机号被加到企业的黑名单
     *  action=forbid	当前用户被加到平台的黑名单，禁止一切操作
     *  action=noresp24h	电信手机号,如果用户超过24小时未回复,会回调推送此信息
     *  action=unbind24h	电信手机号,如果解绑超过24小时用户未确认，会回调推送此信息
     */
    private String action;

    /**md5(手机号+密钥)就是 md5(mobile+secret)的值*/
    private String sign;

    /**action对应的消息*/
    private String msg;


    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "LocationCallbackModel{" +
                "mobileno='" + mobileno + '\'' +
                ", action='" + action + '\'' +
                ", sign='" + sign + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
