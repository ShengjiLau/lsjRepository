package com.lcdt.pay.web;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.pay.dao.PayOrderMapper;
import com.lcdt.pay.model.PayOrder;
import com.lcdt.pay.service.OrderService;
import com.lcdt.pay.service.TopupService;
import com.lcdt.pay.service.impl.OrderServiceImpl;
import com.lcdt.pay.wechatpay.Signature;
import com.lcdt.pay.wechatpay.XMLParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Controller
public class WechatPayController {

    @Autowired
    TopupService topupService;

    @Autowired
    OrderService orderService;

    @Autowired
    PayOrderMapper mapper;

    private Logger logger = LoggerFactory.getLogger(WechatPayController.class);


    @RequestMapping("/wechatpaynotify")
    @ResponseBody
    public String wechatPay(HttpServletRequest request){
        //orderId 充值的订单编号

        logger.info("微信支付回调开始 ");


        JSONObject jo = new JSONObject();
        jo.put("return_code", "FAIL");
        try {
            Map<String, Object> map = getCallbackParams(request);
            if(map != null){
                if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
                    String orderId = map.get("out_trade_no").toString();
                    //这里写成功后的业务逻辑
                    List<PayOrder> payOrders = mapper.selectByOrderNo(orderId);
                    if (!CollectionUtils.isEmpty(payOrders)) {
                        PayOrder payOrder = payOrders.get(0);
                        orderService.changeToPayFinish(payOrder, OrderServiceImpl.PayType.WECHATPAY);
                        logger.info("微信回调处理成功");
                        jo.put("return_code", "SUCCESS");
                        jo.put("return_msg", "OK");
                    }
                }
            }else{
                logger.info("微信回调 签名验证失败");

                jo.put("return_msg", "签名验证失败!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jo.toString();

    }

    public Map<String, Object> getCallbackParams(HttpServletRequest request)
            throws Exception {
        InputStream inStream = request.getInputStream();
        if(inStream != null){

            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }

            outSteam.close();
            inStream.close();
            String result = new String(outSteam.toByteArray(), "utf-8");
            boolean usedflag = Signature.checkIsSignValidFromResponseString(result);
            if(!usedflag){//可能被第三方篡改过
                return null;
            }else{
                return XMLParser.getMapFromXML(result);
            }
        }
        return null;
    }

}
