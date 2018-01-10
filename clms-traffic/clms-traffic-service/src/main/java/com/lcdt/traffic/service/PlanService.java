package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.exception.WaybillPlanException;
import com.lcdt.traffic.model.PlanLeaveMsg;
import com.lcdt.traffic.model.WaybillPlan;
import com.lcdt.traffic.web.dto.PlanLeaveMsgParamsDto;
import com.lcdt.traffic.web.dto.WaybillParamsDto;
import com.lcdt.userinfo.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2017/12/13.
 */
public interface PlanService {

    /***
     * 发布计划
     *
     * @param dto-- 更新人、公司等信息
     *
     * @return
     */
    WaybillPlan publishWayBillPlan(WaybillParamsDto dto);


    /***
     * 计划审核
     *
     * @param dto-- 更新人、公司等信息
     *
     * @return
     */
    WaybillPlan wayBillPlanCheckPass(WaybillParamsDto dto);

    /***
     * 计划查询列表
     *
     * @param map
     * @return
     */
    PageInfo wayBillPlanList(Map map);



    /***
     * 拉取计划详细信息
     *
     * @param dto
     * @return
     */
    WaybillPlan loadWaybillPlan(WaybillParamsDto dto);



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




}
