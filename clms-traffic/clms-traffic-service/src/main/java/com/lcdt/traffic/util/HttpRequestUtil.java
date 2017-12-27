package com.lcdt.traffic.util;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.util.HttpUtils;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @AUTHOR liuh
 * @DATE 2017-12-26
 */
public class HttpRequestUtil {

    @Autowired
    private RestOperations restOperations;

    public static JSONObject httpGet(String url, Map<String, String> params, int timeOut) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(timeOut);   //读取超时
        requestFactory.setConnectTimeout(5000); //连接超时

        StringHttpMessageConverter m = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        RestTemplate restTemplate = new RestTemplateBuilder().additionalMessageConverters(m).build();
        restTemplate.setRequestFactory(requestFactory);     //set超时属性内容
        String pm = "";
        for (String key : params.keySet()) {
            pm += "&" + key + "=" + params.get(key);
        }
        url = url + "?" + pm.substring(1);
//        System.out.println("===> url" + url);
        String response = restTemplate.getForObject(url, String.class);
        String jsonString = null;
        try {
            jsonString = new String(response.getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        return jsonObject;
    }

    public static JSONObject httpPost(String url, Map<String, String> params, int timeOut) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(timeOut);   //读取超时
        requestFactory.setConnectTimeout(5000); //连接超时

        StringHttpMessageConverter m = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        RestTemplate restTemplate = new RestTemplateBuilder().additionalMessageConverters(m).build();
        restTemplate.setRequestFactory(requestFactory);     //set超时属性内容

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        String jsonStr = JSONObject.toJSONString(params);
        HttpEntity<String> formEntity = new HttpEntity<String>(jsonStr, headers);

        String result = restTemplate.postForObject(url, formEntity, String.class);
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject;
    }

    public static void main(String[] args) {
//        String url = "http://pin.shujudu.cn/api/pin/authlbsopen/?key=5bf7007968&secret=21693d8aac25f92ad5e90f328476dbb2&mobile=13589698353";
        Map map = new HashMap();
        map.put("key", "5bf7007968");
        map.put("secret", "21693d8aac25f92ad5e90f328476dbb2");
        map.put("mobile", "13589698353");
        String url = "http://pin.shujudu.cn/api/pin/authlbsopen/";
        JSONObject jsonObject = HttpRequestUtil.httpGet(url, map,1000);
        System.out.println("==> resutl:" + jsonObject.toJSONString());
    }
}
