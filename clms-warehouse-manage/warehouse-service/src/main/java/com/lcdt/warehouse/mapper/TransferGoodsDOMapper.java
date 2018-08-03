package com.lcdt.warehouse.mapper;

import java.util.List;
import java.util.Map;

import com.lcdt.warehouse.entity.TransferGoodsDO;

public interface TransferGoodsDOMapper {
    int deleteByPrimaryKey(Long goodsId);

    int insert(TransferGoodsDO record);

    int insertSelective(TransferGoodsDO record);

    TransferGoodsDO selectByPrimaryKey(Long goodsId);

    int updateByPrimaryKeySelective(TransferGoodsDO record);

    int updateByPrimaryKey(TransferGoodsDO record);
    
    int insertTransferGoodsDOByBatch(List<TransferGoodsDO> transferGoodsDOList);
    
    int updateTransferGoodsDOByBatch(List<TransferGoodsDO> transferGoodsDOList);
    
    List<TransferGoodsDO> getTransferGoodsDOListByTransferIds(Long[] transferIds);
    
    List<TransferGoodsDO> getTransferGoodsDOListByTransferId(Long transferId);
    
    List<TransferGoodsDO> getTransferGoodsDOListByCondition(Long transferId, String materialProduct, String finishedProduct);
    
    List<TransferGoodsDO> getTransferGoodsDOListByConditions(Map<String, Object> map);
    
}