package com.lcdt.contract.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.contract.model.PaApproval;
import com.lcdt.contract.web.dto.PaApprovalDto;
import com.lcdt.contract.web.dto.PaApprovalListDto;

import java.util.List;
import java.util.Map;

/**
 * @AUTHOR liuh
 * @DATE 2018-05-07
 */
public interface PaApprovalService {

    /**
     * 获取审批列表
     *
     * @param paApprovalListDto
     * @return
     */
    PageInfo<List<PaApprovalDto>> paApprovalList(PaApprovalListDto paApprovalListDto, PageInfo pageInfo);

    /**
     * 获取待审批付款单数量
     * @param userId
     * @param companyId
     * @return
     */
    int pendingApprovalNum(Long userId, Long companyId, Short type);

    /**
     * 同意审批
     *
     * @param paApproval
     * @return
     */
    int agreeApproval(PaApproval paApproval);

    /**
     * 驳回审批
     * @param paApproval
     * @return
     */
    int rejectApproval(PaApproval paApproval);

    /**
     * 撤销审批
     * @param paApproval
     * @return
     */
    int revokeApproval(PaApproval paApproval);

    /**
     * 转办
     * @param paApprovalList
     * @return
     */
    int turnDoApproval(List<PaApproval> paApprovalList);

    /**
     * 抄送
     * @param paApprovalList
     * @return
     */
    int ccApproval(List<PaApproval> paApprovalList);

    /**
     * 根据orderIds数组批量获取对应的产品信息
     * @param orderIds
     * @return
     */
    List<Map<Long,String>> orderProductInfo(String[] orderIds);
}
