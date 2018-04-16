package com.lcdt.contract.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.contract.model.OrderApproval;
import com.lcdt.contract.web.dto.OrderApprovalDto;
import com.lcdt.contract.web.dto.OrderApprovalListDto;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2018-03-05
 */
public interface OrderApprovalService {

    /**
     * 获取审批列表
     *
     * @param orderApprovalListDto
     * @return
     */
    PageInfo<List<OrderApprovalDto>> orderApprovalList(OrderApprovalListDto orderApprovalListDto, PageInfo pageInfo);

    /**
     * 获取待审批订单数量
     * @param userId
     * @param companyId
     * @return
     */
    int pendingApprovalNum(Long userId, Long companyId);

    /**
     * 同意审批
     *
     * @param orderApproval
     * @return
     */
    int agreeApproval(OrderApproval orderApproval);

    /**
     * 驳回审批
     * @param orderApproval
     * @return
     */
    int rejectApproval(OrderApproval orderApproval);

    /**
     * 撤销审批
     * @param orderApproval
     * @return
     */
    int revokeApproval(OrderApproval orderApproval);

    /**
     * 转办
     * @param orderApprovalList
     * @return
     */
    int turnDoApproval(List<OrderApproval> orderApprovalList);

    /**
     * 抄送
     * @param orderApprovalList
     * @return
     */
    int ccApproval(List<OrderApproval> orderApprovalList);

}

