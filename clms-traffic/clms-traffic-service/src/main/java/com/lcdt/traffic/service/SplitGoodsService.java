package com.lcdt.traffic.service;

import com.lcdt.traffic.web.dto.SplitGoodsParamsDto;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;

import java.util.List;

/**
 * Created by yangbinq on 2017/12/21.
 */
public interface SplitGoodsService {


    /***
     * 直派逻辑
     *
     * @param dto -- 前端页面传来的分配对象
     * @param user -- 当前登录用户
     * @param companyId -- 用户所属企业ID
     * @return
     */
    Integer splitGoods4Direct(SplitGoodsParamsDto dto, User user, Long companyId);


}
