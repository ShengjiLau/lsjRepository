package lcdt.wms.notify.sms.impl;

import com.lcdt.util.MD5;
import lcdt.wms.notify.dto.SmsDto;
import lcdt.wms.notify.sms.SmsService;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tl.commons.util.DateUtility;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ybq on 2017/8/9.
 */
@Service
public class SmsServiceImpl implements SmsService {

    private Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

    @Override
    public boolean sendSms(SmsDto smsDto, String[] phones, String signature, String message) {

        if(phones==null) return false;

        HttpPost httpPost = new HttpPost(smsDto.getUrl());
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("name", smsDto.getName()));
        String seed = DateUtility.date2String(new Date(),"yyyyMMddHHmmss");
        nameValuePairs.add(new BasicNameValuePair("seed", seed));
        nameValuePairs.add(new BasicNameValuePair("key", encodeKey(smsDto, seed)));
        nameValuePairs.add(new BasicNameValuePair("dest", phoneNumsValue(phones)));
        nameValuePairs.add(new BasicNameValuePair("content", signature + message));
        UrlEncodedFormEntity uefEntity;
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            uefEntity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
            httpPost.setEntity(uefEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    //判断是否发送成功
                    String responseStr = EntityUtils.toString(entity);
                    boolean isSendSuccess = responseStr.contains("success");
                    if (isSendSuccess) {
                        logger.info("短信发送成功：内容{}，手机号：{}", message, phones);
                        return true;
                    } else {
                        logger.error("短信发送失败，错误代码：{},手机号：{}", responseStr, phones);
                        return false;
                    }
                }
            } finally {
                response.close();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("短信发送失败,错误信息{}", e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("短信发送失败，错误代码：{},手机号：{}", phones);
        }
        return false;
    }


    @Override
    public String findSmsBalance(SmsDto smsDto) {
        return null;
    }


    private static String encodeKey(SmsDto smsDto, String seed) {
        MD5 md5 = new MD5();
        String p1 = md5.getMD5ofStr(smsDto.getPwd());
        String key = md5.getMD5ofStr(p1.toLowerCase() + seed).toLowerCase();
        return key;
    }



    private static String phoneNumsValue(String[] phoneNums) {
        StringBuffer stringBuilder = new StringBuffer();
        for (String phone : phoneNums) {
            if (phone != null && !phone.equals("")) {
                stringBuilder.append(phone);
                stringBuilder.append(",");
            }
        }
        return stringBuilder.toString();
    }
}
