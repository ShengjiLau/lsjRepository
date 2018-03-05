package com.lcdt.contract.dao;

import com.lcdt.contract.model.ContractProduct;
import java.util.List;

public interface ContractProductMapper {
    int deleteByPrimaryKey(Long cpId);

    int insert(ContractProduct record);

    ContractProduct selectByPrimaryKey(Long cpId);

    List<ContractProduct> selectAll();

    int updateByPrimaryKey(ContractProduct record);
}