package com.lcdt.driver.wechat.api.pay;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcdt.pay.model.PayOrder;
import com.lcdt.pay.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PayApi {

    @Reference
    OrderService orderService;
    @Autowired
    PayUtils payUtils;

    private Logger logger = LoggerFactory.getLogger(PayApi.class);

    @ResponseBody
    @RequestMapping(value = "/prepay", produces = "text/html;charset=UTF-8")
    public JSONObject prepay(String code, HttpServletRequest request, Long orderid) throws Exception {

        Map<String,String> map = new HashMap();
        map.put("appId", WxpayConstant.APP_ID);
        map.put("timeStamp", System.currentTimeMillis() / 1000 + "");
        map.put("signType", "MD5");
        PayOrder payOrder = orderService.selectByOrderId(orderid);
        String openId = PayUtils.getOpenId(code);
        JSONObject responseJson = new JSONObject();
        if (StringUtils.isEmpty(openId)) {
            responseJson.put("result", false);
            responseJson.put("message","获取openId失败");
            return responseJson;
        }else{
            openId = openId.replace("\"", "").trim();
            String clientIp = CommonUtils.getClientIp(request);
            clientIp = "112.229.127.155";
            String randomNonceStr = RandomUtils.generateMixString(32);
            String prepayId = payUtils.unifiedOrder(openId, clientIp, randomNonceStr,payOrder);
            if (StringUtils.isEmpty(prepayId)){
                responseJson.put("result", false);
                responseJson.put("message","获取prepayId失败");
                return responseJson;
            }else{
                map.put("package", "prepay_id="+prepayId);
                map.put("nonceStr", randomNonceStr);

                responseJson.put("result", true);
                responseJson.put("message", "获取成功");
                String s = mapToKVStr(map);
                map.put("paySign", s);
                map.remove("appId");
                responseJson.put("data", map);
                return responseJson;
            }
        }
    }


    private String mapToKVStr(Map<String,String> map) throws Exception {

        StringBuffer sb = new StringBuffer();
        sb.append("appId=" + map.get("appId"))
                .append("&nonceStr=" + map.get("nonceStr"))
                .append("&package=" + map.get("package"))
                .append("&signType=" + "MD5")
                .append("&timeStamp=" + map.get("timeStamp"))
                .append("&key=" + WxpayConstant.APP_KEY);
//        System.out.println();
        logger.info("小程序支付 签名字符串:  {} ",sb.toString());
        return CommonUtils.getMD5(sb.toString().trim()).toUpperCase();
    }



}
