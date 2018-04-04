package com.lcdt.traffic.service;

import com.lcdt.traffic.dto.BindingSplitParamsDto;
import com.lcdt.traffic.dto.SplitGoodsParamsDto;
import com.lcdt.traffic.model.SplitGoodsDetail;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;

import java.util.List;

/**
 * Created by yangbinq on 2017/12/21.
 */
public interface SplitGoodsService {


    /***
     * 派单取消
     *
     * @param splitGoodsId
     * @param splitGoodsDetailId
     * @param user
     * @param companyId
     */
    Integer splitGoodsCancel(Long splitGoodsId, Long splitGoodsDetailId, User user, Long companyId);


    /***
     * 运单取消回传派单数据
     * @param splitGoodsDetails
     * @return
     */
    Integer waybillCancel4SplitGoods(List<SplitGoodsDetail> splitGoodsDetails, Long waybillPalnId);





}
