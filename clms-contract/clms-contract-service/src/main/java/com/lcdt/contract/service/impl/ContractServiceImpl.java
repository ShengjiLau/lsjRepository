package com.lcdt.contract.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.dao.ContractApprovalMapper;
import com.lcdt.contract.dao.ContractLogMapper;
import com.lcdt.contract.dao.ContractMapper;
import com.lcdt.contract.dao.ContractProductMapper;
import com.lcdt.contract.model.Contract;
import com.lcdt.contract.model.ContractApproval;
import com.lcdt.contract.model.ContractLog;
import com.lcdt.contract.model.ContractProduct;
import com.lcdt.contract.notify.ContractAttachment;
import com.lcdt.contract.notify.ContractNotifyBuilder;
import com.lcdt.contract.notify.ContractNotifyProducer;
import com.lcdt.contract.service.ContractService;
import com.lcdt.contract.web.dto.ContractDto;
import com.lcdt.notify.model.ContractNotifyEvent;
import com.lcdt.notify.model.DefaultNotifyReceiver;
import com.lcdt.notify.model.DefaultNotifySender;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.rpc.CompanyRpcService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tl.commons.util.StringUtility;

import java.math.BigDecimal;
import java.util.*;

/**
 * @AUTHOR liuh
 * @DATE 2018-02-28
 */
@Service
@Transactional
public class ContractServiceImpl implements ContractService {
    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private ContractProductMapper contractProductMapper;
    @Autowired
    private ContractApprovalMapper contractApprovalMapper;
    @Autowired
    private ContractLogMapper contractLogMapper;
    @Reference
    private CompanyRpcService companyRpcService;
    @Autowired
    private ContractNotifyProducer producer;

    @Override
    public int addContract(ContractDto dto) {
        BigDecimal summation = new BigDecimal(0);// 为所有商品价格求和
        if (null != dto.getContractProductList() && dto.getContractProductList().size() != 0) {
            for (ContractProduct contractProduct : dto.getContractProductList()) {
                BigDecimal num = contractProduct.getNum() == null ? BigDecimal.valueOf(0) : contractProduct.getNum();
                BigDecimal price = contractProduct.getPrice() == null ? BigDecimal.valueOf(0) : contractProduct.getPrice();
                //计算单个商品总价
                BigDecimal total = num.multiply(price);
                summation = summation.add(total);
                contractProduct.setTotal(total);
            }
        }
        dto.setSummation(summation);
        Contract contract = new Contract();
        BeanUtils.copyProperties(dto, contract); //复制对象属性
        int result = contractMapper.insert(contract);
        if (dto.getContractProductList() != null && dto.getContractProductList().size() > 0) {
            //设置商品的合同id
            for (ContractProduct contractProduct : dto.getContractProductList()) {
                contractProduct.setContractId(contract.getContractId());
            }
            result += contractProductMapper.insertBatch(dto.getContractProductList());  //批量插入商品
        }
        //审批流程添加
        if (null != dto.getContractApprovalList() && dto.getContractApprovalList().size() > 0) {
            /*1.加入创建人信息 2.设置关联的合同id 3.批量插入审批人信息*/

            for (ContractApproval ca : dto.getContractApprovalList()) {
                ca.setContractId(contract.getContractId()); //设置关联合同id
                if (ca.getActionType().shortValue() == 0) {
                    if (ca.getSort() == 1) {
                        ca.setStatus(new Short("1"));   //同时设置第一个审批的人的状态为审批中
                        /**↓发送消息通知开始*/
                        //发送
                        DefaultNotifySender defaultNotifySender = ContractNotifyBuilder.notifySender(dto.getCompanyId(), dto.getCreateId());
                        User user = companyRpcService.selectByPrimaryKey(ca.getUserId());
                        Contract queryContract = contractMapper.selectByPrimaryKey(contract.getContractId());
                        //接收
                        DefaultNotifyReceiver defaultNotifyReceiver = ContractNotifyBuilder.notifyCarrierReceiver(dto.getCompanyId(), user.getUserId(), user.getPhone());
                        ContractAttachment attachment = new ContractAttachment();
                        attachment.setEmployee(queryContract.getCreateName());
                        attachment.setPurConTittle(queryContract.getTitle());
                        attachment.setPurConSerialNum(queryContract.getSerialNo());
                        attachment.setCarrierWebNotifyUrl(ContractNotifyBuilder.CONTRACT_WEB_NOTIFY_URL+queryContract.getSerialNo());
                        String eventName = "purchase_approval_publish";
                        if (dto.getType().shortValue() == 1) {
                            eventName = "sale_approval_publish";
                            attachment.setSaleConTittle(queryContract.getTitle());
                            attachment.setSaleConSerialNum(queryContract.getSerialNo());
                        }
                        ContractNotifyEvent plan_publish_event = new ContractNotifyEvent(eventName, attachment, defaultNotifyReceiver, defaultNotifySender);
                        producer.sendNotifyEvent(plan_publish_event);
                        /**↑发送消息通知结束*/
                    } else {
                        ca.setStatus(new Short("0"));   //设置其他审批状态为 0 - 初始值
                    }
                } else {
                    ca.setStatus(new Short("0"));   //设置其他审批状态为 0 - 初始值
                }
            }
            ContractApproval contractApproval = new ContractApproval();
//            Long companyId = SecurityInfoGetter.getCompanyId();
            User user = SecurityInfoGetter.getUser();
            UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
            contractApproval.setContractId(contract.getContractId());
            contractApproval.setUserName(user.getRealName());
            contractApproval.setUserId(user.getUserId());
            contractApproval.setDeptName(userCompRel.getDeptNames());
            contractApproval.setSort(0);    // 0 为创建着
            contractApproval.setActionType(new Short("0")); //默认actionType 0
            contractApproval.setStatus(new Short("2"));
            contractApproval.setTime(new Date());
            dto.getContractApprovalList().add(contractApproval);
            contractApprovalMapper.insertBatch(dto.getContractApprovalList());
            //同时设置合同的审批状态为审批中
            contract.setApprovalStatus(new Short("1"));
            contract.setApprovalStartDate(new Date());
            contractMapper.updateByPrimaryKeySelective(contract);
        } else {
            // todo 没有添加审批人，则认为合同无需审批
            //同时设置合同的审批状态为审批中
            contract.setApprovalStatus(new Short("0"));
            contractMapper.updateByPrimaryKeySelective(contract);
        }
        //新增日志
        ContractLog log = new ContractLog();
        log.setContractId(contract.getContractId());
        log.setLogName("新增");
        log.setLogContent("新增合同" + (contract.getIsDraft() == 0 ? "" : "，并发布"));
        if (StringUtility.isNotEmpty(contract.getAttachment1())) {
            log.setLogContent(log.getLogContent() + "，" + setAttachmentLog(contract.getAttachment1()));
        }
        saveContractLog(log);
        return result;
    }

    @Override
    public int modContract(ContractDto dto) {
        //修改之前的合同
        //      Contract oldContract = contractMapper.selectByPrimaryKey(dto.getContractId());

        BigDecimal summation = new BigDecimal(0);// 为所有商品价格求和
        if (null != dto.getContractProductList() && dto.getContractProductList().size() != 0) {
            for (ContractProduct contractProduct : dto.getContractProductList()) {
                BigDecimal num = contractProduct.getNum() == null ? BigDecimal.valueOf(0) : contractProduct.getNum();
                BigDecimal price = contractProduct.getPrice() == null ? BigDecimal.valueOf(0) : contractProduct.getPrice();
                //计算单个商品总价
                BigDecimal total = num.multiply(price);
                summation = summation.add(total);
                contractProduct.setTotal(total);
            }
        }
        dto.setSummation(summation);
        Contract contract = new Contract();
        BeanUtils.copyProperties(dto, contract); //复制对象属性

        //得到修改之前合同
        Contract oldContract = contractMapper.selectByPrimaryKey(contract.getContractId());

        int result = contractMapper.updateByPrimaryKeySelective(contract);
        //获取该合同下面的商品cpId用来匹配被删除的商品
        List<Long> cpIdList = contractProductMapper.selectCpIdsByContractId(contract.getContractId());
        List<ContractProduct> list1 = new ArrayList<>();
        List<ContractProduct> list2 = new ArrayList<>();
        if (dto.getContractProductList() != null && dto.getContractProductList().size() > 0) {
            for (ContractProduct d : dto.getContractProductList()) {  //迭代根据contractId来区分是新增还是插入
                d.setContractId(dto.getContractId());    //设置contractId
                if (d.getCpId() == null) {   //没有主键的则为新增
                    list1.add(d);
                } else {
                    list2.add(d);

                    if (cpIdList != null && cpIdList.size() > 0) {
                        Iterator<Long> it = cpIdList.iterator();
                        while (it.hasNext()) {
                            Long ovcId = it.next();
                            if (ovcId.longValue() == d.getCpId().longValue()) {
                                it.remove();
                            }
                        }
                    }
                }
            }
            if (list1.size() > 0) {
                contractProductMapper.insertBatch(list1);  //批量插入商品
            }
            if (list2.size() > 0) {
                contractProductMapper.updateBatch(list2); //批量更新商品
            }
            if (cpIdList.size() > 0) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("cpIds", cpIdList);
                contractProductMapper.deleteByBatch(params);
            }
        }
        //审批流程添加 如果审批人不是空，则需先删除之前保存的审批人的信息，然后再新增审批人信息
        if (null != dto.getContractApprovalList() && dto.getContractApprovalList().size() > 0) {
            //删除之前保存的审批人信息
            contractApprovalMapper.deleteByContractId(dto.getContractId());
            /*1.加入创建人信息 2.设置关联的合同id 3.批量插入审批人信息*/

            for (ContractApproval ca : dto.getContractApprovalList()) {
                ca.setContractId(contract.getContractId()); //设置关联合同id
                if (ca.getActionType().shortValue() == 0) {
                    if (ca.getSort() == 1) {
                        ca.setStatus(new Short("1"));   //同时设置第一个审批的人的状态为审批中
                    } else {
                        ca.setStatus(new Short("0"));   //设置其他审批状态为 0 - 初始值
                    }
                } else {
                    ca.setStatus(new Short("0"));   //设置其他审批状态为 0 - 初始值
                }
            }
            ContractApproval contractApproval = new ContractApproval();
//            Long companyId = SecurityInfoGetter.getCompanyId();
            User user = SecurityInfoGetter.getUser();
            UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
            contractApproval.setContractId(contract.getContractId());
            contractApproval.setUserName(user.getRealName());
            contractApproval.setUserId(user.getUserId());
            contractApproval.setDeptName(userCompRel.getDeptNames());
            contractApproval.setSort(0);    // 0 为创建着
            contractApproval.setActionType(new Short("0")); //默认actionType 0
            contractApproval.setStatus(new Short("2"));
            contractApproval.setTime(new Date());
            dto.getContractApprovalList().add(contractApproval);
            contractApprovalMapper.insertBatch(dto.getContractApprovalList());
            //同时设置合同的审批状态为审批中
            contract.setApprovalStatus(new Short("1"));
            contract.setApprovalStartDate(new Date());
            contractMapper.updateByPrimaryKeySelective(contract);
        } else {
            /*如果审批流程被清除，则视为改合同不需要审批。需要：1.删除之前关联的审批人信息 2.更新合同状态为无需审批 0 */
            contractApprovalMapper.deleteByContractId(dto.getContractId());
            //同时设置合同的审批状态为审批中
            contract.setApprovalStatus(new Short("0"));
            contract.setApprovalStartDate(null);
            contract.setApprovalProcess(null);
            contract.setApprovalProcessId(null);
            contractMapper.updateByPrimaryKeySelective(contract);
        }
        //新增日志
        ContractLog log = new ContractLog();
        log.setContractId(contract.getContractId());
        log.setLogName("编辑");
        if (oldContract.getIsDraft() != contract.getIsDraft()) {//发布
            log.setLogContent("编辑合同内容，并发布");
        } else {
            log.setLogContent("编辑合同内容");
        }
        if (StringUtility.isNotEmpty(contract.getAttachment1())
                && !contract.getAttachment1().equals(oldContract.getAttachment1())) {
            log.setLogContent(log.getLogContent() + "，" + setAttachmentLog(contract.getAttachment1()));
        }
        saveContractLog(log);
        return result;
    }

    @Override
    public int delContract(Long contractId) {
        return 0;
    }

    @Override
    public PageInfo<List<ContractDto>> contractList(ContractDto contractDto, PageInfo pageInfo) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        List<Contract> contractList = contractMapper.selectByCondition(contractDto);
        List<ContractDto> contractDtoList = new ArrayList<ContractDto>(contractList.size());
        for (Contract Contract : contractList) {
            ContractDto contractDto2 = new ContractDto();
            BeanUtils.copyProperties(Contract, contractDto2);
            List<ContractProduct> contractProductList = contractProductMapper.selectCpsByContractId(Contract.getContractId());
            contractDto2.setContractProductList(contractProductList);
            contractDtoList.add(contractDto2);
        }
        PageInfo page1 = new PageInfo(contractList);
        PageInfo page2 = new PageInfo();
        page2.setTotal(page1.getTotal());
        page2.setList(contractDtoList);
        return page2;
    }

    @Override
    public int modContractStatus(Contract contract) {
        int result = contractMapper.updateContractStatus(contract);
        //新增日志
        ContractLog log = new ContractLog();//0-生效 3-失效 2-发布合同
        log.setContractId(contract.getContractId());
        log.setLogName(contract.getContractStatus() == 4 ? "取消" : contract.getContractStatus() == 0 ? "生效" : contract.getContractStatus() == 3 ? "终止" : "发布");
        log.setLogContent(contract.getContractStatus() == 4 ? "取消合同" : contract.getContractStatus() == 0 ? "合同生效" : contract.getContractStatus() == 3 ? "合同终止" : "发布合同");
        saveContractLog(log);
        //如果更新成功，则进行消息发送
        if (result > 0) {
            /**↓发送消息通知开始*/
            ContractDto contractDto = this.selectByPrimaryKey(contract.getContractId());
            Long userId = SecurityInfoGetter.getUser().getUserId();
            //发送者
            DefaultNotifySender defaultNotifySender = ContractNotifyBuilder.notifySender(contractDto.getCompanyId(), userId);
            //接收者
            DefaultNotifyReceiver defaultNotifyReceiver = ContractNotifyBuilder.notifyCarrierReceiver(contractDto.getCompanyId(), contractDto.getCreateId());
            ContractAttachment attachment = new ContractAttachment();
            attachment.setCarrierWebNotifyUrl("");
            String eventName = "";
            //合同生效
            if(contract.getContractStatus()==0){
                if (contractDto.getType().shortValue() == 1) {
                    eventName = "sale_effective";
                    attachment.setSaleConTittle(contractDto.getTitle());
                    attachment.setSaleConSerialNum(contractDto.getSerialNo());
                }else {
                    eventName = "purchase_effective";
                    attachment.setPurConTittle(contractDto.getTitle());
                    attachment.setPurConSerialNum(contractDto.getSerialNo());
                }
                //合同失效
            }else if(contract.getContractStatus()==3){
                if (contractDto.getType().shortValue() == 1) {
                    eventName = "sale_lose_effective";
                    attachment.setSaleConTittle(contractDto.getTitle());
                    attachment.setSaleConSerialNum(contractDto.getSerialNo());
                }else {
                    eventName = "purchase_lose_effective";
                    attachment.setPurConTittle(contractDto.getTitle());
                    attachment.setPurConSerialNum(contractDto.getSerialNo());
                }
            }
            ContractNotifyEvent plan_publish_event = new ContractNotifyEvent(eventName, attachment, defaultNotifyReceiver, defaultNotifySender);
            producer.sendNotifyEvent(plan_publish_event);
            /**↑发送消息通知结束*/
        }
        return result;
    }

    @Override
    public ContractDto selectByPrimaryKey(Long contractId) {
        ContractDto dto = contractMapper.selectByPrimaryKey(contractId);
        if (null != dto) {
            //获取合同下商品
            List<ContractProduct> productList = contractProductMapper.selectCpsByContractId(contractId);
            if (null != productList && productList.size() != 0) {
                dto.setContractProductList(productList);
            }
            //添加审批人及抄送人信息
            List<ContractApproval> approvalList = contractApprovalMapper.selectForContractDetail(contractId);
            if (null != approvalList && approvalList.size() > 0) {
                dto.setContractApprovalList(approvalList);
            }
        }
        return dto;
    }

    public String setAttachmentLog(String attachmentLog) {
//        String str = "{\"附件分类1\":[{\"name\":\"附件1name\",\"url\":\"附件1url\"},{\"name\":\"附件2name\",\"url\":\"附件2url\"}],"
//                +"\"附件分类2\":[{\"name\":\"附件3name\",\"url\":\"附件3url\"},{\"name\":\"附件4name\",\"url\":\"附件4url\"}]}";
        try {
            StringBuffer logContent = new StringBuffer();
            JSONObject jsonObject = JSONObject.parseObject(attachmentLog);
            Set<String> keys = jsonObject.keySet();
            if (keys != null && keys.size() > 0) {
                logContent.append("上传附件为：");
                for (String key : keys) {
                    JSONArray jsonArray = jsonObject.getJSONArray(key);
                    if (jsonArray != null && jsonArray.size() > 0) {
                        if ("undefinedName".equals(key)) {
                            logContent.append("默认附件——");
                        } else {
                            logContent.append(key + "——");
                        }
                        for (int i = 0; i < jsonArray.size(); i++) {
                            if (i > 0) {
                                logContent.append("，");
                            }
                            logContent.append(jsonArray.getJSONObject(i).get("name"));
                        }
                        logContent.append("；");
                    }
                }
            }
            return logContent.toString();
        } catch (Exception e) {
            return attachmentLog;
        }
    }

    public int saveContractLog(ContractLog log) {
        log.setOperatorId(SecurityInfoGetter.getUser().getUserId());
        log.setOperatorName(SecurityInfoGetter.getUser().getRealName());
        log.setOperatorDeptIds(SecurityInfoGetter.geUserCompRel().getDeptIds());
        log.setOperatorDeptNames(SecurityInfoGetter.geUserCompRel().getDeptNames());
        log.setCreateTime(new Date());
        log.setIsDeleted((short) 0);
        int result = contractLogMapper.insert(log);
        return result;
    }

    @Override
    public PageInfo<List<ContractLog>> contractLogList(Map map) {
        int pageNo = 1;
        int pageSize = 0; //0表示所有

        if (map.containsKey("pageNo")) {
            if (map.get("pageNo") != null) {
                pageNo = (Integer) map.get("pageNo");
            }
        }
        if (map.containsKey("pageSize")) {
            if (map.get("pageSize") != null) {
                pageSize = (Integer) map.get("pageSize");
            }
        }
        PageHelper.startPage(pageNo, pageSize);
        List<ContractLog> list = contractLogMapper.selectByContractId(map);
        PageInfo pageInfo = new PageInfo(list);

        return pageInfo;
    }

    @Override
    public boolean uploadAttachment(ContractDto contract) {
        if (contract.getContractId() == null) {
            return false;
        }
        ContractLog log = new ContractLog();
        log.setContractId(contract.getContractId());
        log.setLogName("上传附件");
        log.setLogContent(setAttachmentLog(contract.getAttachment1()));
        saveContractLog(log);
        contractMapper.updateByPrimaryKeySelective(contract);
        /**↓发送消息通知开始*/
        //发送者
        Long upLoader = SecurityInfoGetter.getUser().getUserId();
        DefaultNotifySender defaultNotifySender = ContractNotifyBuilder.notifySender(contract.getCompanyId(), upLoader);
        Contract ct = contractMapper.selectByPrimaryKey(contract.getContractId());
        //接收者
        DefaultNotifyReceiver defaultNotifyReceiver = ContractNotifyBuilder.notifyCarrierReceiver(contract.getCompanyId(), ct.getCreateId());
        ContractAttachment attachment = new ContractAttachment();
        attachment.setPurConTittle(ct.getTitle());
        attachment.setPurConSerialNum(ct.getSerialNo());
        attachment.setCarrierWebNotifyUrl("");
        String eventName = "purchase_upload";
        if (ct.getType().shortValue() == 1) {
            eventName = "sale_upload";
            attachment.setSaleConTittle(ct.getTitle());
            attachment.setSaleConSerialNum(ct.getSerialNo());
        }
        ContractNotifyEvent plan_publish_event = new ContractNotifyEvent(eventName, attachment, defaultNotifyReceiver, defaultNotifySender);
        producer.sendNotifyEvent(plan_publish_event);
        /**↑发送消息通知结束*/
        return true;
    }
}
