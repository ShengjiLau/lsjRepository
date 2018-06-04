package com.lcdt.driver.wechat.api;

import com.alibaba.fastjson.JSON;
import com.lcdt.driver.dto.UploadPointDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/bdyy")
public class BdyyApi {

    private Logger logger = LoggerFactory.getLogger(BdyyApi.class);

    @RequestMapping("/uploadpoint")
    public String uploadPoint(UploadPointDto dto) {
        logger.info("鹰眼上传 dto {}",dto.toString());


        StringHttpMessageConverter m = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        RestTemplate restTemplate = new RestTemplateBuilder().additionalMessageConverters(m).build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("AK", dto.getAk());
        map.add("service_id", dto.getService_id());
        map.add("coord_type_input", dto.getCoord_type_input());
        map.add("entity_name", dto.getEntity_name());
        map.add("latitude", dto.getLatitude());
        map.add("longitude", dto.getLongitude());
        map.add("loc_time", dto.getLoc_time());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity( "http://yingyan.baidu.com/api/v3/track/addpoint", request , String.class );


//        RestTemplate restTemplate = new RestTemplate();

//        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://yingyan.baidu.com/api/v3/track/addpoint",JSON.toJSONString(dto), String.class);
        String body = response.getBody();
        logger.info("鹰眼上传结果 {}",body);
        return body;
    }


}
