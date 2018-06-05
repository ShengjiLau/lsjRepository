import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class TestBdyyTrack {

    @Test
    public void testTrack(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        Map map = new HashMap();
        map.put("service_id", "200868");
        map.put("entity_name", "15666836323");
        map.put("start_time", "1528160423");
        map.put("end_time", "1528170423");
        map.put("ak", "nDXccuEHSqNXVkHhyHT0UCyUOpUTnz0o");
        HttpEntity entity = new HttpEntity(map, headers);
        RestTemplate restTemplate = getInstanceByCharset("UTF-8");
        ResponseEntity<String> exchange =
                restTemplate.getForEntity("http://yingyan.baidu.com/api/v3/track/gettrack?ak={ak}&service_id={service_id}&entity_name={entity_name}&start_time={start_time}&end_time={end_time}", String.class,map);
//        logger.info("请求头：{}",exchange.getHeaders().);
        System.out.println(exchange.getBody());
    }

    public static RestTemplate getInstanceByCharset(String charset) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName(charset)));
        return restTemplate;
    }
}
