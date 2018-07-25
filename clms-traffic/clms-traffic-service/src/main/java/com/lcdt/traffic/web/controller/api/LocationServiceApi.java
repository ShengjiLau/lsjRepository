package com.lcdt.traffic.web.controller.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.config.BaiduYyConfig;
import com.lcdt.traffic.dao.WaybillMapper;
import com.lcdt.traffic.model.Waybill;
import com.lcdt.traffic.service.LocationService;
import com.lcdt.traffic.service.OwnDriverService;
import com.lcdt.traffic.util.BalanceCheckBo;
import com.lcdt.traffic.util.GprsLocationBo;
import com.lcdt.traffic.web.dto.PageBaseDto;
import com.lcdt.userinfo.model.Driver;
import com.lcdt.userinfo.model.LocationCallbackModel;
import com.lcdt.userinfo.service.DriverService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.tl.commons.util.DateUtility;

import java.nio.charset.Charset;
import java.util.*;

/**
 * @AUTHOR liuh
 * @DATE 2017-12-27
 */

@Api(description = "定位服务api")
@RestController
@RequestMapping("/location")
public class LocationServiceApi {

    Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    @Reference
    public DriverService driverService;

    @Autowired
    private OwnDriverService ownDriverService;

    @Autowired
    private BalanceCheckBo balanceCheckBo;

    @Autowired
    private BaiduYyConfig baiduYyConfig;

    @Autowired
    private WaybillMapper waybillMapper;

    @Autowired
    private LocationService locationService;

    @ApiOperation(value = "定位激活回调地址", notes = "用来接收基站定位第三方发送的回调信息（无任何权限控制）")
    @GetMapping("/callback")

    public JSONObject callbackUrl(@ModelAttribute LocationCallbackModel locationCallbackModel) {
        logger.debug("locationCallbackModel:" + locationCallbackModel.toString());
        String phone = locationCallbackModel.getMobileno();
        Driver driver = new Driver();
        driver.setDriverPhone(locationCallbackModel.getMobileno());
        driver.setGpsStatus(new Short("2"));
        //开启线程执行定位状态同步，将定位状态改为已激活
        Runnable runnable = new Runnable() {
            public Driver dr;

            public Runnable setDr(Driver dr) {
                this.dr = dr;
                return this;
            }

            public void run() {
                System.out.println("----" + dr.getDriverPhone());
                driverService.modGpsStatus(dr);
            }
        }.setDr(driver);
        Thread t = new Thread(runnable);
        t.start();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("message", "请求成功");
        return jsonObject;
    }

    @ApiOperation(value = "开通定位功能", notes = "发送激活短信到用手手机，成功后将数据库状态改为1等待激活")
    @GetMapping("/open")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('lbs_location')")
    public JSONObject getGpstStatus(String mobile) {
        logger.debug("driverPhone:" + mobile);
        Driver driver = new Driver();
        driver.setDriverPhone(mobile);
        JSONObject jsonObject = new JSONObject();
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        /*if(!balanceCheckBo.check(companyId)){
            jsonObject.put("code", -1);
            jsonObject.put("message", "余额不足！请充值！");
            return jsonObject;
        }*/
        try {
            //开通授权
            JSONObject openInfo = GprsLocationBo.getInstance().authOpen(mobile);
            if (openInfo.getIntValue("resid") == 0) {   //开通成功后变更数据库状态为1等待激活
                driver.setGpsStatus(new Short("1"));
                int row = driverService.modGpsStatus(driver);
                jsonObject.put("code", 0);
                jsonObject.put("message", "开通成功，请回复短信小写的y");
            } else if (openInfo.getIntValue("resid") == 1) { //如果改手机已经激活过，则变更数据库状态为已激活
                driver.setGpsStatus(new Short("2"));
                int row = driverService.modGpsStatus(driver);
                jsonObject.put("code", 0);
                jsonObject.put("message", "手机号已经激活");
            } else {
                jsonObject.put("code", -1);
                jsonObject.put("message", "开通失败，请联系客服");
            }
        } catch (Exception e) {
            jsonObject.put("code", -1);
            jsonObject.put("message", "操作失败");
        }
        return jsonObject;
    }

    @ApiOperation(value = "查询定位状态是否激活", notes = "点击等待激活按钮 （会优先查询接口，然后同步更新本地数据库）")
    @GetMapping("/locationstatus")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('lbs_location')")
    public JSONObject queryStatus(String mobile) {
        logger.debug("mobile:" + mobile);
        JSONObject jsonObject = new JSONObject();
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        /*if(!balanceCheckBo.check(companyId)){
            jsonObject.put("code", -1);
            jsonObject.put("message", "余额不足！请充值！");
            return jsonObject;
        }*/
        JSONObject result = GprsLocationBo.getInstance().authStatus(mobile);
        int resid = result.getIntValue("resid");
        if (resid == 1) {   //白名单且已开通定位，更新本地数据库定位状态为2
            Driver driver = new Driver();
            driver.setDriverPhone(mobile);
            driver.setGpsStatus(new Short("2"));
            driverService.modGpsStatus(driver);
            jsonObject.put("code", resid);
            jsonObject.put("message", "定位已激活");
        } else if (resid == 0) {    //未激活
            jsonObject.put("code", resid);
            jsonObject.put("message", "司机" + mobile + "没有回复短信激活，再次发送开通短信？");
        } else if (resid == -1) {    //手机号不存在
            jsonObject.put("code", resid);
            jsonObject.put("message", "查找不到该手机");
        } else {      //resid = -99  黑名单用户
            jsonObject.put("code", resid);
            jsonObject.put("message", "黑名单用户");
        }
        return jsonObject;
    }

    @ApiOperation(value = "定位激活开通", notes = "会调用接口发送定位激活授权短信")
    @GetMapping("/authstatus")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('lbs_location')")
    public JSONObject authOpen(String mobile) {
        logger.debug("mobile:" + mobile);
        JSONObject jsonObject = new JSONObject();
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        /*if(!balanceCheckBo.check(companyId)){
            jsonObject.put("code", -1);
            jsonObject.put("message", "余额不足！请充值！");
            return jsonObject;
        }*/
        Driver driver = new Driver();
        driver.setDriverPhone(mobile);
        JSONObject result = GprsLocationBo.getInstance().authOpen(mobile);
        int resid = result.getIntValue("resid");
        if (resid == 0) {   //白名单开通成功，请通知用户回复短信小写的y
            driver.setGpsStatus(new Short("1"));
            driverService.modGpsStatus(driver);
            jsonObject.put("code", 0);
            jsonObject.put("resid", resid);
            jsonObject.put("message", "开通成功，请回复短信小写的y");
        } else if (resid == 1) {
            driver.setGpsStatus(new Short("2"));
            driverService.modGpsStatus(driver);
            jsonObject.put("code", 0);
            jsonObject.put("resid", resid);
            jsonObject.put("message", "定位已激活");
        } else if (resid == -3) {
            jsonObject.put("code", 0);
            jsonObject.put("resid", resid);
            jsonObject.put("message", "一个手机号一天最多能下发白名单3次，请明天再试");
        } else {
            /**
             *  -2	一个手机号一天最多能下发白名单3次，请明天再试
             *  -3	手机号长度必须为11位
             *  -30	手机不合法，不属于有效的手机号
             *  -10	黑名单用户,禁止定位
             *  -11	key不存在或secret错
             *  -20	您的IP地址不在白名单中,请联系客服
             *  -40	您没有购买当前产品
             *  -80	余额不足,请充值:请联系客服
             */
            jsonObject.put("code", -1);
            jsonObject.put("message", "系统错误,请联系客服");  //
        }
        return jsonObject;
    }

    @ApiOperation(value = "基站定位", notes = "通过接口查询定位信息，并同步到本地数据库")
    @GetMapping("/querylocation")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('lbs_location')")
    public JSONObject queryLocation(String mobile,String driverName) {
        logger.debug("mobile:" + mobile);
        //  获取companyId
        Long companyId = SecurityInfoGetter.getCompanyId();
        return locationService.queryLocation(companyId,mobile,driverName,SecurityInfoGetter.getUser().getRealName(),null);
    }

    @ApiOperation(value = "列表获取位置信息", notes = "根据手机号获取位置(基站定位),多个手机号需要用逗号分隔")
    @GetMapping("/current_location")
    public PageBaseDto<List<Driver>> getGpstStatus(@RequestParam String[] driverPhoneArr) {
//        String driverPhones = StringUtils.join(driverPhoneArr,",");
        List<String> driverPhoneList = Arrays.asList(driverPhoneArr);
        logger.debug("driverPhones:" + driverPhoneList.size());
        List<Driver> driverList = ownDriverService.getGpsInfo(driverPhoneList);
        PageBaseDto pageBaseDto = new PageBaseDto(driverList, driverList.size());
        return pageBaseDto;
    }

    @ApiOperation(value = "基站定位剩余条数", notes = "获取基站定位剩余条数")
    @GetMapping("/querycount")
    public JSONObject queryLocation() {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        JSONObject jsonObject = new JSONObject();
        try {
            int count = balanceCheckBo.getGmsCount(companyId);
            jsonObject.put("code", 0);
            jsonObject.put("message", "请求成功");
            JSONObject gmsCount = new JSONObject();
            gmsCount.put("gmsCount", count);
            jsonObject.put("data", gmsCount);
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("code", -1);
            jsonObject.put("message", "请求失败");
        }
        return jsonObject;
    }


    public static RestTemplate getInstanceByCharset(String charset) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName(charset)));
        return restTemplate;
    }


    @ApiOperation(value = "获取轨迹", notes = "根据运单id获取轨迹")
    @GetMapping("/getTrack")
    public String getTrack(@RequestParam Long waybillId) {
        Waybill waybill=null;
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        //如果运单已完成或已卸货
        if(waybillId!=null&&waybillMapper.selectByPrimaryKey(waybillId)!=null)
        {
            waybill =  waybillMapper.selectByPrimaryKey(waybillId);
            Long sTime = waybill.getCreateDate().getTime()/1000;
            Long eTime = waybill.getUnloadTime()==null?new Date().getTime()/1000:waybill.getUnloadTime().getTime()/1000;
            long dayTime= 86400;
            long dif_time = eTime-sTime;
            int size = 0;
            if(dif_time%dayTime==0){
                size = (int) (dif_time/dayTime);
            }else{
                size = (int) (dif_time/dayTime)+1;
            }
            for (int i = 0; i < size; i++) {
                Long  startTime = eTime-(dayTime*(i+1));
                if(i==size-1)
                {
                    startTime = sTime;
                }
                Long  endTime = eTime-(dayTime*i);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                Map map = new HashMap();
                map.put("service_id", baiduYyConfig.getService_id());
                map.put("entity_name", waybill.getDriverPhone());
                map.put("start_time", startTime);
                map.put("end_time", endTime);
                map.put("ak", baiduYyConfig.getAk());
                logger.info("请求轨迹接口 参数：{}",map.toString());
                HttpEntity entity = new HttpEntity(map, headers);
                RestTemplate restTemplate = getInstanceByCharset("UTF-8");
                ResponseEntity<String> exchange =
                        restTemplate.getForEntity("http://yingyan.baidu.com/api/v3/track/gettrack?ak={ak}&service_id={service_id}&entity_name={entity_name}&start_time={start_time}&end_time={end_time}", String.class,map);
                JSONObject result  = JSON.parseObject(exchange.getBody().toString());
                if(result.containsKey("status")&&result.getInteger("status")==0)
                {
                    JSONArray jsonArrayPo = JSON.parseArray(result.getString("points"));
                    jsonArray.addAll(jsonArrayPo);
                }
            }
            jsonObject.put("startTime",sTime);
            jsonObject.put("endTime",eTime);
            jsonObject.put("code",0);
            jsonObject.put("points",jsonArray);
            return jsonObject.toJSONString();
        }
        jsonObject.put("code",2);
        jsonObject.put("message","运单信息不存在!");
        return jsonObject.toJSONString();
    }

}
