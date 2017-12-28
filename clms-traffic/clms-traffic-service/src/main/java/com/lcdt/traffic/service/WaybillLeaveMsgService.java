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
     * 修改留言
     * @param dto
     * @return
     */
    int modifyWaybillLeaveMsg(WaybillLeaveMsgDto dto);

    /**
     * 查询留言
     * @param id
     * @param companyId
     * @return
     */
    WaybillLeaveMsg queryWaybillLeaveMsg(Long id,Long companyId);

    /**
     * 查询留言列表
     * @param dto
     * @param pageInfo
     * @return
     */
    PageInfo queryWaybillLeaveMsgList(WaybillLeaveMsgDto dto,PageInfo pageInfo);
}
