package com.lcdt.contract.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.contract.dao.ContractMapper;
import com.lcdt.contract.dao.ContractProductMapper;
import com.lcdt.contract.model.Contract;
import com.lcdt.contract.model.ContractApproval;
import com.lcdt.contract.model.ContractProduct;
import com.lcdt.contract.web.dto.ContractDto;
import com.lcdt.contract.service.ContractService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public int addContract(ContractDto dto) {
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
        return result;
    }

    @Override
    public int modContract(ContractDto dto) {
        Contract contract = new Contract();
        BeanUtils.copyProperties(dto, contract); //复制对象属性
        int result = contractMapper.updateByPrimaryKey(contract);
        //获取该合同下面的商品cpId用来匹配被删除的商品
        List<Long> cpIdList = contractProductMapper.selectCpIdByContractId(contract.getContractId());
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
                            if(ovcId==d.getCpId()){
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
}
