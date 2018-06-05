package com.lcdt.driver.wechat.api;

import com.alibaba.fastjson.JSON;
import com.lcdt.driver.config.BDYYConstants;
import com.lcdt.driver.dto.UploadPointDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bdyy")
public class BdyyApi {

    private Logger logger = LoggerFactory.getLogger(BdyyApi.class);

    @Autowired
    private BDYYConstants constants;

    @RequestMapping("/uploadpoint")
    public String uploadPoint(UploadPointDto dto) {
        logger.info("鹰眼上传 dto {}", dto.toString());
        RestTemplate restTemplate = getInstanceByCharset("UTF-8");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("AK", constants.getAk());
        map.add("service_id", constants.getService_id());
        map.add("coord_type_input", dto.getCoord_type_input());
        map.add("entity_name", dto.getEntity_name());
        map.add("latitude", dto.getLatitude());
        map.add("longitude", dto.getLongitude());
        map.add("loc_time", dto.getLoc_time());
        map.add("ak", constants.getAk());
        HttpEntity entity = new HttpEntity(map, headers);
        ResponseEntity<String> response = restTemplate.exchange("http://yingyan.baidu.com/api/v3/track/addpoint", HttpMethod.POST, entity, String.class);
        String body = response.getBody();
        logger.info("鹰眼上传结果 {}", body);
        return body;
    }

    @RequestMapping("/gettrack")
    public String gettrack(String startTime,String endTime,String username) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        Map map = new HashMap();
        map.put("service_id", constants.getService_id());
        map.put("entity_name", username);
        map.put("start_time", startTime);
        map.put("end_time", endTime);
        map.put("ak", constants.getAk());
        logger.info("请求轨迹接口 参数：{}",map.toString());
        HttpEntity entity = new HttpEntity(map, headers);
        RestTemplate restTemplate = getInstanceByCharset("UTF-8");
        ResponseEntity<String> exchange =
                restTemplate.getForEntity("http://yingyan.baidu.com/api/v3/track/gettrack?ak={ak}&service_id={service_id}&entity_name={entity_name}&start_time={start_time}&end_time={end_time}", String.class,map);
        return exchange.getBody();
    }


    public static RestTemplate getInstanceByCharset(String charset) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName(charset)));
        return restTemplate;
    }
}
