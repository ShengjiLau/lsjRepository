package com.lcdt.driver.wechat.api;

import com.lcdt.driver.dto.UploadPointDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/bdyy")
public class BdyyApi {

    private Logger logger = LoggerFactory.getLogger(BdyyApi.class);

    @RequestMapping("/uploadpoint")
    public String uploadPoint(UploadPointDto dto) {
        logger.info("鹰眼上传 dto {}",dto.toString());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://yingyan.baidu.com/api/v3/track/addpoint", dto, String.class);
        String body = stringResponseEntity.getBody();
        logger.info("鹰眼上传结果 {}",body);
        return body;
    }


}
