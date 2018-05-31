package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dto.PlanDetailParamsDto;
import com.lcdt.traffic.model.PlanLeaveMsg;
import com.lcdt.traffic.model.WaybillPlan;
import com.lcdt.traffic.web.dto.PlanLeaveMsgParamsDto;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;

import java.util.List;
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
    Integer ownPlanCancel(Long waybillPlanId,  UserCompRel userCompRel);



    /***
     * 运单回传更改计划状态
     * @param waybillPlan
     */
    int updatePlanStatusByWaybill(WaybillPlan waybillPlan);



    /***
     * 调整计划剩余数量
     * @param dtoList
     * @param userCompRel
     * @return
     */
    int adjustPlanRemainAmount(List<PlanDetailParamsDto> dtoList, UserCompRel userCompRel);

}
