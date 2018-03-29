package com.lcdt.traffic.service;

import com.lcdt.traffic.dto.BindingSplitParamsDto;
import com.lcdt.traffic.model.SplitGoodsDetail;
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
     * @param userCompRel -- 当前登录企业用户
     * @param companyId -- 用户所属企业ID
     * @return
     */
    Integer splitGoods4Direct(SplitGoodsParamsDto dto, UserCompRel userCompRel, Long companyId);




    /***
     * 竞价派单-承运商
     *
     * @param dto -- 前端页面传来的分配对象
     * @param user -- 当前登录用户
     * @param companyId -- 用户所属企业ID
     * @return
     */
    Integer splitGoods4Bidding(BindingSplitParamsDto dto, User user, Long companyId);


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
    Integer waybillCancel4SplitGoods(List<SplitGoodsDetail> splitGoodsDetails);





}
