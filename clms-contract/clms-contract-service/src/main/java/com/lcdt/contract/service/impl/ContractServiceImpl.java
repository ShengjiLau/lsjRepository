package com.lcdt.contract.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.dao.*;
import com.lcdt.contract.model.*;

import com.lcdt.contract.service.ContractService;
import com.lcdt.contract.web.dto.ContractDto;

import com.lcdt.contract.web.utils.SerialNumAutoGenerator;
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
    private OrderMapper orderMapper;
    @Autowired
    private ConditionQueryMapper conditionQueryMapper;

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
        if(!StringUtility.isNotEmpty(dto.getContractCode())){
            contract.setContractCode((contract.getType()==0?"CG":"XS")
                    + SerialNumAutoGenerator.getSerialNoById(contract.getContractId()));
        }
        contract.setSerialNo((contract.getType()==0?"CG":"XS")
                + SerialNumAutoGenerator.getSerialNoById(contract.getContractId()));
        contractMapper.updateByPrimaryKey(contract);
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
            contractMapper.updateByPrimaryKey(contract);
        }else{
            // todo 没有添加审批人，则认为合同无需审批
            //同时设置合同的审批状态为审批中
            contract.setApprovalStatus(new Short("0"));
            contractMapper.updateByPrimaryKey(contract);
        }
        return result;
    }

    @Override
    public int modContract(ContractDto dto) {
        //修改之前的合同
        Contract oldContract = contractMapper.selectByPrimaryKey(dto.getContractId());

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
        //将修改之前的部分属性赋值
        if(!StringUtility.isNotEmpty(contract.getContractCode())){
            contract.setContractCode(oldContract.getSerialNo());
        }
        contract.setSerialNo(oldContract.getSerialNo());
        contract.setContractStatus(oldContract.getContractStatus());
        contract.setCompanyId(oldContract.getCompanyId());
        contract.setCreateId(oldContract.getCreateId());
        contract.setCreateName(oldContract.getCreateName());
        contract.setCreateTime(oldContract.getCreateTime());
        contract.setEffectiveTime(oldContract.getEffectiveTime());
        contract.setTerminationTime(oldContract.getTerminationTime());

        int result = contractMapper.updateByPrimaryKey(contract);
        //获取该合同下面的商品cpId用来匹配被删除的商品
        List<Long> cpIdList = contractProductMapper.selectCpIdsByContractId(contract.getContractId());
        List<ContractProduct> list1 = new ArrayList<>();
        List<ContractProduct> list2 = new ArrayList<>();
        if (dto.getContractProductList() != null && dto.getContractProductList().size() > 0) {
            for (ContractProduct d : dto.getContractProductList()) {  //迭代根据contractId来区分是新增还是插入
                if (d.getCpId() == null) {   //没有主键的则为新增
                    d.setContractId(dto.getContractId());    //设置contractId
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
            contractMapper.updateByPrimaryKey(contract);
        }else{
            /*如果审批流程被清除，则视为改合同不需要审批。需要：1.删除之前关联的审批人信息 2.更新合同状态为无需审批 0 */
            contractApprovalMapper.deleteByContractId(dto.getContractId());
            //同时设置合同的审批状态为审批中
            contract.setApprovalStatus(new Short("0"));
            contract.setApprovalStartDate(null);
            contractMapper.updateByPrimaryKey(contract);
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
}
