package com.lcdt.pay.web;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.pay.config.AlipayContants;
import com.lcdt.pay.model.AlipayTradeOrder;
import com.lcdt.pay.model.OrderStatus;
import com.lcdt.pay.model.PayOrder;
import com.lcdt.pay.service.OrderService;
import com.lcdt.pay.service.TopupService;
import com.lcdt.pay.service.impl.OrderServiceImpl;
import com.lcdt.pay.utils.MoneyNumUtil;
import com.lcdt.pay.utils.WechatPayApi;
import com.lcdt.userinfo.model.User;
import com.lcdt.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
public class AlipayWebController {

    private static final String CHARSET = "UTF-8";

    private String FORMAT = "json";

    @Autowired
    OrderService orderService;

    private Logger logger = LoggerFactory.getLogger(AlipayWebController.class);

    @Autowired
    TopupService topupService;

    @Autowired
    WechatPayApi wechatPayApi;


    /**
     * 新建一个充值订单
     * @return
     */
    @RequestMapping(value = "/topup",method = RequestMethod.POST)
    @ResponseBody
    public PayOrder createTopupPayOrder(Integer amount){
        //这里的单位是元
        Long companyId = SecurityInfoGetter.getCompanyId();
        Long userId = SecurityInfoGetter.getUser().getUserId();
        User user = SecurityInfoGetter.getUser();

        //amount单位是元，order中单位是分
        amount = amount * 100;
        PayOrder topUpOrder = topupService.createTopUpOrder(amount, companyId, user);
        return topUpOrder;
    }


    @RequestMapping("/alipay")
    public void payPage(HttpServletResponse httpResponse, Long orderId) throws IOException {
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
                , AlipayContants.getAppPrivateKey(), FORMAT, CHARSET, AlipayContants.getAlipayPublicKey(),"RSA2");

        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        AlipayTradeOrder alipayTradeOrder = new AlipayTradeOrder();
        alipayTradeOrder.setTradeNo(serverOrder.getOrderNo());
        alipayTradeOrder.setTotalAmount(MoneyNumUtil.integerMoneyToString(serverOrder.getOrderAmount()));
        alipayTradeOrder.setProductCode("FAST_INSTANT_TRADE_PAY");
        alipayTradeOrder.setSubject("clms充值");
        alipayTradeOrder.setBody("clms充值");
        alipayRequest.setBizContent(JSONObject.toJSONString(alipayTradeOrder));//填充业务参数
        alipayRequest.setReturnUrl(AlipayContants.getReturnUrl());
        alipayRequest.setNotifyUrl(AlipayContants.getNotifyUrl());//在公共参数中设置回跳和通知地址
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
    @ResponseBody
    public String alipayNotify(HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException {

        HashMap<String, String> parameterMap = new HashMap<>();

        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            parameterMap.put(name, valueStr);

        }
        logger.info("alipay 异步请求 {}",request.getRequestURI()+request.getQueryString());

        boolean signVerified = AlipaySignature.rsaCheckV1(parameterMap, AlipayContants.getAlipayPublicKey(), CHARSET, "RSA2");

        if (signVerified) {
            String orderNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //支付宝交易号
            String trade_orderNono = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //交易状态
            String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
            logger.info("接受支付宝 支付推送 交易订单号：{}  支付状态 {} ", orderNo, tradeStatus);

            if (!"TRADE_SUCCESS".equals(tradeStatus)) {
                return "fail";
            }

            PayOrder payOrder = orderService.selectByOrderNo(orderNo);
            if (payOrder == null) {
                logger.error("充值订单数据库没有记录 orderNo:  {} ", payOrder.getOrderNo());
                return "fail";
            }

            orderService.changeToPayFinish(payOrder, OrderServiceImpl.PayType.ALIPAY);
            return "success";
        }else{
            logger.info("异步回调延签失败");
        }

        return "fail";

    }

    /**
     * 支付宝 验签
     *
     * @return
     */
    @RequestMapping("/alipay/returnurl")
    @ResponseBody
    public String alipayReturn(HttpServletRequest request) throws UnsupportedEncodingException {

        HashMap<String, String> parameterMap = new HashMap<>();

        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            parameterMap.put(name, valueStr);

        }

        String orderNo = parameterMap.get("out_trade_no");
        String tradeStatus = parameterMap.get("trade_status");
        try {
            boolean b = AlipaySignature.rsaCheckV1(parameterMap, AlipayContants.getAlipayPublicKey(), CHARSET, "RSA2");
            if (b) {
                PayOrder payOrder = orderService.selectByOrderNo(orderNo);

                if (tradeStatus == null) {
                    return "success";
                }

                if (tradeStatus.equals("TRADE_SUCCESS")) {
                    if (payOrder == null) {
                        logger.error("充值订单数据库没有记录 orderNo:  {} ",payOrder.getOrderNo());
                        return "fail";
                    }
                    orderService.changeToPayFinish(payOrder, OrderServiceImpl.PayType.ALIPAY);
                }
                return "success";
            }else{
                return "fail";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return "fail";
    }


    public void checkAlipayOrder(){



        return;
    }

    @RequestMapping("/wechatqrcode")
    public void wechatQrcode(HttpServletResponse response,Long orderId,HttpServletRequest request){
        PayOrder serverOrder = orderService.selectByOrderId(orderId);
        Integer orderStatus = serverOrder.getOrderStatus();
        //判断是否是待支付状态
        if (serverOrder.getOrderStatus() != OrderStatus.PENDINGPAY) {
            throw new RuntimeException("订单状态异常");
        }

        String localIp = HttpUtils.getLocalIp(request);

        String qrcodeUrl = wechatPayApi.createWechatOrder(serverOrder.getOrderNo(), localIp, serverOrder.getOrderAmount());


        if( qrcodeUrl ==null || "".equals(qrcodeUrl))
            return;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); //设置字符集编码类型
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = multiFormatWriter.encode(qrcodeUrl, BarcodeFormat.QR_CODE, 300, 300,hints);
            BufferedImage image = toBufferedImage(bitMatrix);
            //输出二维码图片流
            try {
//                response.setHeader();
                response.setContentType("image/png");
                response.setHeader("Pragma","no-cache");
                response.setHeader("Cache-Control","no-cache");
                ImageIO.write(image, "png", response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (WriterException e1) {
            e1.printStackTrace();
        }
    }

    private static BufferedImage toBufferedImage(BitMatrix bitMatrix){
        BufferedImage image = null;
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        image = new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) == true ?
                        Color.BLACK.getRGB():Color.WHITE.getRGB());
            }
        }

        return image;
    }


}
