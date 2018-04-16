package com.lcdt.contract.dao;

import com.lcdt.contract.model.OrderApproval;
import com.lcdt.contract.web.dto.OrderApprovalDto;
import com.lcdt.contract.web.dto.OrderApprovalListDto;

import java.util.List;

public interface OrderApprovalMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_approval
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long oaId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_approval
     *
     * @mbggenerated
     */
    int insert(OrderApproval record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_approval
     *
     * @mbggenerated
     */
    int insertSelective(OrderApproval record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_approval
     *
     * @mbggenerated
     */
    OrderApproval selectByPrimaryKey(Long oaId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_approval
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(OrderApproval record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_approval
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(OrderApproval record);

    /**
     * 批量新增审批人信息
     * @param orderApprovalList
     * @return
     */
    int insertBatch(List<OrderApproval> orderApprovalList);


    /**
     * 根据orderId删除审批人信息
     * @param orderId
     * @return
     */
    int deleteByOrderId(Long orderId);


    /**
     * 订单审批列表
     * @param orderApprovalListDto
     * @return
     */
    List<OrderApprovalDto> selectOrderApprovalByCondition(OrderApprovalListDto orderApprovalListDto);

    int selectPendingNum(Long userId, Long companyId);

    /**
     * 更新审批人状态
     * @param orderApproval
     * @return
     */
    int updateStatus(OrderApproval orderApproval);

    List<OrderApproval> selectByOrderId(Long orderId);

    /**
     * 获取订单相关的审批人和抄送人
     * @param orderId
     * @return
     */
    List<OrderApproval> selectForOrderDetail(Long orderId);

    /**
     * 查询订单审批的抄送人
     * @param orderApprovalList
     * @return
     */
    List<OrderApproval> selectCC(Long orderId,List<OrderApproval> orderApprovalList);

}