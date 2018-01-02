package com.lcdt.traffic.web.controller.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.traffic.util.GprsLocationBo;
import com.lcdt.userinfo.model.Driver;
import com.lcdt.userinfo.model.LocationCallbackModel;
import com.lcdt.userinfo.service.DriverService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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
    private DriverService driverService;

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
        jsonObject.put("msg", "请求成功");
        return jsonObject;
    }

    @ApiOperation(value = "开通定位功能", notes = "此操作会更新本地数据库为1，待激活的状态")
    @GetMapping("/open")
    public JSONObject getGpstStatus(String mobile) {
        logger.debug("driverPhone:" + mobile);
        Driver driver = new Driver();
        driver.setGpsStatus(new Short("1"));
        JSONObject jsonObject = new JSONObject();
        try {
            driverService.modGpsStatus(driver);
            jsonObject.put("code", 0);
            jsonObject.put("message", "操作成功");
        } catch (Exception e) {
            jsonObject.put("code", -1);
            jsonObject.put("message", "定位状态异常");
        }
        return jsonObject;
    }

    @ApiOperation(value = "查询定位状态是否激活", notes = "点击等待激活按钮 （会优先查询接口，然后同步更新本地数据库）")
    @GetMapping("/locationstatus")
    public JSONObject queryStatus(String moblie) {
        logger.debug("moblie:" + moblie);
        JSONObject jsonObject = new JSONObject();
        JSONObject result = GprsLocationBo.getInstance().authStatus(moblie);
        int resid = result.getInteger("resid");
        if (resid == 1) {   //白名单且已开通定位，更新本地数据库定位状态为2
            Driver driver = new Driver();
            driver.setDriverPhone(moblie);
            driver.setGpsStatus(new Short("2"));
            driverService.modGpsStatus(driver);
            jsonObject.put("code", resid);
            jsonObject.put("msg", "定位已激活");
        } else if (resid == 0) {    //未激活
            jsonObject.put("code", resid);
            jsonObject.put("msg", "司机" + moblie + "没有回复短信激活，再次发送开通短信？");
        } else if (resid == -1) {    //手机号不存在
            jsonObject.put("code", resid);
            jsonObject.put("msg", "查找不到该手机");
        } else {      //resid = -99  黑名单用户
            jsonObject.put("code", resid);
            jsonObject.put("msg", "黑名单用户");
        }
        return jsonObject;
    }

    @ApiOperation(value = "定位激活开通", notes = "会调用接口发送定位激活授权短信")
    @GetMapping("/authstatus")
    public JSONObject authOpen(String moblie) {
        logger.debug("moblie:" + moblie);
        JSONObject jsonObject = new JSONObject();
        JSONObject result = GprsLocationBo.getInstance().authOpen(moblie);
        int resid = result.getInteger("resid");
        if (resid == 0) {   //白名单开通成功，请通知用户回复短信小写的y
            Driver driver = new Driver();
            driver.setDriverPhone(moblie);
            driver.setGpsStatus(new Short("2"));
            driverService.modGpsStatus(driver);
            jsonObject.put("code", 0);
            jsonObject.put("resid", resid);
            jsonObject.put("msg", "白名单开通成功，请通知用户回复短信小写的y");
        } else if (resid == 1) {
            jsonObject.put("code", 0);
            jsonObject.put("resid", resid);
            jsonObject.put("msg", "定位已激活");
        } else if (resid == -3) {
            jsonObject.put("code", 0);
            jsonObject.put("resid", resid);
            jsonObject.put("msg", "一个手机号一天最多能下发白名单3次，请明天再试");
        }else{
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
            jsonObject.put("msg", "系统错误");  //
        }
        return jsonObject;
    }

    @ApiOperation(value = "基站定位", notes = "通过接口查询定位信息，并同步到本地数据库")
    @GetMapping("/querylocation")
    public JSONObject queryLocation(String moblie) {
        logger.debug("moblie:" + moblie);
        JSONObject jsonObject = new JSONObject();
        JSONObject result = GprsLocationBo.getInstance().queryLocation(moblie);
        int resid = result.getInteger("resid");
        if (resid == 0) {   //正确返回
            Driver driver = new Driver();
            driver.setDriverPhone(moblie);
            driver.setCurrentLocation(result.getString("location"));
            driver.setShortCurrentLocation(result.getString("street"));
            driverService.updateLocation(driver);
            jsonObject.put("code", resid);
            jsonObject.put("location",result.getString("location"));
            jsonObject.put("msg", "查询成功");
        } else if (resid == -80) {    //	余额不足,请充值:请联系客服
            jsonObject.put("code", resid);
            jsonObject.put("msg", "余额不足,请充值:请联系客服");
        } else if (resid == -130) {    //用户可能关机
            jsonObject.put("code", resid);
            jsonObject.put("msg", "用户可能关机");
        } else {      //对于移动手机，定位失败时运营商返回的结果
            jsonObject.put("code", resid);
            jsonObject.put("msg", "接口返回错误");
        }
        return jsonObject;
    }

}
