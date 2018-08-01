package com.lcdt.warehouse.mapper;

import com.lcdt.warehouse.entity.TransferGoodsDO;

public interface TransferGoodsDOMapper {
    int deleteByPrimaryKey(Long goodsId);

    int insert(TransferGoodsDO record);

    int insertSelective(TransferGoodsDO record);

    TransferGoodsDO selectByPrimaryKey(Long goodsId);

    int updateByPrimaryKeySelective(TransferGoodsDO record);

    int updateByPrimaryKey(TransferGoodsDO record);
}