package com.lcdt.pay.utils;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.pay.wechatpay.Configure;
import com.lcdt.pay.wechatpay.HttpsRequest;
import com.lcdt.pay.wechatpay.UnifiedorderBean;
import com.lcdt.pay.wechatpay.XMLParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Map;

@Service
public class WechatPayApi {

    @Autowired
    HttpsRequest request;

    /**
     * 返回微信支付的交易二维码
     * @return
     */
    public String createWechatOrder(String orderID,String ip,Integer totalfee){
        UnifiedorderBean unifiedorderBean = UnifiedorderBean.defaultOrderBean(orderID, totalfee, ip);
        try {
            String response = request.sendPost(Configure.UNIFIEDORDER_API, unifiedorderBean);
            Map<String, Object> responseMap = XMLParser.getMapFromXML(response);

            String return_code = responseMap.get("return_code").toString();
            if(return_code.equalsIgnoreCase("SUCCESS")){
                String result_code = responseMap.get("result_code").toString();
                if(result_code.equalsIgnoreCase("SUCCESS")){
                    if(savePaymentLog(unifiedorderBean)){
                        return (String) responseMap.get("code_url");
                    }
                }
            }else{
                //log.error(JSONObject.toJSONString(responseMap));
                return null;
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return "";
    }

    private boolean savePaymentLog(UnifiedorderBean bean){
        return true;
    }

}
