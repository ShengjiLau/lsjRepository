package com.lcdt.warehouse.mapper;

import com.lcdt.warehouse.entity.TransferInventoryListDO;

public interface TransferInventoryListDOMapper {
    int deleteByPrimaryKey(Long transfersId);

    int insert(TransferInventoryListDO record);

    int insertSelective(TransferInventoryListDO record);

    TransferInventoryListDO selectByPrimaryKey(Long transfersId);

    int updateByPrimaryKeySelective(TransferInventoryListDO record);

    int updateByPrimaryKey(TransferInventoryListDO record);
}