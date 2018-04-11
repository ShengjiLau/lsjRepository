package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.model.PlanLeaveMsg;
import com.lcdt.traffic.model.WaybillPlan;
import com.lcdt.traffic.web.dto.PlanLeaveMsgParamsDto;
import com.lcdt.userinfo.model.User;

import java.util.Map;

/**
 * Created by yangbinq on 2017/12/13.
 */
public interface PlanService {



    /***
     * 计划留言
     * @param dto
     * @return
     */
    PlanLeaveMsg planLeaveMsgAdd(PlanLeaveMsgParamsDto dto);


    /***
     * 留言列表
     * @param map
     * @return
     */
    PageInfo planLeaveMsgList(Map map);


    /***
     * 删除计划商品
     * @param planDetailId
     * @param companyId
     * @return
     */
    Integer planDetailDelete(Long planDetailId, Long companyId);


    /***
     * 计划取消
     * @param waybillPlanId
     * @param companyId
     * @param user
     * @return
     */
    Integer ownPlanCancel(Long waybillPlanId, Long companyId, User user);



    /***
     * 运单回传更改计划状态
     * @param waybillPlan
     */
    int updatePlanStatusByWaybill(WaybillPlan waybillPlan);




}
