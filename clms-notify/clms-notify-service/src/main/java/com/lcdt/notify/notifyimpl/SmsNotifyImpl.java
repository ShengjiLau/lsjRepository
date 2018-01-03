package com.lcdt.notify.notifyimpl;

import com.lcdt.notify.model.NotifyReceiver;
import com.lcdt.util.MD5;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
public class SmsNotifyImpl  {

    private Logger logger = LoggerFactory.getLogger(SmsNotifyImpl.class);

    private static String username = "dtdtz";
    private static String pwd = "n8ie41u7";

    public static final String SMS_API = "http://160.19.212.218:8080/eums/utf8/send_strong.do";
    CloseableHttpClient httpClient = HttpClients.createDefault();

    public boolean sendSmsNotify(String content, String phoneNum) {
        logger.info("发送短信通知 >>> {} >>> {}",content,phoneNum);
        
        return true;
    }



    public boolean sendSms(String[] phonsNums, String message) {

        if (phonsNums == null) {
            return false;
        }

        String seed = simpleDate();

        HttpPost httpPost = new HttpPost(SMS_API);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("name", username));
        nameValuePairs.add(new BasicNameValuePair("seed", seed));
        nameValuePairs.add(new BasicNameValuePair("key", encodeKey(seed)));
        nameValuePairs.add(new BasicNameValuePair("dest", phoneNumsValue(phonsNums)));
        nameValuePairs.add(new BasicNameValuePair("content", "【大驼队】" + message));
//		nameValuePairs.add(new BasicNameValuePair("reference", String.valueOf(recordId)));

        UrlEncodedFormEntity uefEntity;
        try {
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
                        logger.info("短信发送成功：内容{}，手机号：{}", message, phonsNums);
                        return true;
                    } else {
                        logger.error("短信发送失败，错误代码：{},手机号：{}", responseStr, phonsNums);
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
            logger.error("短信发送失败，错误代码：{},手机号：{}", phonsNums);
        }
        return false;
    }

    private static String simpleDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = simpleDateFormat.format(new Date());
        return format;
    }
    public static String encodeKey(String seed) {
        MD5 md5 = new MD5();
        String p1 = md5.getMD5ofStr(pwd);
        String key = md5.getMD5ofStr(p1.toLowerCase() + seed).toLowerCase();
        return key;
    }

    public static String phoneNumsValue(String[] phoneNums) {
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
