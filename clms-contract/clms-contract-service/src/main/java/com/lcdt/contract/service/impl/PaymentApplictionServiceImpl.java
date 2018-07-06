package com.lcdt.contract.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.dao.OrderProductMapper;
import com.lcdt.contract.dao.PaApprovalMapper;
import com.lcdt.contract.dao.PaymentApplicationMapper;
import com.lcdt.contract.model.OrderProduct;
import com.lcdt.contract.model.PaApproval;
import com.lcdt.contract.model.PaymentApplication;
import com.lcdt.contract.notify.ContractAttachment;
import com.lcdt.contract.notify.ContractNotifyBuilder;
import com.lcdt.contract.notify.ContractNotifyProducer;
import com.lcdt.contract.service.PaymentApplictionService;
import com.lcdt.contract.web.dto.PaymentApplicationDto;
import com.lcdt.notify.model.ContractNotifyEvent;
import com.lcdt.notify.model.DefaultNotifyReceiver;
import com.lcdt.notify.model.DefaultNotifySender;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.rpc.CompanyRpcService;
import org.jboss.netty.handler.execution.OrderedDownstreamThreadPoolExecutor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @AUTHOR liuh
 * @DATE 2018-05-08
 */
@Service
@Transactional
public class PaymentApplictionServiceImpl implements PaymentApplictionService {

    @Autowired
    private PaymentApplicationMapper paymentApplicationMapper;
    @Autowired
    private PaApprovalMapper paApprovalMapper;
    @Reference
    private CompanyRpcService companyRpcService;
    @Autowired
    private ContractNotifyProducer producer;

    @Override
    public PageInfo<List<PaymentApplication>> paymentApplictionList(PaymentApplicationDto paymentApplicationDto, PageInfo pageInfo) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        List<PaymentApplication> paymentApplicationList = paymentApplicationMapper.selectByCondition(paymentApplicationDto);
        PageInfo page = new PageInfo(paymentApplicationList);
        return page;
    }

    @Override
    public int addPaymentAppliction(PaymentApplicationDto paymentApplicationDto) {
        PaymentApplication paymentApplication = new PaymentApplication();
        //拷贝属性
        BeanUtils.copyProperties(paymentApplicationDto, paymentApplication);
        //插入付款单记录
        int row = paymentApplicationMapper.insertSelective(paymentApplication);
        if(row>0){
            //付款单记录插入成功后，进行审批相关的插入
            if(null != paymentApplicationDto.getPaApprovalList() && paymentApplicationDto.getPaApprovalList().size()>0){
                /*1.加入创建人信息 2.设置关联的合同id 3.批量插入审批人信息*/
                for(PaApproval paApproval : paymentApplicationDto.getPaApprovalList()){
                    //设置关联付款单
                    paApproval.setPaId(paymentApplication.getPaId());
                    if(paApproval.getActionType().shortValue()==0){
                        if(paApproval.getSort()==1){
                            //同时设置第一个审批的人的状态为审批中
                            paApproval.setStatus(new Short("1"));
                            /**↓发送消息通知开始*/
                            //发送
                            DefaultNotifySender defaultNotifySender = ContractNotifyBuilder.notifySender(paymentApplicationDto.getCompanyId(), paymentApplicationDto.getCreateId());
                            User user = companyRpcService.selectByPrimaryKey(paApproval.getUserId());
                            PaymentApplication queryApplication = paymentApplicationMapper.selectByPrimaryKey(paymentApplication.getPaId());
                            //接收
                            DefaultNotifyReceiver defaultNotifyReceiver = ContractNotifyBuilder.notifyCarrierReceiver(paymentApplicationDto.getCompanyId(), user.getUserId(), user.getPhone());
                            ContractAttachment attachment = new ContractAttachment();
                            attachment.setEmployee(paymentApplicationDto.getCreateName());
                            attachment.setPerPaymentSerialNum(queryApplication.getApplicationSerialNo());
                            attachment.setCarrierWebNotifyUrl("");
                            String eventName = "payment_approval_publish";
                            /*if (dto.getType().shortValue() == 1) {
                                eventName = "sale_approval_publish";
                                attachment.setPerPaymentSerialNum(dto.getSerialNo());
                            }*/
                            ContractNotifyEvent plan_publish_event = new ContractNotifyEvent(eventName, attachment, defaultNotifyReceiver, defaultNotifySender);
                            producer.sendNotifyEvent(plan_publish_event);
                        }else{
                            //设置其他审批状态为 0 - 初始值
                            paApproval.setStatus(new Short("0"));
                        }
                    }else{
                        //设置其他审批状态为 0 - 初始值
                        paApproval.setStatus(new Short("0"));
                    }
                }
                PaApproval approval = new PaApproval();
                User user = SecurityInfoGetter.getUser();
                UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
                approval.setPaId(paymentApplication.getPaId());
                approval.setUserName(user.getRealName());
                approval.setUserId(user.getUserId());
                approval.setDeptName(userCompRel.getDeptNames());
                // 0 为创建着
                approval.setSort(0);
                //默认actionType 0
                approval.setActionType(new Short("0"));
                approval.setStatus(new Short("2"));
                approval.setTime(new Date());
                paymentApplicationDto.getPaApprovalList().add(approval);
                //批量插入审批人信息
                paApprovalMapper.insertBatch(paymentApplicationDto.getPaApprovalList());
                //设置付款单审批状态为审批中
                paymentApplication.setApprovalStatus(new Short("1"));
                //设置审批开始时间
                paymentApplication.setApprovalStartDate(new Date());
            }else{
                //设置付款单审批状态为无需审批
                paymentApplication.setApprovalStatus(new Short("0"));
            }
            //更新付款单的审批状态
            paymentApplicationMapper.updateByPrimaryKeySelective(paymentApplication);
        }
        return row;
    }



    @Override
    public PaymentApplicationDto paymentApplictionDetail(Long paId){
        return paymentApplicationMapper.selectPaymentApplicationDetail(paId);
    }

    @Override
    public int confirmPayment(PaymentApplication paymentApplication){
        int row = 0;
        row = paymentApplicationMapper.updateByPrimaryKeySelective(paymentApplication);
        Long companyId = SecurityInfoGetter.getCompanyId();
        Long userId = SecurityInfoGetter.getUser().getUserId();
        if(row>0){
            /**↓发送消息通知开始*/
            //发送者
            DefaultNotifySender defaultNotifySender = ContractNotifyBuilder.notifySender(companyId, userId);
            PaymentApplication pa = paymentApplicationMapper.selectByPrimaryKey(paymentApplication.getPaId());
            User user = companyRpcService.selectByPrimaryKey(pa.getCreateId());
            //接收者
            DefaultNotifyReceiver defaultNotifyReceiver = ContractNotifyBuilder.notifyCarrierReceiver(companyId, user.getUserId(), user.getPhone());
            ContractAttachment attachment = new ContractAttachment();
            attachment.setPerPaymentSerialNum(pa.getApplicationSerialNo());
            attachment.setCarrierWebNotifyUrl("");
            String eventName = "payment_confirm";
                /*if (paymentApplication.getType().shortValue() == 1) {
                    eventName = "sale_approval_reject";
                    attachment.setSaleOrderSerialNum(order.getOrderSerialNo());
                }*/
            ContractNotifyEvent plan_publish_event = new ContractNotifyEvent(eventName, attachment, defaultNotifyReceiver, defaultNotifySender);
            producer.sendNotifyEvent(plan_publish_event);
            /**↑发送消息通知结束*/
        }
        return row;
    }
}
