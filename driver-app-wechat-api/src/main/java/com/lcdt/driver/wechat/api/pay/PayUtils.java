package com.lcdt.driver.wechat.api.pay;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.pay.model.PayOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;

@Service
public class PayUtils {

    private static Logger logger = LoggerFactory.getLogger(PayUtils.class);

    public static String getOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + WxpayConstant.APP_ID +
                "&secret=" + WxpayConstant.APP_SECRET + "&js_code=" + code + "&grant_type=authorization_code";


        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, null, String.class);

        String body = stringResponseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(body);
        if(stringResponseEntity.getStatusCode().is2xxSuccessful()) {

            if(jsonObject.get("errcode") != null) {
                logger.error("getOpenId returns errcode: " + jsonObject.get("errcode"));
                return "";
            } else {
                return jsonObject.get("openid").toString();
            }
        }
        return "";
    }


    @Autowired
    HttpsRequest request;

    /**
     * 调用统一下单接口
     * @param openId
     */
    public  String unifiedOrder(String openId, String clientIP, String randomNonceStr, PayOrder order) {

        try {

            String url = WxpayConstant.URL_UNIFIED_ORDER;

            PayInfo payInfo = createPayInfo(openId, clientIP, randomNonceStr,order);
            String md5 = getSign(payInfo);
            payInfo.setSign(md5);


            String xml = CommonUtils.payInfoToXML(payInfo);
//            xml = xml.replace("__", "_").replace("<![CDATA[1]]>", "1");
            xml = xml.replace("__", "_").replace("<![CDATA[", "").replace("]]>", "");
            logger.info("统一下单 请求参数 \n {}",xml);
            RestTemplate restTemplate = new RestTemplate();

            String buffer = request.sendPost(WxpayConstant.URL_UNIFIED_ORDER, payInfo);
//            StringBuffer buffer = HttpUtils.httpsRequest(url, "POST", xml);
            logger.info("统一下单 返回内容" + buffer.toString());
            Map<String, String> result = CommonUtils.parseXml(buffer.toString());


            String return_code = result.get("return_code");
            if(StringUtils.isNotEmpty(return_code) && return_code.equals("SUCCESS")) {
                String return_msg = result.get("return_msg");
                if(StringUtils.isNotEmpty(return_msg) && !return_msg.equals("OK")) {
                    logger.error("统一下单错误！");
                    return "";
                }

                String prepay_Id = result.get("prepay_id");
                return prepay_Id;

            } else {
                return "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    private static PayInfo createPayInfo(String openId, String clientIP, String randomNonceStr,PayOrder order) {

        Date date = new Date();
        String timeStart = TimeUtils.getFormatTime(date, WxpayConstant.TIME_FORMAT);
        String timeExpire = TimeUtils.getFormatTime(TimeUtils.addDay(date, WxpayConstant.TIME_EXPIRE), WxpayConstant.TIME_FORMAT);

        String randomOrderId = CommonUtils.getRandomOrderId();
        logger.info("create payorder"+order);
        PayInfo payInfo = new PayInfo();
        payInfo.setAppid(WxpayConstant.APP_ID);
        payInfo.setMch_id(WxpayConstant.MCH_ID);
        payInfo.setDevice_info("WEB");
        payInfo.setNonce_str(randomNonceStr);
        payInfo.setSign_type("MD5");  //默认即为MD5
        payInfo.setBody(order.getOrderDes());
        payInfo.setOut_trade_no(order.getOrderNo());
        payInfo.setTotal_fee(order.getOrderAmount());
        payInfo.setSpbill_create_ip(clientIP);
        payInfo.setTime_start(timeStart);
        payInfo.setTime_expire(timeExpire);

        payInfo.setNotify_url(WxpayConstant.URL_NOTIFY);

        payInfo.setTrade_type("JSAPI");
        payInfo.setLimit_pay("no_credit");
        payInfo.setOpenid(openId);

        return payInfo;
    }

    private static String getSign(PayInfo payInfo) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("appid=" + payInfo.getAppid())
                .append("&body=" + payInfo.getBody())
                .append("&device_info=" + payInfo.getDevice_info())
                .append("&limit_pay=" + payInfo.getLimit_pay())
                .append("&mch_id=" + payInfo.getMch_id())
                .append("&nonce_str=" + payInfo.getNonce_str())
                .append("&notify_url=" + payInfo.getNotify_url())
                .append("&openid=" + payInfo.getOpenid())
                .append("&out_trade_no=" + payInfo.getOut_trade_no())
                .append("&sign_type=" + payInfo.getSign_type())
                .append("&spbill_create_ip=" + payInfo.getSpbill_create_ip())
                .append("&time_expire=" + payInfo.getTime_expire())
                .append("&time_start=" + payInfo.getTime_start())
                .append("&total_fee=" + payInfo.getTotal_fee())
                .append("&trade_type=" + payInfo.getTrade_type())
                .append("&key=" + WxpayConstant.APP_KEY);
        logger.info("签名前 编码 \n {}",sb.toString());
        return CommonUtils.getMD5(sb.toString().trim()).toUpperCase();
    }


    public static void main(String[] args) throws Exception {
        PayInfo payInfo = new PayInfo();
        payInfo.setNonce_str(RandomUtils.generateMixString(12));
        String sign = getSign(payInfo);
        System.out.println(sign);
    }


}
