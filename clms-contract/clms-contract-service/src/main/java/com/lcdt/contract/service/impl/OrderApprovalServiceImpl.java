package com.lcdt.contract.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.dao.OrderApprovalMapper;
import com.lcdt.contract.dao.OrderMapper;
import com.lcdt.contract.model.OrderApproval;
import com.lcdt.contract.service.OrderApprovalService;
import com.lcdt.contract.web.dto.OrderApprovalDto;
import com.lcdt.contract.web.dto.OrderApprovalListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2018-03-05
 */
@Service
@Transactional
public class OrderApprovalServiceImpl implements OrderApprovalService {

    @Autowired
    private OrderApprovalMapper orderApprovalMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public PageInfo<List<OrderApprovalDto>> orderApprovalList(OrderApprovalListDto orderApprovalListDto, PageInfo pageInfo) {
        if(null!=orderApprovalListDto.getApprovalStartDate() && !"".equals(orderApprovalListDto.getApprovalStartDate())){
            orderApprovalListDto.setApprovalStartDate(orderApprovalListDto.getApprovalStartDate()+" 00:00:00");
            orderApprovalListDto.setApprovalEndDate(orderApprovalListDto.getApprovalEndDate()+" 23:59:59");
        }
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        List<OrderApprovalDto> orderApprovalDtoList = orderApprovalMapper.selectOrderApprovalByCondition(orderApprovalListDto);
        PageInfo page = new PageInfo(orderApprovalDtoList);
        /**
         * 整合数据，
         * 审批中和已驳回的状态只保留一条审批人的信息
         * 已完成和已撤销的只设置审批创建人
         *
         * */
        for(OrderApprovalDto oad : orderApprovalDtoList){
            OrderApproval oa = null;
            for(OrderApproval orderApproval : oad.getOrderApprovalList()){
                if(null != orderApproval.getSort()) {
                    if (orderApproval.getSort() == 0) {  //发起人及创建人
                        oad.setApprovalCreateName(orderApproval.getUserName());
                    }
                    /**审批状态为 1 3 4 代表审批流程正在执行的状态或者已经结束（撤销/驳回），可作为列表审批关键环节的展示*/
                    if (null != orderApproval.getStatus()) {
                        if (orderApproval.getStatus() == 1 || orderApproval.getStatus() == 3 || orderApproval.getStatus() == 4) {
                            oa = new OrderApproval();
                            oa.setOaId(orderApproval.getOaId());
                            oa.setOrderId(orderApproval.getOrderId());
                            if(null!=orderApproval.getDeptName()){
                                oa.setDeptName(orderApproval.getDeptName());
                            }
                            oa.setSort(orderApproval.getSort());
                            oa.setUserId(orderApproval.getUserId());
                            oa.setUserName(orderApproval.getUserName());
                            oa.setActionType(orderApproval.getActionType());
                            oa.setStatus(orderApproval.getStatus());
                            oad.setApprovalStatus(orderApproval.getStatus());   //设置当前审批状态
                        }else if (oad.getApprovalStatus() == 2) {   //审批流程完成，无需再设置当前人
                            oad.setApprovalStatus(new Short("2"));
                        }
                    }
                }
            }
            oad.getOrderApprovalList().clear();
            if(null!=oa){
                oad.getOrderApprovalList().add(oa);
            }
        }
        return page;
    }

    @Override
    public int pendingApprovalNum(Long userId, Long companyId, Short type){
        return orderApprovalMapper.selectPendingNum(userId,companyId,type);
    }

    @Override
    public int agreeApproval(OrderApproval orderApproval) {

        Long companyId = SecurityInfoGetter.getCompanyId();
        orderApproval.setStatus(new Short("2"));     //审批状态
        orderApproval.setTime(new Date());   //审批时间
        /**
         * 同意审批：
         * 1.审批人状态变为已完成
         * 2.下一位审批人状态变为审批中
         *
         * 如果是最后一个审批人
         * 1.则更新合同表审批状态为2 - 已完成
         * 2.审批结束时间随之更新
         */
        int rows = 0;
        try {
            rows = orderApprovalMapper.updateStatus(orderApproval);
            List<OrderApproval> caList = orderApprovalMapper.selectByOrderId(orderApproval.getOrderId());
            for(int i=0;i<caList.size();i++){
                OrderApproval ca = caList.get(i);
                if(ca.getOaId().equals(orderApproval.getOaId())){    //找出当前正在审核的人
                    if(ca.getSort().longValue()==caList.size()){  //如果正在审核的人为最后一人，则审批流程结束
                        rows = orderMapper.updateApprovalStatus(orderApproval.getOrderId(),companyId,new Short("2"));
                        break;
                    }else{  //否则更新下一位审核人状态为审批中
                        orderApproval.setOaId(caList.get(i+1).getOaId());
                        orderApproval.setStatus(new Short("1"));
                        orderApproval.setTime(null);   //置空之前设置的值
                        orderApproval.setContent(null);
                        rows = orderApprovalMapper.updateStatus(orderApproval);
                        break;
                    }
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RuntimeException("操作失败!");
        }
        return rows;
    }

    @Override
    public int rejectApproval(OrderApproval orderApproval) {

        Long companyId = SecurityInfoGetter.getCompanyId();
        orderApproval.setStatus(new Short("4"));     //审批状态
        orderApproval.setTime(new Date());   //审批时间
        /**
         * 驳回的处理逻辑：即审批终止，
         * 1.设置当前人的审批状态为4 已驳回
         * 2.合同主表更新审批状态为4 已驳回
         * 3.设置合同主表审批结束时间
         */
        int rows = 0;
        try {
            rows += orderApprovalMapper.updateStatus(orderApproval);
            rows += orderMapper.updateApprovalStatus(orderApproval.getOrderId(),companyId,new Short("4"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RuntimeException("操作失败！");
        }
        return rows;
    }

    @Override
    public int revokeApproval(OrderApproval orderApproval) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        orderApproval.setStatus(new Short("3"));     //审批状态
        orderApproval.setTime(new Date());   //审批时间
        /**
         * 撤销的处理逻辑：即审批终止，
         * 1.设置当前人的审批状态为3 已撤销
         * 2.合同主表更新审批状态为3 已撤销
         * 3.设置合同主表审批结束时间
         */
        int rows = 0;
        try {
            rows += orderApprovalMapper.updateStatus(orderApproval);
            rows += orderMapper.updateApprovalStatus(orderApproval.getOrderId(),companyId,new Short("3"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RuntimeException("操作失败！");
        }
        return rows;
    }

    @Override
    public int turnDoApproval(List<OrderApproval> orderApprovalList) {
        OrderApproval orderApproval =  orderApprovalList.get(0);    //第一个为当前审批人
        Long companyId = SecurityInfoGetter.getCompanyId();
        orderApproval.setStatus(new Short("5"));     //审批状态 5 - 转办
        orderApproval.setActionType(new Short("0"));
        orderApproval.setTime(new Date());   //审批时间
        /**
         * 转办处理逻辑：
         * 1.当前审批人状态置为 5 （转办）
         * 2.新增一条审批人记录，并设置对应sort为当前人的sort，并设置审批状态为审批中
         *
         */
        int rows = 0;
        try {
            rows = orderApprovalMapper.updateStatus(orderApproval);
            OrderApproval orderApproval1 = orderApprovalList.get(1);   //第二个为接收转办的人
            //获取当前人的审批记录
            OrderApproval oa = orderApprovalMapper.selectByPrimaryKey(orderApproval.getOaId());
            orderApproval1.setSort(oa.getSort());    //设置审批顺序
            orderApproval1.setOrderId(oa.getOrderId());
            orderApproval1.setStatus(new Short("1"));   //设置审批状态为审批中
            orderApproval1.setActionType(new Short("0"));    //为审批类型
            if(rows>0){
                orderApprovalMapper.insert(orderApproval1);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RuntimeException("操作失败！");
        }
        return rows;
    }

    @Override
    public int ccApproval(List<OrderApproval> orderApprovalList) {
        OrderApproval orderApproval =  orderApprovalList.get(0);    //第一个携带合同信息，合同主键orderId
        Long companyId = SecurityInfoGetter.getCompanyId();
        Long orderId = orderApproval.getOrderId();
        orderApprovalList.remove(0); //移除第一个只携带合同信息的记录，剩余的即为需要抄送的人员记录
        /**
         * 抄送处理逻辑：
         * 1.获取抄送合同的主键信息
         * 2.查询抄送人是否已经存在
         * 3.组织抄送人信息记录并批量更新
         */
        List<OrderApproval> ccList = orderApprovalMapper.selectCC(orderId,orderApprovalList);
        //剔除重复的抄送人
        for(OrderApproval oal : ccList){
            for(int i=0; i<orderApprovalList.size(); i++){
                if(oal.getUserId().longValue()==orderApprovalList.get(i).getUserId().longValue()){
                    orderApprovalList.remove(i);
                }
            }
        }
        int row = 0;
        try {
            for(OrderApproval oa : orderApprovalList){
                oa.setOrderId(orderId);
                oa.setActionType(new Short("1"));
                oa.setStatus(new Short("0"));
                oa.setTime(new Date());
            }
            if(null != orderApprovalList && orderApprovalList.size()>0){
                row = orderApprovalMapper.insertBatch(orderApprovalList);
            }else{
                row = 1;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RuntimeException("操作失败！");

        }
        return row;
    }



    
}
