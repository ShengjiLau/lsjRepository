package com.lcdt.contract.dao;

import com.lcdt.contract.model.ContractProduct;
import java.util.List;
import java.util.Map;

public interface ContractProductMapper {
    int deleteByPrimaryKey(Long cpId);

    int insert(ContractProduct record);

    ContractProduct selectByPrimaryKey(Long cpId);

    List<ContractProduct> selectAll();

    int updateByPrimaryKey(ContractProduct record);

    /**
     * 批量插入
     * @param contractProductList
     * @return
     */
    int insertBatch(List<ContractProduct> contractProductList);

    /**
     * 批量更新
     * @param contractProductList
     * @return
     */
    int updateBatch(List<ContractProduct> contractProductList);

    /**
     * 根据合同id查询该合同下的商品cpId
     * @param contractId
     * @return
     */
    List<Long> selectCpIdByContractId(Long contractId);

    /**
     * 根据主键批量删除
     * @param map
     * @return
     */
    int deleteByBatch(Map<String, Object> map);
}