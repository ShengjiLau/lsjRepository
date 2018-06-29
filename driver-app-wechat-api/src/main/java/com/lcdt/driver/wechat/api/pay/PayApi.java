package com.lcdt.driver.wechat.api.pay;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcdt.pay.model.PayOrder;
import com.lcdt.pay.service.OrderService;
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

    @ResponseBody
    @RequestMapping(value = "/prepay", produces = "text/html;charset=UTF-8")
    public JSONObject prepay(String code, HttpServletRequest request, Long orderid){

        Map<String,String> map = new HashMap();
        map.put("appId", WxpayConstant.APP_ID);
        map.put("timeStamp", System.currentTimeMillis() / 1000 + "");
        map.put("signType", "MD5");
        PayOrder payOrder = orderService.selectByOrderId(orderid);
        ObjectMapper objectMapper = new ObjectMapper();
        String openId = PayUtils.getOpenId(code);


        JSONObject responseJson = new JSONObject();

        if (StringUtils.isEmpty(openId)) {
            responseJson.put("result", false);
            responseJson.put("message","获取openId失败");
            return responseJson;
        }else{
            openId = openId.replace("\"", "").trim();
            String clientIp = CommonUtils.getClientIp(request);
            String randomNonceStr = RandomUtils.generateMixString(32);
            String prepayId = PayUtils.unifiedOrder(openId, clientIp, randomNonceStr,payOrder);

            if (StringUtils.isEmpty(prepayId)){
                responseJson.put("result", false);
                responseJson.put("message","获取prepayId失败");
                return responseJson;
            }else{
                map.put("prepayId", prepayId);
                map.put("nonceStr", randomNonceStr);
                responseJson.put("result", true);
                responseJson.put("message", "获取成功");
                String s = mapToKVStr(map);
                responseJson.put("sign", s);
                return responseJson;
            }
        }
    }


    private String mapToKVStr(Map<String,String> map) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append('&');
        }
        return stringBuilder.toString();
    }



}
