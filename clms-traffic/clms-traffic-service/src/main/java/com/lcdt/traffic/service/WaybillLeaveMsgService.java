package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.model.WaybillLeaveMsg;
import com.lcdt.traffic.web.dto.WaybillLeaveMsgDto;

/**
 * Created by lyqishan on 2017/12/28
 */

public interface WaybillLeaveMsgService {
    /**
     * 新增留言
     * @param dto
     * @return
     */
    int addWaybillLeaveMsg(WaybillLeaveMsgDto dto);

    /**
     * 删除留言
     * @param id
     * @param companyId
     * @return
     */
    int deleteWaybillLeaveMsg(Long id,Long companyId);

    /**
     * 运单留言修改：客户运单需要传递参数（id,carrierCompanyId,和其它参数）;我的运单需要传递参数（id,companyId,和其它参数）
     * @param dto
     * @return
     */
    int modifyWaybillLeaveMsg(WaybillLeaveMsgDto dto);

    /**
     * 运单留言查询：客户运单需要传递参数（id,carrierCompanyId,和其它参数）;我的运单需要传递参数（id,companyId,和其它参数）
     * @param dto
     * @return
     */
    WaybillLeaveMsg queryWaybillLeaveMsg(WaybillLeaveMsgDto dto);

    /**
     * 运单留言列表：客户运单需要传递参数（id,carrierCompanyId,和其它参数）;我的运单需要传递参数（id,companyId,和其它参数）
     * @param dto
     * @param pageInfo
     * @return
     */
    PageInfo queryWaybillLeaveMsgList(WaybillLeaveMsgDto dto,PageInfo pageInfo);
}
