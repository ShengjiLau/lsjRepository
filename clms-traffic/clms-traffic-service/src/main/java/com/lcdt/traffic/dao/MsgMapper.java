package com.lcdt.traffic.dao;

import java.util.List;

import com.lcdt.traffic.model.Msg;
import com.lcdt.traffic.web.dto.MsgDto;

public interface MsgMapper {
    int deleteByPrimaryKey(Long msgId);

    int insert(Msg record);

    int insertSelective(Msg record);

    Msg selectByPrimaryKey(Long msgId);

    int updateByPrimaryKeySelective(Msg record);

    int updateByPrimaryKey(Msg record);
    
    int updateIsDelete(Long msgId);
    
    Msg selectByAccountId(Long accountId,Short type);
    
    List<Msg> selectSomeMsg(MsgDto msgDto);
}