package com.lcdt.contract.service.impl;

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
import com.lcdt.contract.service.ContractService;
import com.lcdt.contract.web.dto.ContractDto;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
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

    @Override
    public int addContract(ContractDto dto) {
        BigDecimal summation =new 	BigDecimal(0);// 为所有商品价格求和
        if(null!=dto.getContractProductList()&&dto.getContractProductList().size()!=0){
            for(ContractProduct contractProduct : dto.getContractProductList()) {
                BigDecimal num = contractProduct.getNum();
                BigDecimal price = contractProduct.getPrice();
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
        if(dto.getContractProductList() != null && dto.getContractProductList().size() > 0) {
            //设置商品的合同id
            for (ContractProduct contractProduct : dto.getContractProductList()) {
                contractProduct.setContractId(contract.getContractId());
            }
            result += contractProductMapper.insertBatch(dto.getContractProductList());  //批量插入商品
        }
        //审批流程添加
        if(null!=dto.getContractApprovalList() && dto.getContractApprovalList().size() > 0){
            /*1.加入创建人信息 2.设置关联的合同id 3.批量插入审批人信息*/

            for(ContractApproval ca : dto.getContractApprovalList()){
                ca.setContractId(contract.getContractId()); //设置关联合同id
                if(ca.getActionType().shortValue()==0){
                    if(ca.getSort()==1){
                        ca.setStatus(new Short("1"));   //同时设置第一个审批的人的状态为审批中
                    }else{
                        ca.setStatus(new Short("0"));   //设置其他审批状态为 0 - 初始值
                    }
                }else{
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
        }else{
            // todo 没有添加审批人，则认为合同无需审批
            //同时设置合同的审批状态为审批中
            contract.setApprovalStatus(new Short("0"));
            contractMapper.updateByPrimaryKeySelective(contract);
        }
        ContractLog log = new ContractLog();
        log.setContractId(contract.getContractId());
        log.setLogName("新增");
        log.setLogContent("新增合同，"+(contract.getIsDraft()==0?"存草稿":"发布合同"));
        saveContractLog(log);
        if(StringUtility.isNotEmpty(contract.getAttachment1Name())){
            ContractLog log1 = new ContractLog();
            log1.setContractId(contract.getContractId());
            log1.setLogName("上传附件");
            log1.setLogContent(contract.getAttachment1Name());
            saveContractLog(log1);
        }
        return result;
    }

    @Override
    public int modContract(ContractDto dto) {
        //修改之前的合同
  //      Contract oldContract = contractMapper.selectByPrimaryKey(dto.getContractId());

        BigDecimal summation =new 	BigDecimal(0);// 为所有商品价格求和
        if(null!=dto.getContractProductList()&&dto.getContractProductList().size()!=0){
            for(ContractProduct contractProduct : dto.getContractProductList()) {
                BigDecimal num = contractProduct.getNum();
                BigDecimal price = contractProduct.getPrice();
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

                    if(cpIdList != null && cpIdList.size() > 0) {
                        Iterator<Long> it = cpIdList.iterator();
                        while(it.hasNext()){
                            Long ovcId = it.next();
                            if(ovcId.longValue()==d.getCpId().longValue()){
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
            if(cpIdList.size()>0){
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("cpIds",cpIdList);
                contractProductMapper.deleteByBatch(params);
            }
        }
        //审批流程添加 如果审批人不是空，则需先删除之前保存的审批人的信息，然后再新增审批人信息
        if(null!=dto.getContractApprovalList() && dto.getContractApprovalList().size() > 0){
            //删除之前保存的审批人信息
            contractApprovalMapper.deleteByContractId(dto.getContractId());
            /*1.加入创建人信息 2.设置关联的合同id 3.批量插入审批人信息*/

            for(ContractApproval ca : dto.getContractApprovalList()){
                ca.setContractId(contract.getContractId()); //设置关联合同id
                if(ca.getActionType().shortValue()==0){
                    if(ca.getSort()==1){
                        ca.setStatus(new Short("1"));   //同时设置第一个审批的人的状态为审批中
                    }else{
                        ca.setStatus(new Short("0"));   //设置其他审批状态为 0 - 初始值
                    }
                }else{
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
        }else{
            /*如果审批流程被清除，则视为改合同不需要审批。需要：1.删除之前关联的审批人信息 2.更新合同状态为无需审批 0 */
            contractApprovalMapper.deleteByContractId(dto.getContractId());
            //同时设置合同的审批状态为审批中
            contract.setApprovalStatus(new Short("0"));
            contract.setApprovalStartDate(null);
            contract.setApprovalProcess(null);
            contract.setApprovalProcessId(null);
            contractMapper.updateByPrimaryKeySelective(contract);
        }
        ContractLog log = new ContractLog();
        log.setContractId(contract.getContractId());
        log.setLogName("编辑");
        if(oldContract.getIsDraft() != contract.getIsDraft()){//草稿改为发布
            log.setLogContent("编辑合同内容，并发布草稿");
        }else{
            log.setLogContent("编辑合同内容");
        }
        saveContractLog(log);
        if(StringUtility.isNotEmpty(contract.getAttachment1Name())){
            ContractLog log1 = new ContractLog();
            log1.setContractId(contract.getContractId());
            log1.setLogName("上传附件");
            log1.setLogContent(contract.getAttachment1Name());
            saveContractLog(log1);
        }
        return result;
    }

    @Override
    public int delContract(Long contractId) {
        return 0;
    }

    @Override
    public PageInfo<List<Contract>> ontractList(ContractDto contractDto, PageInfo pageInfo) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        PageInfo page = new PageInfo(contractMapper.selectByCondition(contractDto));
        return page;
    }

    @Override
    public int modContractStatus(Contract contract) {
        int result = contractMapper.updateContractStatus(contract);
        ContractLog log = new ContractLog();//0-生效 3-失效 2-草稿发布
        log.setContractId(contract.getContractId());
        log.setLogName(contract.getContractStatus()==0?"生效":contract.getContractStatus()==3?"终止":"发布");
        log.setLogContent(contract.getContractStatus()==0?"合同生效":contract.getContractStatus()==3?"合同终止":"发布草稿");
        saveContractLog(log);
        return result;
    }

    @Override
    public ContractDto selectByPrimaryKey(Long contractId) {
        ContractDto dto=contractMapper.selectByPrimaryKey(contractId);
        if(null != dto) {
            //获取合同下商品
            List<ContractProduct> productList = contractProductMapper.selectCpsByContractId(contractId);
            if(null != productList && productList.size() != 0) {
                dto.setContractProductList(productList);
            }
            //添加审批人及抄送人信息
            List<ContractApproval> approvalList = contractApprovalMapper.selectForContractDetail(contractId);
            if(null != approvalList && approvalList.size() > 0){
                dto.setContractApprovalList(approvalList);
            }
        }
        return dto;
    }
    public int saveContractLog(ContractLog log){
//        String str = "{\"附件分类1\":[{\"name\":\"附件1name\",\"url\":\"附件1url\"},{\"name\":\"附件2name\",\"url\":\"附件2url\"}],"
//                +"\"附件分类2\":[{\"name\":\"附件3name\",\"url\":\"附件3url\"},{\"name\":\"附件4name\",\"url\":\"附件4url\"}]}";
        if("上传附件".equals(log.getLogName())){
            StringBuffer logContent = new StringBuffer();
            JSONObject jsonObject = JSONObject.parseObject(log.getLogContent());
            Set<String> keys = jsonObject.keySet();
            if(keys != null && keys.size() > 0){
                logContent.append("上传附件为：");
                for(String key : keys) {
                    JSONArray jsonArray = jsonObject.getJSONArray(key);
                    if(jsonArray != null && jsonArray.size() > 0){
                        logContent.append(key+"——");
                        for(int i=0; i<jsonArray.size(); i++){
                            if(i > 0){
                                logContent.append("，");
                            }
                            logContent.append(jsonArray.getJSONObject(i).get("name"));
                        }
                        logContent.append("；");
                    }
                }
            }
            log.setLogContent(logContent.toString());
        }
        log.setOperatorId(SecurityInfoGetter.getUser().getUserId());
        log.setOperatorName(SecurityInfoGetter.getUser().getRealName());
        log.setOperatorDeptIds(SecurityInfoGetter.geUserCompRel().getDeptIds());
        log.setOperatorDeptNames(SecurityInfoGetter.geUserCompRel().getDeptNames());
        log.setCreateTime(new Date());
        log.setIsDeleted((short)0);
        int result = contractLogMapper.insert(log);
        return result;
    }

    @Override
    public PageInfo<List<ContractLog>> ontractLogList(Map map) {
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

    public boolean uploadAttachment(ContractDto contract){
        if(contract.getContractId() == null){
            return false;
        }
        ContractLog log1 = new ContractLog();
        log1.setContractId(contract.getContractId());
        log1.setLogName("上传附件");
        log1.setLogContent(contract.getAttachment1());
        saveContractLog(log1);
        contractMapper.updateByPrimaryKeySelective(contract);

        return true;
    }
}
