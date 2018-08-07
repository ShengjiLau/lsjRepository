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
import com.lcdt.contract.service.PaApprovalService;
import com.lcdt.contract.web.dto.PaApprovalDto;
import com.lcdt.contract.web.dto.PaApprovalListDto;
import com.lcdt.notify.model.ContractNotifyEvent;
import com.lcdt.notify.model.DefaultNotifyReceiver;
import com.lcdt.notify.model.DefaultNotifySender;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.rpc.CompanyRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @AUTHOR liuh
 * @DATE 2018-05-10
 */
@Service
@Transactional
public class PaApprovalServiceImpl implements PaApprovalService {

    @Autowired
    private PaApprovalMapper paApprovalMapper;

    @Autowired
    private PaymentApplicationMapper paymentApplicationMapper;

    @Autowired
    private OrderProductMapper orderProductMapper;

    @Reference
    private CompanyRpcService companyRpcService;

    @Autowired
    private ContractNotifyProducer producer;

    @Override
    public PageInfo<List<PaApprovalDto>> paApprovalList(PaApprovalListDto paApprovalListDto, PageInfo pageInfo) {
        //根据需求审批创建起始时间加上时间
        if (null != paApprovalListDto.getPaymentTimeStart() && !"".equals(paApprovalListDto.getPaymentTimeStart())) {
            paApprovalListDto.setPaymentTimeStart(paApprovalListDto.getPaymentTimeStart() + " 00:00:00");
            paApprovalListDto.setPaymentTimeEnd(paApprovalListDto.getPaymentTimeEnd() + " 23:59:59");
        }
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        List<PaApprovalDto> paApprovalDtoList = paApprovalMapper.selectPaApprovalByCondition(paApprovalListDto);
        PageInfo page = new PageInfo(paApprovalDtoList);
        /**
         * 整合数据，
         * 审批中和已驳回的状态只保留一条审批人的信息
         * 已完成和已撤销的只设置审批创建人
         *
         * */
        for (PaApprovalDto pad : paApprovalDtoList) {
            PaApproval pa = null;
            for (PaApproval paApproval : pad.getPaApprovalList()) {
                if (null != paApproval.getSort()) {
                    //发起人及创建人
                    if (paApproval.getSort() == 0) {
                        pad.setApprovalCreateName(paApproval.getUserName());
                    }
                    /**审批状态为 1 3 4 代表审批流程正在执行的状态或者已经结束（撤销/驳回），可作为列表审批关键环节的展示*/
                    if (null != paApproval.getStatus()) {
                        //审批流程完成，无需再设置当前人
                        if (pad.getApprovalStatus() == 2) {
                            pad.setApprovalStatus(new Short("2"));
                        } else if (paApproval.getStatus() == 1 || paApproval.getStatus() == 3 || paApproval.getStatus() == 4) {
                            pa = new PaApproval();
                            pa.setPaaId(paApproval.getPaaId());
                            pa.setPaId(paApproval.getPaId());
                            if (null != paApproval.getDeptName()) {
                                pa.setDeptName(paApproval.getDeptName());
                            }
                            pa.setSort(paApproval.getSort());
                            pa.setUserId(paApproval.getUserId());
                            pa.setUserName(paApproval.getUserName());
                            pa.setActionType(paApproval.getActionType());
                            pa.setStatus(paApproval.getStatus());
                            //设置当前审批状态
                            pad.setApprovalStatus(paApproval.getStatus());
                            //审批流程完成，无需再设置当前人
                        } else if (pad.getApprovalStatus() == 2) {
                            pad.setApprovalStatus(new Short("2"));
                        }
                    }
                }
            }
            pad.getPaApprovalList().clear();
            if (null != pa) {
                pad.getPaApprovalList().add(pa);
            }
        }
        //整合货物信息
        List<String> stringList = new ArrayList<>();
        for (PaApprovalDto paDto : paApprovalDtoList) {
            stringList.add(paDto.getOrderId().toString());
        }
        if(stringList.size()>0){
            String[] orderIds = new String[stringList.size()];
            List<Map<Long, String>> mapList = this.orderProductInfo(stringList.toArray(orderIds));
            for (PaApprovalDto paDto : paApprovalDtoList) {
                for(Map<Long,String> map : mapList){
                    for (Long key : map.keySet()) {
                        if(paDto.getOrderId().longValue()== key.longValue()){
                            paDto.setProduct(map.get(key));
                        }
                    }

                }
            }
        }
        return page;
    }

    @Override
    public int pendingApprovalNum(Long userId, Long companyId, Short type) {
        return paApprovalMapper.selectPendingNum(userId, companyId);
    }

    @Override
    public int agreeApproval(PaApproval paApproval) {

        Long companyId = SecurityInfoGetter.getCompanyId();
        //审批状态
        paApproval.setStatus(new Short("2"));
        //审批时间
        paApproval.setTime(new Date());
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
            rows = paApprovalMapper.updateStatus(paApproval);
            List<PaApproval> caList = paApprovalMapper.selectByPaId(paApproval.getPaId());
            for (int i = 0; i < caList.size(); i++) {
                PaApproval pa = caList.get(i);
                //找出当前正在审核的人
                if (pa.getPaaId().equals(paApproval.getPaaId())) {
                    //如果正在审核的人为最后一人，则审批流程结束
                    if (pa.getSort().longValue() == caList.size()) {
                        rows += paymentApplicationMapper.updateApprovalStatus(paApproval.getPaId(), companyId, new Short("2"));
                        if (rows > 0) {
                            /**↓发送消息通知开始*/
                            //发送者
                            DefaultNotifySender defaultNotifySender = ContractNotifyBuilder.notifySender(companyId, pa.getUserId());
                            PaymentApplication paymentApplication = paymentApplicationMapper.selectByPrimaryKey(pa.getPaId());
                            User user = companyRpcService.selectByPrimaryKey(paymentApplication.getCreateId());
                            //接收者
                            DefaultNotifyReceiver defaultNotifyReceiver = ContractNotifyBuilder.notifyCarrierReceiver(paymentApplication.getCompanyId(), paymentApplication.getCreateId(), user.getPhone());
                            ContractAttachment attachment = new ContractAttachment();
                            attachment.setPaymentSerialNum(paymentApplication.getApplicationSerialNo());
                            attachment.setCarrierWebNotifyUrl(ContractNotifyBuilder.PAYMENT_WEB_NOTIFY_URL+paymentApplication.getApplicationSerialNo());
                            String eventName = "payment_approval_agree";
                            if (paymentApplication.getApplicationType().shortValue() == 1) {
                                eventName = "receivable_approval_agree";
                                attachment.setSaleRecSerialNum(paymentApplication.getApplicationSerialNo());
                            }
                            ContractNotifyEvent planPublishEvent = new ContractNotifyEvent(eventName, attachment, defaultNotifyReceiver, defaultNotifySender);
                            producer.sendNotifyEvent(planPublishEvent);
                            /**↑发送消息通知结束*/
                        }
                        break;
                    } else {  //否则更新下一位审核人状态为审批中
                        paApproval.setPaaId(caList.get(i + 1).getPaaId());
                        paApproval.setStatus(new Short("1"));
                        //置空之前设置的值
                        paApproval.setTime(null);
                        paApproval.setContent(null);
                        rows += paApprovalMapper.updateStatus(paApproval);
                        if (rows > 0) {
                            /**↓发送消息通知开始*/
                            //发送者
                            DefaultNotifySender defaultNotifySender = ContractNotifyBuilder.notifySender(companyId, pa.getUserId());
                            PaymentApplication paymentApplication = paymentApplicationMapper.selectByPrimaryKey(pa.getPaId());
                            PaApproval pApproval = caList.get(i+1);
                            User user = companyRpcService.selectByPrimaryKey(pApproval.getUserId());
                            //接收者
                            DefaultNotifyReceiver defaultNotifyReceiver = ContractNotifyBuilder.notifyCarrierReceiver(companyId, user.getUserId(), user.getPhone());
                            ContractAttachment attachment = new ContractAttachment();
                            attachment.setPaymentSerialNum(paymentApplication.getApplicationSerialNo());
                            attachment.setCarrierWebNotifyUrl(ContractNotifyBuilder.PAYMENT_WEB_NOTIFY_URL+paymentApplication.getApplicationSerialNo());
                            String eventName = "payment_approval_agree";
                            if (paymentApplication.getApplicationType().shortValue() == 1) {
                                eventName = "receivable_approval_agree";
                                attachment.setSaleRecSerialNum(paymentApplication.getApplicationSerialNo());
                            }
                            ContractNotifyEvent planPublishEvent = new ContractNotifyEvent(eventName, attachment, defaultNotifyReceiver, defaultNotifySender);
                            producer.sendNotifyEvent(planPublishEvent);
                            /**↑发送消息通知结束*/
                        }
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
    public int rejectApproval(PaApproval paApproval) {

        Long companyId = SecurityInfoGetter.getCompanyId();
        //审批状态
        paApproval.setStatus(new Short("4"));
        //审批时间
        paApproval.setTime(new Date());
        /**
         * 驳回的处理逻辑：即审批终止，
         * 1.设置当前人的审批状态为4 已驳回
         * 2.合同主表更新审批状态为4 已驳回
         * 3.设置合同主表审批结束时间
         */
        int rows = 0;
        try {
            rows = paApprovalMapper.updateStatus(paApproval);
            paymentApplicationMapper.updateApprovalStatus(paApproval.getPaId(), companyId, new Short("4"));
            if (rows > 0) {
                /**↓发送消息通知开始*/
                //发送者
                DefaultNotifySender defaultNotifySender = ContractNotifyBuilder.notifySender(companyId, SecurityInfoGetter.getUser().getUserId());
                PaymentApplication paymentApplication = paymentApplicationMapper.selectByPrimaryKey(paApproval.getPaId());
                User user = companyRpcService.selectByPrimaryKey(paymentApplication.getCreateId());
                //接收者
                DefaultNotifyReceiver defaultNotifyReceiver = ContractNotifyBuilder.notifyCarrierReceiver(paymentApplication.getCompanyId(), paymentApplication.getCreateId(), user.getPhone());
                ContractAttachment attachment = new ContractAttachment();
                attachment.setPaymentSerialNum(paymentApplication.getApplicationSerialNo());
                attachment.setCarrierWebNotifyUrl(ContractNotifyBuilder.PAYMENT_WEB_NOTIFY_URL+paymentApplication.getApplicationSerialNo());
                String eventName = "payment_approval_reject";
                if (paymentApplication.getApplicationType().shortValue() == 1) {
                    eventName = "receivable_approval_reject";
                    attachment.setSaleRecSerialNum(paymentApplication.getApplicationSerialNo());
                }
                ContractNotifyEvent planPublishEvent = new ContractNotifyEvent(eventName, attachment, defaultNotifyReceiver, defaultNotifySender);
                producer.sendNotifyEvent(planPublishEvent);
                /**↑发送消息通知结束*/
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RuntimeException("操作失败！");
        }
        return rows;
    }

    @Override
    public int revokeApproval(PaApproval paApproval) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        //审批状态
        paApproval.setStatus(new Short("3"));
        //审批时间
        paApproval.setTime(new Date());
        /**
         * 撤销的处理逻辑：即审批终止，
         * 1.设置当前人的审批状态为3 已撤销
         * 2.合同主表更新审批状态为3 已撤销
         * 3.设置合同主表审批结束时间
         */
        int rows = 0;
        try {
            rows = paApprovalMapper.updateStatus(paApproval);
            paymentApplicationMapper.updateApprovalStatus(paApproval.getPaId(), companyId, new Short("3"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RuntimeException("操作失败！");
        }
        return rows;
    }

    @Override
    public int turnDoApproval(List<PaApproval> paApprovalList) {
        //第一个为当前审批人
        PaApproval paApproval = paApprovalList.get(0);
        //审批状态 5 - 转办
        Long companyId = SecurityInfoGetter.getCompanyId();
        //审批时间
        paApproval.setStatus(new Short("5"));
        paApproval.setTime(new Date());
        /**
         * 转办处理逻辑：
         * 1.当前审批人状态置为 5 （转办）
         * 2.新增一条审批人记录，并设置对应sort为当前人的sort，并设置审批状态为审批中
         *
         */
        int rows = 0;
        try {
            rows = paApprovalMapper.updateStatus(paApproval);
            //第二个为接收转办的人
            PaApproval paApproval1 = paApprovalList.get(1);
            //获取当前人的审批记录
            PaApproval pa = paApprovalMapper.selectByPrimaryKey(paApproval.getPaaId());
            //设置审批顺序
            paApproval1.setSort(pa.getSort());
            paApproval1.setPaId(pa.getPaId());
            //设置审批状态为审批中
            paApproval1.setStatus(new Short("1"));
            //为审批类型
            paApproval1.setActionType(new Short("0"));
            paApprovalMapper.insert(paApproval1);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RuntimeException("操作失败！");
        }
        return rows;
    }

    @Override
    public int ccApproval(List<PaApproval> paApprovalList) {
        //第一个携带合同信息，合同主键paaId
        PaApproval paApproval = paApprovalList.get(0);
        Long companyId = SecurityInfoGetter.getCompanyId();
        Long paId = paApproval.getPaId();
        Long userId = SecurityInfoGetter.getUser().getUserId();
        //移除第一个只携带合同信息的记录，剩余的即为需要抄送的人员记录
        paApprovalList.remove(0);
        /**
         * 抄送处理逻辑：
         * 1.获取抄送合同的主键信息
         * 2.查询抄送人是否已经存在
         * 3.组织抄送人信息记录并批量更新
         */
        List<PaApproval> ccList = paApprovalMapper.selectCC(paId, paApprovalList);
        //剔除重复的抄送人
        for (PaApproval cal : ccList) {
            for (int i = 0; i < paApprovalList.size(); i++) {
                if (cal.getUserId().longValue() == paApprovalList.get(i).getUserId().longValue()) {
                    paApprovalList.remove(i);
                }
            }
        }
        int row = 0;
        try {
            for (PaApproval pa : paApprovalList) {
                pa.setPaId(paId);
                pa.setActionType(new Short("1"));
                pa.setStatus(new Short("0"));
                pa.setTime(new Date());
            }
            if (null != paApprovalList && paApprovalList.size() > 0) {
                row = paApprovalMapper.insertBatch(paApprovalList);
                if (row > 0) {
                    /**↓发送消息通知开始*/
                    //发送者
                    DefaultNotifySender defaultNotifySender = ContractNotifyBuilder.notifySender(companyId, userId);
                    PaymentApplication paymentApplication = paymentApplicationMapper.selectByPrimaryKey(paId);
                    for (PaApproval paApproval1 : paApprovalList) {
                        User user = companyRpcService.selectByPrimaryKey(paApproval1.getUserId());
                        //接收者
                        DefaultNotifyReceiver defaultNotifyReceiver = ContractNotifyBuilder.notifyCarrierReceiver(companyId, user.getUserId(), user.getPhone());
                        ContractAttachment attachment = new ContractAttachment();
                        attachment.setPaymentSerialNum(paymentApplication.getApplicationSerialNo());
                        attachment.setCarrierWebNotifyUrl(ContractNotifyBuilder.PAYMENT_WEB_NOTIFY_URL+paymentApplication.getApplicationSerialNo());
                        String eventName = "payment_approval_cc";
                        if (paymentApplication.getApplicationType().shortValue() == 1) {
                            eventName = "receivable_approval_cc";
                            attachment.setSaleRecSerialNum(paymentApplication.getApplicationSerialNo());
                        }
                        ContractNotifyEvent planPublishEvent = new ContractNotifyEvent(eventName, attachment, defaultNotifyReceiver, defaultNotifySender);
                        producer.sendNotifyEvent(planPublishEvent);
                    }
                    /**↑发送消息通知结束*/
                }
            } else {
                row = 1;
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RuntimeException("操作失败！");

        }
        return row;
    }

    @Override
    public List<Map<Long, String>> orderProductInfo(String[] orderIds) {
        List<Map<Long, String>> mapList = new ArrayList<>();
        List<OrderProduct> orderProductList = orderProductMapper.selectProductByOrderIds(orderIds);
        Map<Long, String> map = null;
        for (int i = 0; i < orderProductList.size(); i++) {
            map = new HashMap<>();
            OrderProduct orderProduct = orderProductList.get(i);
            //先尝试获取一下该orderId是否已经存在，组合后重新put值
            String temp = map.get(orderProduct.getOrderId()) == null ? "" : "、" + map.get(orderProduct.getOrderId());
            map.put(orderProduct.getOrderId(), orderProduct.getName() + orderProduct.getNum() + orderProduct.getSku() + temp);
            mapList.add(map);
        }
        return mapList;
    }

}
