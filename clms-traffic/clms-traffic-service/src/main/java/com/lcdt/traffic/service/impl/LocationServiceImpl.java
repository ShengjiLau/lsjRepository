package com.lcdt.traffic.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.config.BaiduYyConfig;
import com.lcdt.traffic.service.LocationService;
import com.lcdt.traffic.util.BalanceCheckBo;
import com.lcdt.traffic.util.GprsLocationBo;
import com.lcdt.userinfo.model.Driver;
import com.lcdt.userinfo.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.tl.commons.util.DateUtility;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static com.lcdt.traffic.web.controller.api.LocationServiceApi.getInstanceByCharset;

/**
 * @AUTHOR liuh
 * @DATE 2018-06-27
 */
@Service
public class LocationServiceImpl implements LocationService {

    @Reference
    public DriverService driverService;

    @Autowired
    private BaiduYyConfig baiduYyConfig;

    @Autowired
    private BalanceCheckBo balanceCheckBo;

    @Override
    public JSONObject queryLocation(Long companyId, String mobile, String driverName){
        return  this.queryLocation(companyId,mobile,driverName,null,null);
    }

    @Override
    public JSONObject queryLocation(Long companyId, String mobile,String driverName,String userName, String serialNo) {

        JSONObject jsonObject = new JSONObject();

        if (!balanceCheckBo.check(companyId)) {
            jsonObject.put("code", -1);
            jsonObject.put("message", "余额不足！请充值！");
            return jsonObject;
        }
        /**
         * 先通过定位状态查询改手机号是否已激活，未激活提示需要开通才能定位，数据库状态变成0未开通
         * 如果用户已激活则开始定位并把定位信息同步到数据库
         * */
        JSONObject openStatus = GprsLocationBo.getInstance().authStatus(mobile);
        int resid = openStatus.getIntValue("resid");
        //白名单且已开通定位，更新本地数据库定位状态为2
        if (resid == 1) {
            JSONObject result = GprsLocationBo.getInstance().queryLocation(mobile);
            int resid1 = result.getIntValue("resid");
            //已激活
            if (resid1 == 0) {
                //查询正常扣费
                balanceCheckBo.deductionGms(companyId,mobile,driverName,userName,serialNo);
                Driver driver = new Driver();
                driver.setDriverPhone(mobile);
                driver.setCurrentLocation(result.getString("location"));
                driver.setShortCurrentLocation(result.getString("street"));
                //增加经纬度内容
                driver.setLongitude(result.getString("lng"));
                driver.setLatitude(result.getString("lat"));
                driverService.updateLocation(driver);
                //司机信息更新后上传鹰眼信息
                MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
                map.add("entity_name", mobile);
                map.add("latitude", driver.getLatitude());
                map.add("longitude", driver.getLongitude());
                //上传鹰眼信息
                new Thread(() -> upPoint(map)).start();

//                upPoint(map);

                jsonObject.put("code", resid1);
                jsonObject.put("location", result.getString("location"));
                jsonObject.put("locationTime", DateUtility.getCurrDatetime());
                jsonObject.put("message", "查询成功");
                //	余额不足,请充值:请联系客服
            } else if (resid1 == -80) {
                jsonObject.put("code", resid1);
                jsonObject.put("message", "余额不足,请充值:请联系客服");
                //用户可能关机
            } else if (resid1 == -130) {
                jsonObject.put("code", resid1);
                jsonObject.put("message", "用户可能关机");
                //对于移动手机，定位失败时运营商返回的结果
            } else {
                jsonObject.put("code", resid1);
                jsonObject.put("message", "定位失败，请联系客服！");
            }
            //未激活
        } else if (resid == 0) {
            Driver driver = new Driver();
            driver.setDriverPhone(mobile);
            driver.setGpsStatus(new Short("1"));
            driverService.modGpsStatus(driver);
            jsonObject.put("code", -1);
            jsonObject.put("message", "需要先开通定位服务才能定位");
            //手机号不存在
        } else if (resid == -1) {
            jsonObject.put("code", resid);
            jsonObject.put("message", "查找不到该手机");
        } else {      //resid = -99  黑名单用户
            jsonObject.put("code", resid);
            jsonObject.put("message", "黑名单用户");
        }

        return jsonObject;
    }


    public String upPoint(MultiValueMap map) {
        /**
         * 由于坐标是非百度坐标，所以需要对坐标进行转换后再上传
         *http://lbsyun.baidu.com/index.php?title=webapi/guide/changeposition 转换api文档
         * from 3 to 5
         * */
        RestTemplate restTemplate = getInstanceByCharset("utf-8");

        String coords = map.getFirst("longitude") + ","+ map.getFirst("latitude");
        String url = "http://api.map.baidu.com/geoconv/v1/?"+ "coords="+coords+"&ak="+baiduYyConfig.getAk();
        ResponseEntity<String> result = restTemplate.getForEntity(url,String.class);
        String bodyString = result.getBody();
//        System.out.println("bodyString==" + bodyString);
        JSONObject jsonObject = (JSONObject) JSON.parseObject(bodyString).getJSONArray("result").get(0);
        System.out.println("x="+jsonObject.getString("x"));
        System.out.println("x="+jsonObject.getString("y"));
        map.set("longitude",jsonObject.getString("x"));
        map.set("latitude",jsonObject.getString("y"));
        map.add("ak", baiduYyConfig.getAk());
        map.add("service_id", baiduYyConfig.getService_id());
        map.add("coord_type_input", "bd09ll");
        map.add("loc_time", (System.currentTimeMillis() / 1000) + "");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity entity = new HttpEntity(map, headers);
        ResponseEntity<String> response = restTemplate.exchange("http://yingyan.baidu.com/api/v3/track/addpoint", HttpMethod.POST, entity, String.class);
        String body = response.getBody();
        System.out.println("body==" + body);
        return body;

    }
}
