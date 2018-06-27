package com.lcdt.traffic.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @AUTHOR liuh
 * @DATE 2018-06-27
 */
public interface LocationService {

    /**
     * 基站定位信息查询，并返回相关信息
     *
     * @param mobile
     * @return
     */
    JSONObject queryLocation(Long companyId, String mobile);
}
