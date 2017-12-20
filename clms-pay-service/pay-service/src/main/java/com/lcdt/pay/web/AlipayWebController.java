package com.lcdt.pay.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.pay.config.AlipayContants;
import com.lcdt.pay.model.AlipayTradeOrder;
import com.lcdt.pay.model.OrderStatus;
import com.lcdt.pay.model.PayOrder;
import com.lcdt.pay.service.OrderService;
import com.lcdt.pay.service.TopupService;
import com.lcdt.pay.utils.MoneyNumUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

@Controller
public class AlipayWebController {

    private static final String CHARSET = "UTF-8";

    private String FORMAT = "json";

    @Autowired
    OrderService orderService;

    private Logger logger = LoggerFactory.getLogger(AlipayWebController.class);

    @Autowired
    TopupService topupService;


    /**
     * 新建一个充值订单
     * @return
     */
    @RequestMapping("/topup")
    @ResponseBody
    public PayOrder createTopupPayOrder(Integer amount){
        Long companyId = SecurityInfoGetter.getCompanyId();
        Long userId = SecurityInfoGetter.getUser().getUserId();
        PayOrder topUpOrder = topupService.createTopUpOrder(amount, companyId, userId);
        return topUpOrder;
    }


    @RequestMapping("/pay")
    public void payPage(HttpServletResponse httpResponse, Long orderId) throws IOException {

//        Long orderId = payOrder.getOrderId();
        if (orderId == null) {
            return;
        }
        PayOrder serverOrder = orderService.selectByOrderId(orderId);
        Integer orderStatus = serverOrder.getOrderStatus();
        //判断是否是待支付状态
        if (serverOrder.getOrderStatus() != OrderStatus.PENDINGPAY) {
            throw new RuntimeException("订单状态异常");
        }


        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayContants.getApiGataway(), AlipayContants.getAppId()
                , AlipayContants.getAppPrivateKey(), FORMAT, CHARSET, AlipayContants.getAlipayPublicKey(),AlipayContants.getSignType());

        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        AlipayTradeOrder alipayTradeOrder = new AlipayTradeOrder();
        alipayTradeOrder.setTradeNo(serverOrder.getOrderNo());
        alipayTradeOrder.setTotalAmount(MoneyNumUtil.integerMoneyToString(serverOrder.getOrderAmount()));
        alipayTradeOrder.setProductCode("FAST_INSTANT_TRADE_PAY");
        alipayTradeOrder.setSubject("clms充值");
        alipayTradeOrder.setBody("clms充值");
        alipayRequest.setBizContent(JSONObject.toJSONString(alipayTradeOrder));//填充业务参数


        alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
        alipayRequest.setNotifyUrl("http://domain.com/CallBack/notify_url.jsp");//在公共参数中设置回跳和通知地址
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    @RequestMapping("/alipay/notify")
    public void alipayNotify(HttpServletRequest request) {
        HashMap<String, String> parameterMap = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        if (parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            String parameter = request.getParameter(s);
            parameterMap.put(s, parameter);
        }

        String orderNo = parameterMap.get("out_trade_no");
        String tradeStatus = parameterMap.get("trade_status");
        if (!"TRADE_SUCCESS".equals(tradeStatus)) {
            return;
        }

        boolean b = false;
        try {
            b = AlipaySignature.rsaCheckV1(parameterMap, AlipayContants.getAlipayPublicKey(), CHARSET,AlipayContants.getSignType());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        if (b) {
            PayOrder payOrder = orderService.selectByOrderNo(orderNo);
            if (payOrder == null) {
                logger.error("充值订单数据库没有记录 orderNo:  {} ",payOrder.getOrderNo());
                return;
            }

            orderService.changeToPayFinish(payOrder);
            return;
        }
    }

    @RequestMapping("/alipay/return")
    public void alipayReturn(){

    }

    public AlipayTradePagePayRequest request(AlipayTradeOrder tradeOrder) {
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl(AlipayContants.getReturnUrl());
        alipayRequest.setNotifyUrl(AlipayContants.getNotifyUrl());//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent(tradeOrder.alipayBizContent());
        return alipayRequest;
    }

}
