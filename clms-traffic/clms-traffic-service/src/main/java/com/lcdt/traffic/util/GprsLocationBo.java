package com.lcdt.traffic.util;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.userinfo.model.LocationCallbackModel;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据都基站定位服务
 * @AUTHOR liuh
 * @DATE 2017-12-26
 */
public class GprsLocationBo {

    /**开通授权定位API接口*/
    private static String OPEN_URL = "http://pin.shujudu.cn/api/pin/authlbsopen/";

    /**状态查询接口*/
    private static String STATUS_URL = "http://pin.shujudu.cn/api/pin/authlbsstatus/";

    /**实时定位接口*/
    private static String QUERY_URL = "http://pin.shujudu.cn/api/pin/authlbsquery/";

    /**定位关闭接口*/
    private static String CLOSE_URL = "http://pin.shujudu.cn/api/pin/authlbsclose/";

    /**账户余额查询*/
    private static  String ACCOUNTBALANCE_URL = "http://pin.shujudu.cn/api/pin/accountbalance/";

    private static final String KEY = "5bf7007968";

    private static final String SECRET = "21693d8aac25f92ad5e90f328476dbb2";


    private static GprsLocationBo gprsLocationBo = null;
    public static GprsLocationBo getInstance() {

        if (gprsLocationBo == null) {
            gprsLocationBo = new GprsLocationBo();
        }
        return gprsLocationBo;
    }

    /**
     * 请求开通定位服务 (返回值说明：http://doc.oschina.net/shujudu?t=197482)
     * @param moblile
     * @return
     */
    public JSONObject authOpen(String moblile){
        Map map = new HashMap<>();
        map.put("mobile",moblile);
        map.put("key", GprsLocationBo.KEY);
        map.put("secret",GprsLocationBo.SECRET);
        return HttpRequestUtil.httpGet(GprsLocationBo.OPEN_URL,map,60000);
    }

    /**
     * 手机定位状态查询 (返回值说明：http://doc.oschina.net/shujudu?t=197485)
     * @param moblile
     * @return
     */
    public JSONObject authStatus(String moblile){
        Map map = new HashMap<>();
        map.put("mobile",moblile);
        map.put("key", GprsLocationBo.KEY);
        map.put("secret",GprsLocationBo.SECRET);
        return HttpRequestUtil.httpGet(GprsLocationBo.STATUS_URL,map,5000);
    }

    /**
     * 实时定位查询 (返回值说明：http://doc.oschina.net/shujudu?t=197483)
     * @param moblile
     * @return
     */
    public JSONObject queryLocation(String moblile){
        Map map = new HashMap<>();
        map.put("mobile",moblile);
        map.put("key", GprsLocationBo.KEY);
        map.put("secret",GprsLocationBo.SECRET);
        return HttpRequestUtil.httpGet(GprsLocationBo.QUERY_URL,map,60000);
    }


}
